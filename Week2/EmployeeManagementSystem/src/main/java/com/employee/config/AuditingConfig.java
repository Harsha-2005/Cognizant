package com.employee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

/**
 * Exercise 7: Enable JPA auditing.
 *
 * @EnableJpaAuditing tells Spring Data to start filling in the
 * @CreatedDate, @LastModifiedDate, @CreatedBy, @LastModifiedBy fields.
 *
 * AuditorAwareImpl provides the "current user" — in a real app you'd
 * pull this from the security context (e.g., SecurityContextHolder).
 * For this exercise we just hardcode a system user.
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditingConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        // In production: return () -> Optional.ofNullable(SecurityContextHolder
        //     .getContext().getAuthentication().getName());
        return () -> Optional.of("system");
    }
}
