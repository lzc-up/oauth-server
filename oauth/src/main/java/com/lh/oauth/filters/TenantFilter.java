package com.lh.oauth.filters;

import io.micrometer.common.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.boot.web.servlet.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.nio.file.*;

@Component
public class TenantFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String tenantId = request.getHeader("X-Tenant-Id");
        boolean hasAccess = isUserAllowed(tenantId);
        if (hasAccess) {
            filterChain.doFilter(request, response);
            return;
        }
        throw new AccessDeniedException("Access denied");
    }

    private boolean isUserAllowed(String tenantId) {
        return StringUtils.isBlank(tenantId);
    }


    @Bean
    public FilterRegistrationBean<TenantFilter> tenantFilterRegistration(TenantFilter filter) {
        FilterRegistrationBean<TenantFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(true);
        registration.setOrder(Integer.MAX_VALUE);
        return registration;
    }
}