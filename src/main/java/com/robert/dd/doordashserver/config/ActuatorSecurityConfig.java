package com.robert.dd.doordashserver.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.context.ShutdownEndpoint;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.trace.http.HttpTraceEndpoint;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class ActuatorSecurityConfig extends WebSecurityConfigurerAdapter {

    // source : https://github.com/callicoder/spring-boot-actuator-demo/blob/master/src/main/java/com/example/actuatordemo/config/ActuatorSecurityConfig.java
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    //.requestMatchers(EndpointRequest.toAnyEndpoint())
                    //    .permitAll()
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                        .permitAll()
                    .requestMatchers(EndpointRequest.to(HealthEndpoint.class)).permitAll()
                    .antMatchers("/", "/api/**")
                        .permitAll()
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                ;
    }
}