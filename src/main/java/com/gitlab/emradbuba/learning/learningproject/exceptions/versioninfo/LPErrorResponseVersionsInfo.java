package com.gitlab.emradbuba.learning.learningproject.exceptions.versioninfo;

import com.gitlab.emradbuba.learning.learningproject.libs.exceptions.core.utils.ExceptionLibVersion;
import com.gitlab.emradbuba.learning.learningproject.libs.utils.SecurityLibVersion;
import lombok.Getter;

@Getter
public class LPErrorResponseVersionsInfo {
    private final String exceptionsLibVersion = ExceptionLibVersion.getCurrentVersion();
    private final String securityLibVersion = SecurityLibVersion.getCurrentVersion();
}
