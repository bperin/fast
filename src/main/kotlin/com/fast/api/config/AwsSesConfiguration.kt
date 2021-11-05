package com.fast.api.config

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class AwsSesConfiguration(@param:Value("\${email.region}") private val region: String) {
    /**
     * Build the AWS ses client
     * @return AmazonSimpleEmailServiceClientBuilder
     * we would NEVER hard code these values, they would be stored
     * as ENV vars, but since you
     * dont have access to environment variables for this server
     * this will do for demo purposes
     */
    @Bean
    fun amazonSimpleEmailService(): AmazonSimpleEmailService {

        val credentialsProvider: AWSCredentialsProvider = object : AWSCredentialsProvider {
            override fun refresh() {}
            override fun getCredentials(): AWSCredentials {
                return object : AWSCredentials {
                    override fun getAWSSecretKey(): String {
                        return "Lhxg4DyWTQp1/6DmQ7peK45EQ9BRZ2P+C8UZLs0e"
                    }

                    override fun getAWSAccessKeyId(): String {
                        return "AKIA4W6XHFEGWWTN42F5"
                    }
                }
            }
        }
        return AmazonSimpleEmailServiceClientBuilder.standard().withRegion(region).withCredentials(credentialsProvider).build()
    }
}
