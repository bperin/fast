package com.fast.api.service

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService
import com.amazonaws.services.simpleemail.model.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.SpringTemplateEngine

@Service
class AwsSesService @Autowired constructor(
    private val emailService: AmazonSimpleEmailService, private val templateEngine: SpringTemplateEngine, @param:Value("\${email.sender}") private val sender: String, @param:Value("\${host.url}") private val hostUrl: String
) {

    private val log = LoggerFactory.getLogger(AwsSesService::class.java)

    companion object {
        private const val CHAR_SET = "UTF-8"
        private const val EMAIL_VERIFICATION = "FAST – Email Address Verification Request"
        private const val PASSWORD_RESET = "FAST – Password Reset Request"
    }

    /**
     * This method send verification email using the aws ses sdk
     * @param email email
     */
    fun sendEmailVerification(email: String, token: String) {

        val link = "${hostUrl}/api/v1/auth/confirm_email/?token=${token}"

        val params: MutableMap<String, Any> = mutableMapOf()
        params["link"] = link

        val context = Context()
        context.setVariables(params)

        val html = templateEngine.process("verification-email-template", context)

        sendEmail(email, EMAIL_VERIFICATION, html)
    }

    /**
     * Send user verification to Fast team
     * @param email email
     */
    fun sendPasswordReset(email: String, token: String) {

        val params: MutableMap<String, Any> = mutableMapOf()

        params["token"] = token

        val context = Context()
        context.setVariables(params)

        val html = templateEngine.process("reset-password-template", context)

        sendEmail(email, PASSWORD_RESET, html)
    }

    /**
     * This method send email using the aws ses sdk
     * @param email email
     * @param body body
     */
    fun sendEmail(email: String?, subject: String, body: String?) {
        try {
            // The time for request/response round trip to aws in milliseconds
            val requestTimeout = 3000
            val request = SendEmailRequest()
                .withDestination(
                    Destination().withToAddresses(email)
                )
                .withMessage(
                    Message()
                        .withBody(
                            Body()
                                .withHtml(
                                    Content()
                                        .withCharset(CHAR_SET).withData(body)
                                )
                        )
                        .withSubject(
                            Content()
                                .withCharset(CHAR_SET).withData(subject)
                        )
                )
                .withSource(sender).withSdkRequestTimeout<SendEmailRequest>(requestTimeout)
            emailService.sendEmail(request)
        } catch (e: RuntimeException) {
            log.error(e.toString())
        }
    }
}
