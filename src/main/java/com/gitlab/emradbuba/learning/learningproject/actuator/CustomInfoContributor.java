package com.gitlab.emradbuba.learning.learningproject.actuator;

import com.gitlab.emradbuba.learning.learningproject.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomInfoContributor implements InfoContributor {

    @Value("${info.custom.static.information:noInfo}")
    private String customInfoFromProperties;
    @Value("${info.build.version}")
    private String version;
    private final PersonService personService;

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, String> information = new HashMap<>();
        information.put("version", version);
        information.put("description", customInfoFromProperties);
        information.put("personCount", "" + personService.countPerson());
        builder.withDetail("personInfo", information);
    }
}
