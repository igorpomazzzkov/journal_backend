package com.diploma.security

import com.diploma.security.jwt.JwtTokenFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.servlet.http.HttpServletResponse


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
class SecurityConfig :
    WebSecurityConfigurerAdapter(), WebMvcConfigurer {

    @Autowired
    private lateinit var jwtTokenFilter: JwtTokenFilter

    @Value("\${allowed.origins.front}")
    private lateinit var origins: String

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins(origins)
            .allowedMethods("*")
    }

    @Throws(RuntimeException::class)
    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .cors().and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .logout().permitAll()
            .and()
            .authorizeRequests()
            .antMatchers(
                "/auth/**",
                "/swagger-resources/**",
                "/favicon.ico",
                "/*.html",
                "/**/*.html",
                "/webjars/springfox-swagger-ui/**",
                "/v2/**",
                "/",
                "/groups/names"
            ).permitAll()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated()
            .and().exceptionHandling().accessDeniedHandler { request, response, accessDeniedException ->
                response.sendError(HttpServletResponse.SC_FORBIDDEN)
            }
            .and().exceptionHandling().authenticationEntryPoint { request, response, authException ->
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
            }
        http.addFilterBefore(
            jwtTokenFilter,
            UsernamePasswordAuthenticationFilter::class.java
        )
    }
}
