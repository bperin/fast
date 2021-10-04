package com.fast.api.security

import com.fast.api.util.Constants
import javax.servlet.http.HttpServletRequest

object TokenUtil {

    fun extractToken(request: HttpServletRequest): String? {

        val authHeader = request.getHeader(Constants.AUTHORIZATION)

        if (authHeader == null || !authHeader.startsWith(Constants.BEARER + " ")) {
            return null
        }
        return authHeader.substring(7)
    }
}