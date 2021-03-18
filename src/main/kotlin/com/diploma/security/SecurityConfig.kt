package com.diploma.security

import com.diploma.security.jwt.JwtTokenFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Configuration
@EnableWebSecurity
class SecurityConfig :
    WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var jwtTokenFilter: JwtTokenFilter

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun customAccessDeniedHandler(): CustomAccessDeniedHandler {
        return CustomAccessDeniedHandler()
    }

    override fun configure(http: HttpSecurity) {
        http
            .cors().and()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .logout().permitAll()
            .and()
            .authorizeRequests()
            .antMatchers("/auth/**", "/").permitAll()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated()
            .and()
            .exceptionHandling().accessDeniedHandler { request, response, accessDeniedException ->
                response.sendError(HttpServletResponse.SC_FORBIDDEN)
            }
            .and().exceptionHandling().authenticationEntryPoint { request, response, authException ->
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
            }
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}


//@Component
//class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {
//    override fun commence(
//        request: HttpServletRequest,
//        response: HttpServletResponse,
//        authException: AuthenticationException
//    ) {
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,  "suffused")
//    }
//}

class CustomAccessDeniedHandler : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        println("ERROR")
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "suffused")
    }
}