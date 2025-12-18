package org.mobicents.servlet.sip.router;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.sip.SipServletRequest;

/**
 * 
 * Simple implementation that uses a regex against a predefined SIP header.
 * 
 * The regular expression comes from the RouterInfo, and have been previously precompiled for performance.
 * 
 * Several regex may be defined against different headers.
 * 
 * The condition returns true if all the regular expressions match the proper header.
 * 
 * This fixes https://github.com/Mobicents/sip-servlets/issues/5
 */
public class HeaderRegexCondition implements AppRouterCondition {

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean checkCondition(final SipServletRequest initialRequest, final DefaultSipApplicationRouterInfo info) {
        boolean enabled = true;
        for (String hAux : info.getHeaderPatternMap().keySet()) {
            String headerValue = initialRequest.getHeader(hAux);
            // Pattern is ThreadSafe as doc by Java doc
            // Matcher is not threadsafe, but a new one is created every time.
            // Anyway if performance is degraded, Threadlocal/pool may be used
            Pattern headerPattern = info.getHeaderPatternMap().get(hAux);
            Matcher matcher = headerPattern.matcher(headerValue);
            enabled = enabled && matcher.find();
        }
        return enabled;
    }
}
