package com.bws.restgrpcforwarder.auth;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.MediaType;
import org.springframework.web.filter.GenericFilterBean;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Filter for API key authentication.
 * This class filters incoming requests and checks for a valid API key in the header.
 */
public class ApiKeyAuthFilter extends GenericFilterBean {

    private String headerName;
    private String accessKey;

    /**
     * Constructs an ApiKeyAuthFilter with the provided header name and API key.
     *
     * @param headerName the name of the header containing the API key
     * @param apiKey the API key
     */
    public ApiKeyAuthFilter(String headerName, String apiKey) {
        this.headerName = headerName;
        this.accessKey = apiKey;
    }

    /**
     * Filters incoming requests and checks for a valid API key.
     *
     * @param request the ServletRequest
     * @param response the ServletResponse
     * @param filterChain the FilterChain
     * @throws IOException if an input or output error occurs
     * @throws ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException
    {
        try
        {
            String apiKey = ((HttpServletRequest) request).getHeader(headerName);
            if (apiKey == null || !apiKey.equals(accessKey))
            {
                throw new BadCredentialsException("Invalid API Key");
            }
            SecurityContextHolder.getContext().setAuthentication(new ApiKeyAuthenticationToken(apiKey, AuthorityUtils.NO_AUTHORITIES));
        } catch (Exception exp)
        {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            PrintWriter writer = httpResponse.getWriter();
            writer.print(exp.getMessage());
            writer.flush();
            writer.close();
        }
        filterChain.doFilter(request, response);
    }
}
