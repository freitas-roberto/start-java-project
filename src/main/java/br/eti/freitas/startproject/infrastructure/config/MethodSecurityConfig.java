package br.eti.freitas.startproject.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import br.eti.freitas.startproject.infrastructure.custom.CustomPermissionEvaluator;
import br.eti.freitas.startproject.infrastructure.handler.CustomMethodSecurityExpressionHandler;

/**
 * Defines method of global security
 *
 * @author Roberto Freitas
 * @version 1.0
 * @since 2023-03-01
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {

        final CustomMethodSecurityExpressionHandler expressionHandler = new CustomMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(new CustomPermissionEvaluator());
        return expressionHandler;

    }
}