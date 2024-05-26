package com.gitlab.emradbuba.learning.learningproject.api.controller.request;

import com.gitlab.emradbuba.learning.learningproject.api.controller.request.additionals.LPAdditionalRequestInfoFinder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor
public class LPRestRequestContextCreator {
    private static final String INFO_UNAVAILABLE = "<unavailable>";

    private LocalDateTime requestTime = LocalDateTime.now();
    private LPAdditionalRequestInfoFinder additionalRequestInfoFinder = LPAdditionalRequestInfoFinder.DEFAULT;

    public LPRestRequestContextCreator withRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
        return this;
    }

    public LPRestRequestContextCreator withAdditionalRequestInfoFinder(LPAdditionalRequestInfoFinder additionalRequestInfoFinder) {
        this.additionalRequestInfoFinder = additionalRequestInfoFinder;
        return this;
    }


    public LPRestRequestContext create() {
        return Optional.of(RequestContextHolder.currentRequestAttributes())
                .filter(ServletRequestAttributes.class::isInstance)
                .map(ServletRequestAttributes.class::cast)
                .map(this::fromServletRequestAttributes)
                .orElse(LPRestRequestContext.NULL_OBJECT);
    }

    private LPRestRequestContext fromServletRequestAttributes(final ServletRequestAttributes requestAttributes) {
        return LPRestRequestContext.builder()
                .requestStartTime(this.requestTime)
                .endpointType(retrieveEndpointType(requestAttributes))
                .endpointPath(retrieveEndpointPath(requestAttributes))
                .username(retrieveUsername(requestAttributes))
                .userRole(retrievedRole(requestAttributes))
                .additionalDetails(retrieveAdditionalDetails(requestAttributes))
                .build();
    }

    private static String retrieveEndpointType(final ServletRequestAttributes requestAttributes) {
        return Optional.of(requestAttributes.getRequest())
                .map(HttpServletRequest::getMethod)
                .orElse(INFO_UNAVAILABLE);
    }

    private static String retrieveEndpointPath(final ServletRequestAttributes requestAttributes) {
        return Optional.of(requestAttributes.getRequest())
                .map(HttpServletRequest::getServletPath)
                .orElse(INFO_UNAVAILABLE);
    }

    private static String retrieveUsername(final ServletRequestAttributes requestAttributes) {
        return Optional.of(requestAttributes.getRequest())
                .map(HttpServletRequest::getUserPrincipal)
                .map(Principal::getName)
                .orElse(INFO_UNAVAILABLE);
    }

    private static String retrievedRole(final ServletRequestAttributes requestAttributes) {
        return Optional.of(requestAttributes.getRequest())
                .map(HttpServletRequest::getUserPrincipal)
                .map(LPRestRequestContextCreator::retrieveRoleFromPrinciple)
                .orElse(INFO_UNAVAILABLE);
    }

    private static String retrieveRoleFromPrinciple(Principal principal) {
        return Optional.of(principal)
                .filter(UsernamePasswordAuthenticationToken.class::isInstance)
                .map(UsernamePasswordAuthenticationToken.class::cast)
                .map(UsernamePasswordAuthenticationToken::getAuthorities)
                .stream()
                .flatMap(Collection::stream)
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));
    }

    private Map<String, String> retrieveAdditionalDetails(ServletRequestAttributes requestAttributes) {
        return Optional.ofNullable(additionalRequestInfoFinder)
                .map(attributesFinder -> attributesFinder.find(requestAttributes))
                .orElse(Collections.emptyMap());
    }
}