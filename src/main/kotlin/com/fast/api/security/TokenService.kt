package com.fast.api.security

import com.fast.api.model.RefreshToken
import com.fast.api.model.User
import com.fast.api.model.request.RefreshTokenRequest
import com.fast.api.repo.RefreshTokensRepo
import com.fast.api.util.Constants
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
    lateinit var secret: String

    @Value("\${primary.owner.email}")
    lateinit var primaryOwnerEmail: String

    @Autowired
    private lateinit var refreshTokensRepo: RefreshTokensRepo

    /**
     * Creates a new auth token and refresh token removes old refresh token from database
     */
    fun login(user: User, response: HttpServletResponse) {

        createRefreshToken(user, response)
        createAuthToken(user, response)

        return
    }

    /**
     * Creates a new auth token and refresh token removes old refresh token from database
     * If the user is our pre defined primary owner set them as owner
     */
    private fun createAuthToken(user: User, response: HttpServletResponse) {

        val expDate = DateTime().plusHours(2)
        val subject: String? = null
        val claimMap: MutableMap<String, Any> = mutableMapOf()

        subject.apply {
            user.id
        }

        if (user.email === primaryOwnerEmail) {
            claimMap[Constants.ADMIN] = true
            claimMap[Constants.OWNER] = true
        }
        if (!user.owner) {
            claimMap[Constants.VIEWER] = true
        } else {
            claimMap[Constants.OWNER] = true
        }

        val authJwt = Jwts.builder()
            .setSubject(subject)
            .setId(UUID.randomUUID().toString())
            .setClaims(claimMap)
            .setIssuedAt(Date())
            .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secret.toByteArray()))
            .setExpiration(expDate.toDate())
            .compact()

        response.addHeader(Constants.JWT_TOKEN_HEADER, authJwt)
        response.addHeader(Constants.JWT_TOKEN_EXPIRATION_HEADER, expDate.millis.toString())
    }

    private fun createRefreshToken(user: User, response: HttpServletResponse) {

        val expDate = DateTime().plusYears(1)
        var subject: String? = null

        subject.apply {
            subject = user.id.toString()
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
        refreshToken.user = user

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
                try {
                    refreshToken.user?.let {
                        response.setContentType("application/json")
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN, "JWT token error")
                        return
                    }

                    createAuthToken(it.user!!, response)

                } catch (e: SignatureException) {
                    response.setContentType("application/json")
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN, "Invalid JWT token")

                } catch (e: MalformedJwtException) {
                    response.setContentType("application/json")
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN, "Invalid JWT token")

                } catch (e: ExpiredJwtException) {
                    response.setContentType("application/json")
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN, "Expired JWT token")

                } catch (e: UnsupportedJwtException) {
                    response.setContentType("application/json")
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN, "Unsupported JWT token")

                } catch (e: IllegalArgumentException) {
                    response.setContentType("application/json")
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN, "Empty JWT token")

                }
            } ?: kotlin.run {
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
