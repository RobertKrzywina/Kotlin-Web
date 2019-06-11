package pl.robert.kotlinweb.security

import java.util.Arrays

import pl.robert.kotlinweb.user.domain.UserService

import org.springframework.context.annotation.Bean
import org.springframework.security.access.vote.RoleVoter
import org.springframework.context.annotation.Configuration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.vote.UnanimousBased
import org.springframework.security.access.AccessDecisionManager
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.access.vote.AuthenticatedVoter
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.access.expression.WebExpressionVoter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder

@Configuration
@EnableWebSecurity
class WebSecurityConfiguration @Autowired
constructor(
        val service: UserService,
        val unauthorizedHandler: AuthenticationEntryPoint,
        val successHandler: WebSecurityAuthSuccessHandler)
    : WebSecurityConfigurerAdapter() {

    @Autowired
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authenticationProvider())
    }

    override fun configure(http: HttpSecurity?) {
        http
            ?.csrf()?.disable()
            ?.exceptionHandling()
            ?.authenticationEntryPoint(unauthorizedHandler)

            ?.and()

            ?.authorizeRequests()
            ?.antMatchers("/api/task*")?.authenticated()
            ?.antMatchers("/api/user**")?.hasAnyAuthority("USER")
            ?.antMatchers("/api/user/save")?.permitAll()

            ?.and()

            ?.formLogin()
            ?.successHandler(successHandler)
            ?.failureHandler(SimpleUrlAuthenticationFailureHandler())

            ?.and()

            ?.logout()
    }

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(service)
        authProvider.setPasswordEncoder(encoder())
        return authProvider
    }

    @Bean
    fun encoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun accessDecisionManager(): AccessDecisionManager {
        val decisionVoters = Arrays.asList(
                WebExpressionVoter(),
                RoleVoter(),
                AuthenticatedVoter()
        )
        return UnanimousBased(decisionVoters)
    }
}
