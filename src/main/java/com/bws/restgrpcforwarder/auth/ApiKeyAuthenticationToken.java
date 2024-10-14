package com.bws.restgrpcforwarder.auth;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * Authentication token for API key authentication.
 * This class represents an authentication token that holds an API key.
 */
public class ApiKeyAuthenticationToken extends AbstractAuthenticationToken {

    private final String apiKey;

    /**
     * Constructs an ApiKeyAuthenticationToken with the provided API key and authorities.
     *
     * @param apiKey the API key
     * @param authorities the collection of granted authorities
     */
    public ApiKeyAuthenticationToken(String apiKey, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.apiKey = apiKey;
        setAuthenticated(true);
    }

    /**
     * Returns the credentials..
     *
     * @return null
     */
    @Override
    public Object getCredentials()
    {
        return null;
    }

    /**
     * Returns the principal, which is the API key.
     *
     * @return the API key
     */
    @Override
    public Object getPrincipal()
    {
        return apiKey;
    }
}
