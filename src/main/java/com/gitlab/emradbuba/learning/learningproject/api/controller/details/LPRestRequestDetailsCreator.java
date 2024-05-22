package com.gitlab.emradbuba.learning.learningproject.api.controller.details;

import com.gitlab.emradbuba.learning.learningproject.api.model.request.LPRestRequestDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class LPRestRequestDetailsCreator {
    private static final String INFO_UNAVAILABLE = "<unavailable>";

    public LPRestRequestDetails createRequestDetails() {
        return Optional.of(RequestContextHolder.currentRequestAttributes())
                .filter(ServletRequestAttributes.class::isInstance)
                .map(ServletRequestAttributes.class::cast)
                .map(LPRestRequestDetailsCreator::fromServletRequestAttributes)
                .orElse(LPRestRequestDetails.NULL_OBJECT);
    }

    private static LPRestRequestDetails fromServletRequestAttributes(final ServletRequestAttributes requestAttributes) {
        return LPRestRequestDetails.builder()
                .requestStartTime(LocalDateTime.now())
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
                .map(LPRestRequestDetailsCreator::retrieveRoleFromPrinciple)
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

    private static Map<String, String> retrieveAdditionalDetails(final ServletRequestAttributes requestAttributes) {
        return new HashMap<String, String>() {{
            put("test-param1", "<not supported yet>");
            put("test-param2", "<not supported yet>");
        }};
    }
}