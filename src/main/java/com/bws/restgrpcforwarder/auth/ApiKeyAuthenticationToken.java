package com.bws.restgrpcforwarder.auth;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * Authentication token for API key authentication.
 * This class represents an authentication token that holds an API key.
 */
public final class ApiKeyAuthenticationToken extends AbstractAuthenticationToken {

    private final String apiKey;


    /**
     * Constructor for unauthenticated tokens (e.g. on request)
     */
    public ApiKeyAuthenticationToken(String apiKey) {
        super(null);
        this.apiKey = apiKey;
        setAuthenticated(false);
    }

    /**
     * Constructor for authenticated tokens (e.g. after validation)
     */
    public ApiKeyAuthenticationToken(String apiKey, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.apiKey = apiKey;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() { return null; }

    @Override
    public Object getPrincipal() { return apiKey; }
}
