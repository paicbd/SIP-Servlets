package org.mobicents.io.undertow.servlet.core;

import static javax.servlet.http.HttpServletRequest.BASIC_AUTH;
import static javax.servlet.http.HttpServletRequest.CLIENT_CERT_AUTH;
import static javax.servlet.http.HttpServletRequest.DIGEST_AUTH;
import static javax.servlet.http.HttpServletRequest.FORM_AUTH;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;

import javax.servlet.ServletContext;

import org.mobicents.io.undertow.servlet.handlers.ConvergedSessionRestoringHandler;
import io.undertow.Handlers;
import io.undertow.predicate.Predicates;
import io.undertow.security.api.AuthenticationMechanism;
import io.undertow.security.api.AuthenticationMechanismFactory;
import io.undertow.security.api.AuthenticationMode;
import io.undertow.security.api.NotificationReceiver;
import io.undertow.security.api.SecurityContextFactory;
import io.undertow.security.handlers.AuthenticationMechanismsHandler;
import io.undertow.security.handlers.NotificationReceiverHandler;
import io.undertow.security.handlers.SecurityInitialHandler;
import io.undertow.security.idm.IdentityManager;
import io.undertow.security.impl.BasicAuthenticationMechanism;
import io.undertow.security.impl.CachedAuthenticatedSessionMechanism;
import io.undertow.security.impl.ClientCertAuthenticationMechanism;
import io.undertow.security.impl.DigestAuthenticationMechanism;
import io.undertow.security.impl.ExternalAuthenticationMechanism;
import io.undertow.security.impl.SecurityContextFactoryImpl;
import io.undertow.server.HandlerWrapper;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.form.FormEncodedDataDefinition;
import io.undertow.server.handlers.form.FormParserFactory;
import io.undertow.server.session.SessionManager;
import io.undertow.servlet.ServletExtension;
import io.undertow.servlet.UndertowServletMessages;
import io.undertow.servlet.api.AuthMethodConfig;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.ErrorPage;
import io.undertow.servlet.api.FilterInfo;
import io.undertow.servlet.api.HttpMethodSecurityInfo;
import io.undertow.servlet.api.ListenerInfo;
import io.undertow.servlet.api.LoginConfig;
import io.undertow.servlet.api.MimeMapping;
import io.undertow.servlet.api.SecurityConstraint;
import io.undertow.servlet.api.ServletContainer;
import io.undertow.servlet.api.ServletInfo;
import io.undertow.servlet.api.ServletSecurityInfo;
import io.undertow.servlet.api.SessionPersistenceManager;
import io.undertow.servlet.api.WebResourceCollection;
import io.undertow.servlet.api.SecurityInfo.EmptyRoleSemantic;
import io.undertow.servlet.core.ApplicationListeners;
import io.undertow.servlet.core.DeploymentImpl;
import io.undertow.servlet.core.DeploymentManagerImpl;
import io.undertow.servlet.core.ErrorPages;
import io.undertow.servlet.core.ManagedListener;
import io.undertow.servlet.handlers.SessionRestoringHandler;
import io.undertow.servlet.handlers.security.CachedAuthenticatedSessionHandler;
import io.undertow.servlet.handlers.security.SSLInformationAssociationHandler;
import io.undertow.servlet.handlers.security.SecurityPathMatches;
import io.undertow.servlet.handlers.security.ServletAuthenticationCallHandler;
import io.undertow.servlet.handlers.security.ServletAuthenticationConstraintHandler;
import io.undertow.servlet.handlers.security.ServletConfidentialityConstraintHandler;
import io.undertow.servlet.handlers.security.ServletFormAuthenticationMechanism;
import io.undertow.servlet.handlers.security.ServletSecurityConstraintHandler;
import io.undertow.util.MimeMappings;
/**
 * This class extends io.undertow.servlet.core.DeploymentManagerImpl in order to create org.mobicents.servlet.sip.startup.ConvergedServletContextImpl instead of
 * io.undertow.servlet.spec.ServletContextImpl during deploy. With this modification it is possible to create ConvergedHttpSessions instead of pain HttpSessions during ServletContext.getSession() calls.
 *
 * @author kakonyi.istvan@alerant.hu
 * @author balogh.gabor@alerant.hu
 */
public class ConvergedDeploymentManagerImpl extends DeploymentManagerImpl {

    private final DeploymentInfo originalDeployment;

    private final ServletContainer servletContainer;


    public ConvergedDeploymentManagerImpl(DeploymentInfo deployment, ServletContainer servletContainer) {
        super(deployment, servletContainer);
        this.originalDeployment = deployment;
        this.servletContainer = servletContainer;
    }

    private void invokeDeploymentMethod(DeploymentImpl source, String methodName, Class<?>[] parameterTypes, Object... args){
        try{
            //FIXME kakonyii: what if the declared method throws an exception?
            Method method = DeploymentImpl.class.getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true);
            method.invoke(source, args);
            method.setAccessible(false);
        }catch(NoSuchMethodException | InvocationTargetException | IllegalAccessException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deploy() {
        super.deploy();
    }

    private void createServletsAndFilters(final DeploymentImpl deployment, final DeploymentInfo deploymentInfo) {
        for (Map.Entry<String, ServletInfo> servlet : deploymentInfo.getServlets().entrySet()) {
            deployment.getServlets().addServlet(servlet.getValue());
        }
        for (Map.Entry<String, FilterInfo> filter : deploymentInfo.getFilters().entrySet()) {
            deployment.getFilters().addFilter(filter.getValue());
        }
    }

    private void handleExtensions(final DeploymentInfo deploymentInfo, final ServletContext servletContext) {
        Set<Class<?>> loadedExtensions = new HashSet<>();

        for (ServletExtension extension : ServiceLoader.load(ServletExtension.class, deploymentInfo.getClassLoader())) {
            loadedExtensions.add(extension.getClass());
            extension.handleDeployment(deploymentInfo, servletContext);
        }

        if (!ServletExtension.class.getClassLoader().equals(deploymentInfo.getClassLoader())) {
            for (ServletExtension extension : ServiceLoader.load(ServletExtension.class)) {

                // Note: If the CLs are different, but can the see the same extensions and extension might get loaded
                // and thus instantiated twice, but the handleDeployment() is executed only once.

                if (!loadedExtensions.contains(extension.getClass())) {
                    extension.handleDeployment(deploymentInfo, servletContext);
                }
            }
        }
        for(ServletExtension extension : deploymentInfo.getServletExtensions()) {
            extension.handleDeployment(deploymentInfo, servletContext);
        }
    }

    private HttpHandler setupSecurityHandlers(HttpHandler initialHandler) {
        final DeploymentInfo deploymentInfo = super.getDeployment().getDeploymentInfo();
        final LoginConfig loginConfig = deploymentInfo.getLoginConfig();

        final Map<String, AuthenticationMechanismFactory> factoryMap = new HashMap<>(deploymentInfo.getAuthenticationMechanisms());
        final IdentityManager identityManager = deploymentInfo.getIdentityManager();
        
        if(!factoryMap.containsKey(BASIC_AUTH)) {
            factoryMap.put(BASIC_AUTH, new BasicAuthenticationMechanism.Factory(identityManager));
        }
        if(!factoryMap.containsKey(FORM_AUTH)) {
            factoryMap.put(FORM_AUTH, new ServletFormAuthenticationMechanism.Factory(identityManager));
        }
        if(!factoryMap.containsKey(DIGEST_AUTH)) {
            factoryMap.put(DIGEST_AUTH, new DigestAuthenticationMechanism.Factory(identityManager));
        }
        if(!factoryMap.containsKey(CLIENT_CERT_AUTH)) {
            factoryMap.put(CLIENT_CERT_AUTH, new ClientCertAuthenticationMechanism.Factory(identityManager));
        }
        if(!factoryMap.containsKey(ExternalAuthenticationMechanism.NAME)) {
            factoryMap.put(ExternalAuthenticationMechanism.NAME, new ExternalAuthenticationMechanism.Factory(identityManager));
        }
        HttpHandler current = initialHandler;
        current = new SSLInformationAssociationHandler(current);

        final SecurityPathMatches securityPathMatches = buildSecurityConstraints();
        current = new ServletAuthenticationCallHandler(current);
        if(deploymentInfo.isDisableCachingForSecuredPages()) {
            current = Handlers.predicate(Predicates.authRequired(), Handlers.disableCache(current), current);
        }
        if (!securityPathMatches.isEmpty()) {
            current = new ServletAuthenticationConstraintHandler(current);
        }
        current = new ServletConfidentialityConstraintHandler(deploymentInfo.getConfidentialPortManager(), current);
        if (!securityPathMatches.isEmpty()) {
            current = new ServletSecurityConstraintHandler(securityPathMatches, current);
        }
        List<AuthenticationMechanism> authenticationMechanisms = new LinkedList<>();
        authenticationMechanisms.add(new CachedAuthenticatedSessionMechanism()); //TODO: does this really need to be hard coded?

        String mechName = null;
        if (loginConfig != null || deploymentInfo.getJaspiAuthenticationMechanism() != null) {

            //we don't allow multipart requests, and always use the default encoding
            FormParserFactory parser = FormParserFactory.builder(false)
                    .addParser(new FormEncodedDataDefinition().setDefaultEncoding(deploymentInfo.getDefaultEncoding()))
                    .build();

            List<AuthMethodConfig> authMethods = Collections.<AuthMethodConfig>emptyList();
            if(loginConfig != null) {
                authMethods = loginConfig.getAuthMethods();
            }

            for(AuthMethodConfig method : authMethods) {
                AuthenticationMechanismFactory factory = factoryMap.get(method.getName());
                if(factory == null) {
                    throw UndertowServletMessages.MESSAGES.unknownAuthenticationMechanism(method.getName());
                }
                if(mechName == null) {
                    mechName = method.getName();
                }

                final Map<String, String> properties = new HashMap<>();
                properties.put(AuthenticationMechanismFactory.CONTEXT_PATH, deploymentInfo.getContextPath());
                properties.put(AuthenticationMechanismFactory.REALM, loginConfig.getRealmName());
                properties.put(AuthenticationMechanismFactory.ERROR_PAGE, loginConfig.getErrorPage());
                properties.put(AuthenticationMechanismFactory.LOGIN_PAGE, loginConfig.getLoginPage());
                properties.putAll(method.getProperties());

                String name = method.getName().toUpperCase(Locale.US);
                // The mechanism name is passed in from the HttpServletRequest interface as the name reported needs to be
                // comparable using '=='
                name = name.equals(FORM_AUTH) ? FORM_AUTH : name;
                name = name.equals(BASIC_AUTH) ? BASIC_AUTH : name;
                name = name.equals(DIGEST_AUTH) ? DIGEST_AUTH : name;
                name = name.equals(CLIENT_CERT_AUTH) ? CLIENT_CERT_AUTH : name;

                authenticationMechanisms.add(factory.create(name, parser, properties));
            }
        }

        ((DeploymentImpl)super.getDeployment()).setAuthenticationMechanisms(authenticationMechanisms);
        //if the JASPI auth mechanism is set then it takes over
        if(deploymentInfo.getJaspiAuthenticationMechanism() == null) {
            current = new AuthenticationMechanismsHandler(current, authenticationMechanisms);
        } else {
            current = new AuthenticationMechanismsHandler(current, Collections.<AuthenticationMechanism>singletonList(deploymentInfo.getJaspiAuthenticationMechanism()));
        }

        current = new CachedAuthenticatedSessionHandler(current, super.getDeployment().getServletContext());
        List<NotificationReceiver> notificationReceivers = deploymentInfo.getNotificationReceivers();
        if (!notificationReceivers.isEmpty()) {
            current = new NotificationReceiverHandler(current, notificationReceivers);
        }

        // TODO - A switch to constraint driven could be configurable, however before we can support that with servlets we would
        // need additional tracking within sessions if a servlet has specifically requested that authentication occurs.
        SecurityContextFactory contextFactory = deploymentInfo.getSecurityContextFactory();
        if (contextFactory == null) {
            contextFactory = SecurityContextFactoryImpl.INSTANCE;
        }
        current = new SecurityInitialHandler(AuthenticationMode.PRO_ACTIVE, deploymentInfo.getIdentityManager(), mechName,
                contextFactory, current);
        return current;
    }

    private SecurityPathMatches buildSecurityConstraints() {
        SecurityPathMatches.Builder builder = SecurityPathMatches.builder(super.getDeployment().getDeploymentInfo());
        final Set<String> urlPatterns = new HashSet<>();
        for (SecurityConstraint constraint : super.getDeployment().getDeploymentInfo().getSecurityConstraints()) {
            builder.addSecurityConstraint(constraint);
            for (WebResourceCollection webResources : constraint.getWebResourceCollections()) {
                urlPatterns.addAll(webResources.getUrlPatterns());
            }
        }

        for (final ServletInfo servlet : super.getDeployment().getDeploymentInfo().getServlets().values()) {
            final ServletSecurityInfo securityInfo = servlet.getServletSecurityInfo();
            if (securityInfo != null) {
                final Set<String> mappings = new HashSet<>(servlet.getMappings());
                mappings.removeAll(urlPatterns);
                if (!mappings.isEmpty()) {
                    final Set<String> methods = new HashSet<>();

                    for (HttpMethodSecurityInfo method : securityInfo.getHttpMethodSecurityInfo()) {
                        methods.add(method.getMethod());
                        if (method.getRolesAllowed().isEmpty() && method.getEmptyRoleSemantic() == EmptyRoleSemantic.PERMIT) {
                            //this is an implict allow
                            continue;
                        }
                        SecurityConstraint newConstraint = new SecurityConstraint()
                                .addRolesAllowed(method.getRolesAllowed())
                                .setTransportGuaranteeType(method.getTransportGuaranteeType())
                                .addWebResourceCollection(new WebResourceCollection().addUrlPatterns(mappings)
                                        .addHttpMethod(method.getMethod()));
                        builder.addSecurityConstraint(newConstraint);
                    }
                    //now add the constraint, unless it has all default values and method constrains where specified
                    if (!securityInfo.getRolesAllowed().isEmpty()
                            || securityInfo.getEmptyRoleSemantic() != EmptyRoleSemantic.PERMIT
                            || methods.isEmpty()) {
                        SecurityConstraint newConstraint = new SecurityConstraint()
                                .setEmptyRoleSemantic(securityInfo.getEmptyRoleSemantic())
                                .addRolesAllowed(securityInfo.getRolesAllowed())
                                .setTransportGuaranteeType(securityInfo.getTransportGuaranteeType())
                                .addWebResourceCollection(new WebResourceCollection().addUrlPatterns(mappings)
                                        .addHttpMethodOmissions(methods));
                        builder.addSecurityConstraint(newConstraint);
                    }
                }

            }
        }

        return builder.build();
    }

    private void initializeTempDir(final ServletContext servletContext, final DeploymentInfo deploymentInfo) {
        if (deploymentInfo.getTempDir() != null) {
            servletContext.setAttribute(ServletContext.TEMPDIR, deploymentInfo.getTempDir());
        } else {
            servletContext.setAttribute(ServletContext.TEMPDIR, new File(SecurityActions.getSystemProperty("java.io.tmpdir")));
        }
    }

    private void initializeMimeMappings(final DeploymentImpl deployment, final DeploymentInfo deploymentInfo) {
        final Map<String, String> mappings = new HashMap<>(MimeMappings.DEFAULT_MIME_MAPPINGS);
        for (MimeMapping mapping : deploymentInfo.getMimeMappings()) {
            mappings.put(mapping.getExtension(), mapping.getMimeType());
        }
        deployment.setMimeExtensionMappings(mappings);
    }

    private void initializeErrorPages(final DeploymentImpl deployment, final DeploymentInfo deploymentInfo) {
        final Map<Integer, String> codes = new HashMap<>();
        final Map<Class<? extends Throwable>, String> exceptions = new HashMap<>();
        String defaultErrorPage = null;
        for (final ErrorPage page : deploymentInfo.getErrorPages()) {
            if (page.getExceptionType() != null) {
                exceptions.put(page.getExceptionType(), page.getLocation());
            } else if (page.getErrorCode() != null) {
                codes.put(page.getErrorCode(), page.getLocation());
            } else {
                if (defaultErrorPage != null) {
                    throw UndertowServletMessages.MESSAGES.moreThanOneDefaultErrorPage(defaultErrorPage, page.getLocation());
                } else {
                    defaultErrorPage = page.getLocation();
                }
            }
        }
        deployment.setErrorPages(new ErrorPages(codes, exceptions, defaultErrorPage));
    }

    private ApplicationListeners createListeners() {
        final List<ManagedListener> managedListeners = new ArrayList<>();
        for (final ListenerInfo listener : super.getDeployment().getDeploymentInfo().getListeners()) {
            managedListeners.add(new ManagedListener(listener, false));
        }
        return new ApplicationListeners(managedListeners, super.getDeployment().getServletContext());
    }


    private static HttpHandler wrapHandlers(final HttpHandler wrapee, final List<HandlerWrapper> wrappers) {
        HttpHandler current = wrapee;
        for (HandlerWrapper wrapper : wrappers) {
            current = wrapper.wrap(current);
        }
        return current;
    }

    private HttpHandler handleDevelopmentModePersistentSessions(HttpHandler next, final DeploymentInfo deploymentInfo, final SessionManager sessionManager, final ServletContext servletContext) {
        final SessionPersistenceManager sessionPersistenceManager = deploymentInfo.getSessionPersistenceManager();
        if (sessionPersistenceManager != null) {
            SessionRestoringHandler handler = new ConvergedSessionRestoringHandler(super.getDeployment().getDeploymentInfo().getDeploymentName(), sessionManager, servletContext, next, sessionPersistenceManager);

            //((DeploymentImpl)super.getDeployment()).addLifecycleObjects(handler);
            this.invokeDeploymentMethod(((DeploymentImpl)super.getDeployment()), "addLifecycleObjects", new Class[]{SessionRestoringHandler.class}, new Object[]{handler});

            return handler;
        }
        return next;
    }
}
