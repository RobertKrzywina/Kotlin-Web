package pl.robert.kotlinweb.security

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.springframework.stereotype.Component

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint

@Component
class WebSecurityEntryPoint : AuthenticationEntryPoint {

    override fun commence(request: HttpServletRequest?,
                          response: HttpServletResponse?,
                          authException: AuthenticationException?) {
        response?.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access denied!")
    }
}
