package com.gitlab.emradbuba.learning.learningproject.api.controller.request.additionals;

import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;

public interface LPAdditionalRequestInfoFinder {
    Map<String, String> find(ServletRequestAttributes requestAttributes);

    DefaultLPAdditionalRequestInfoFinder DEFAULT = new DefaultLPAdditionalRequestInfoFinder();
}
