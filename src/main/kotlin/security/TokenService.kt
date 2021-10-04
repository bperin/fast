package com.cusp.app.security

import com.cusp.app.model.Office
import com.cusp.app.model.RefreshToken
import com.cusp.app.model.Worker
import com.cusp.app.model.request.RefreshTokenRequest
import com.cusp.app.repo.RefreshTokensRepo
import com.cusp.app.util.Constants
import io.jsonwebtoken.*
import org.joda.time.DateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletResponse

@Component
class TokenService {

    @Value("\${jwt.secret}")
    val secret = "secret"

//    @Autowired
//    private lateinit var refreshTokensRepo: RefreshTokensRepo //TODO refresh tokens

    /**
     * Creates a new auth token and refresh token removes old refresh token from database
     */


    private fun createAuthToken(worker: Worker?, office: Office?, response: HttpServletResponse) {

        val expDate = DateTime().plusYears(5)
        var subject: String? = null
        var role: String? = null

        if (worker != null) {
            subject = worker.id.toString()
            role = Constants.WORKER
        }
        if (office != null) {
            subject = office.id.toString()
            role = Constants.OFFICE
        }

        val authJwt = Jwts.builder()
                .setSubject(subject)
                .setId(UUID.randomUUID().toString())
                .claim(Constants.ROLES, role)
                .setIssuedAt(Date())
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secret.toByteArray()))
                .setExpiration(expDate.toDate())
                .compact()

        response.addHeader(Constants.JWT_TOKEN_HEADER, authJwt)
        response.addHeader(Constants.JWT_TOKEN_EXPIRATION_HEADER, expDate.millis.toString())
    }

    private fun createRefreshToken(worker: Worker?, office: Office?, response: HttpServletResponse) {

        val expDate = DateTime().plusYears(5)
        var subject: String? = null

        if (worker != null) {
            subject = worker.id.toString()
            refreshTokensRepo.deleteForWorker(worker.id)
        }
        if (office != null) {
            subject = office.id.toString()
            refreshTokensRepo.deleteForOffice(office.id)
        }

        val refreshJwt = Jwts.builder()
                .setSubject(subject)
                .setId(UUID.randomUUID().toString())
                .claim(Constants.ROLES, "refresh_token")
                .setIssuedAt(Date())
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secret.toByteArray()))
                .setExpiration(expDate.toDate())
                .compact()


        val refreshToken = RefreshToken()
        refreshToken.token = refreshJwt
        refreshToken.worker = worker
        refreshToken.office = office

        refreshTokensRepo.save(refreshToken)

        response.addHeader(Constants.JWT_REFRESH_TOKEN_HEADER, refreshJwt)
        response.addHeader(Constants.JWT_REFRESH_TOKEN_EXPIRATION_HEADER, expDate.millis.toString())
    }

    /**
     * Create a new auth token given an old auth token
     */
    fun refreshToken(request: RefreshTokenRequest, response: HttpServletResponse) {

        request.refreshToken.let { inputToken ->

            val refreshToken = refreshTokensRepo.getRefreshToken(inputToken)

            refreshToken?.let {

                val worker: Worker? = it.worker
                val office: Office? = it.office

//                if (request.userId != refreshToken.user!!.id.toString()) {
//                    analyticsService.refreshFailure("user id mismatch to refresh token", token, inputToken)
//                    response.setContentType("application/json")
//                    response.setStatus(HttpServletResponse.SC_FORBIDDEN, "User id mismatch")
//
//                    return
//                }
                try {

                    createAuthToken(worker, office, response)

                } catch (e: SignatureException) {
//                    analyticsService.refreshFailure("signature exception", token, inputToken)
                    response.setContentType("application/json")
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN, "Invalid JWT token")

                } catch (e: MalformedJwtException) {
//                    analyticsService.refreshFailure("malformed JWT", token, inputToken)
                    response.setContentType("application/json")
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN, "Invalid JWT token")

                } catch (e: ExpiredJwtException) {
//                    analyticsService.refreshFailure("expired JWT", token, inputToken)
                    response.setContentType("application/json")
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN, "Expired JWT token")

                } catch (e: UnsupportedJwtException) {
//                    analyticsService.refreshFailure("unsupported jwt", token, inputToken)
                    response.setContentType("application/json")
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN, "Unsupported JWT token")

                } catch (e: IllegalArgumentException) {
//                    analyticsService.refreshFailure("empty JWT,token", token, inputToken)
                    response.setContentType("application/json")
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN, "Empty JWT token")

                }
            } ?: kotlin.run {
//                analyticsService.refreshFailure("refresh not found in DB", "token null on ? check", inputToken)
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN, "Empty JWT token");

            }
        }
    }

    /**
     * Get all claims for the JWT
     */
    fun getClaims(token: String): Claims {
        return Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(secret.toByteArray()))
                .parseClaimsJws(token).body
    }
}
