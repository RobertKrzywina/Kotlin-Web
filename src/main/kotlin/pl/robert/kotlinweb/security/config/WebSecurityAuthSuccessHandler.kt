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
        val savedRequest = requestCache.getRequest(request, response)
        if (savedRequest == null) {
            clearAuthenticationAttributes(request)
            return
        }
        val parameter = request.getParameter(targetUrlParameter)
        val ok = isAlwaysUseDefaultTargetUrl || targetUrlParameter != null && StringUtils.hasText(parameter)
        if (ok) {
            requestCache.removeRequest(request, response)
            clearAuthenticationAttributes(request)
            return
        }
        clearAuthenticationAttributes(request)
    }
}
