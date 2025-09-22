package com.project.enotes_api_service.config;

import com.project.enotes_api_service.entity.User;
import com.project.enotes_api_service.util.CommonUtil;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditAwareConfig implements AuditorAware<Integer> {
    @Override
    public Optional<Integer> getCurrentAuditor() {
        return Optional.ofNullable(CommonUtil.getLoggedInUser())
                .map(User::getId);
    }
}
