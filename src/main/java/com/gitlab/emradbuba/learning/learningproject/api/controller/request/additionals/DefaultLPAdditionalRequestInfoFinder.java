package com.gitlab.emradbuba.learning.learningproject.api.controller.request.additionals;

import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;

public class DefaultLPAdditionalRequestInfoFinder implements LPAdditionalRequestInfoFinder {
    @Override
    public Map<String, String> find(ServletRequestAttributes requestAttributes) {
        return new HashMap<String, String>() {{
            put("key1", "value1");
            put("key2", "value2");
        }};
    }
}
