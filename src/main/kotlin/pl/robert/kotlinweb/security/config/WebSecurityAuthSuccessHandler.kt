package pl.robert.kotlinweb.security.config

import org.springframework.util.StringUtils
import org.springframework.stereotype.Component
import org.springframework.security.core.Authentication
import org.springframework.security.web.savedrequest.HttpSessionRequestCache
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class WebSecurityAuthSuccessHandler : SimpleUrlAuthenticationSuccessHandler() {

    var requestCache = HttpSessionRequestCache()

    override fun onAuthenticationSuccess(request: HttpServletRequest,
                                         response: HttpServletResponse,
                                         authentication: Authentication) {
        if (requestCache.getRequest(request, response) == null) {
            clearAuthenticationAttributes(request)
            return
        }
        if (isAlwaysUseDefaultTargetUrl || targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter))) {
            requestCache.removeRequest(request, response)
            clearAuthenticationAttributes(request)
            return
        }
        clearAuthenticationAttributes(request)
    }
}
