package security


import com.cusp.app.security.TokenService
import com.fast.api.util.Constants
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureException
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtFilter(private val tokenService: TokenService) : GenericFilterBean() {

    override fun doFilter(req: ServletRequest, res: ServletResponse, chain: FilterChain) {

        val request = req as HttpServletRequest
        val response = res as HttpServletResponse

        val path = request.servletPath

        val token = TokenUtil.extractToken(request as HttpServletRequest)

        if (token != null && token.isNotEmpty()) {
            try {
                tokenService.getClaims(token)
                chain.doFilter(request, response)
            } catch (e: SignatureException) {
                response.setContentType(Constants.APPLICATION_JSON);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token " + path);
            } catch (e: MalformedJwtException) {
                response.setContentType(Constants.APPLICATION_JSON);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token " + path);
            } catch (e: ExpiredJwtException) {
                response.setContentType(Constants.APPLICATION_JSON);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED, "Expired JWT token " + path);
            } catch (e: UnsupportedJwtException) {
                response.setContentType(Constants.APPLICATION_JSON);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED, "Unsupported JWT token " + path);
            } catch (e: IllegalArgumentException) {
                response.setContentType(Constants.APPLICATION_JSON);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED, "Empty JWT token" + path);
            }
        } else {
            response.setContentType(Constants.APPLICATION_JSON);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED, "No JWT token " + path);
        }
    }
}