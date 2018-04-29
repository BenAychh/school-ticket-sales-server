package io.benaychh.ticketsales

import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Nested
import java.util.*

internal class ApiGatewayResponseTest {

    @Nested
    inner class DefaultConstructor {
        @Test
        fun `it defaults to a status code of 200`() {
            assertThat(ApiGatewayResponse().statusCode).isEqualTo(200)
        }

        @Test
        fun `it defaults to a null string body`() {
            assertThat(ApiGatewayResponse().body).isNull()
        }

        @Test
        fun `it defaults to empty headers`() {
            assertThat(ApiGatewayResponse().headers?.size).isEqualTo(0)
        }

        @Test
        fun `it defaults to false for base64 encoding`() {
            assertThat(ApiGatewayResponse().isBase64Encoded).isFalse()
        }

    }

    @Nested
    inner class builder {
        @Test
        fun `it defaults to a status code of 200`() {
            assertThat(ApiGatewayResponse.build { }.statusCode).isEqualTo(200)
        }

        @Test
        fun `it defaults to a null string body`() {
            assertThat(ApiGatewayResponse.build { }.body).isNull()
        }

        @Test
        fun `it defaults to empty headers`() {
            assertThat(ApiGatewayResponse.build { }.headers?.size).isEqualTo(0)
        }

        @Test
        fun `it defaults to false for base64 encoding`() {
            assertThat(ApiGatewayResponse.build { }.isBase64Encoded).isFalse()
        }

        @Test
        fun `it can set a status code`() {
            assertThat(ApiGatewayResponse.build { statusCode = 404 }.statusCode).isEqualTo(404)
        }

        @Test
        fun `it can set base64Encoded`() {
            assertThat(ApiGatewayResponse.build { base64Encoded = true }.isBase64Encoded).isTrue()
        }


        @Test
        fun `it sets the body as a raw string if rawBody is not null`() {
            assertThat(ApiGatewayResponse.build { rawBody = "Test String" }.body).isEqualTo("Test String")
        }

        @Test
        fun `it sets the body as a json string if there is no raw body and objectBody is valid`() {
            val responseBody = Response("Test", Collections.emptyMap())
            assertThat(ApiGatewayResponse.build { objectBody = responseBody }.body)
                    .isEqualTo("{\"message\":\"Test\",\"input\":{}}")
        }

        @Test
        fun `it sets the body as a binary if both rawBody and objectBody are null and binaryBody is not`() {
            val localBody = ByteArray(2)
            assertThat(ApiGatewayResponse.build { binaryBody = localBody }.body).isEqualTo("AAA=")
        }

        @Test
        fun `it prefers rawBody to objectBody`() {
            val responseBody = Response("Test", Collections.emptyMap())
            assertThat(ApiGatewayResponse.build {
                rawBody = "Test String"
                objectBody = responseBody
            }.body).isEqualTo("Test String")
        }

        @Test
        fun `it prefers rawBody to binaryBody`() {
            val localBody = ByteArray(2)
            assertThat(ApiGatewayResponse.build {
                rawBody = "Test String"
                binaryBody = localBody
            }.body).isEqualTo("Test String")
        }

        @Test
        fun `it prefers objectBody to binaryBody`() {
            val responseBody = Response("Test", Collections.emptyMap())
            val localBody = ByteArray(2)
            assertThat(ApiGatewayResponse.build {
                objectBody = responseBody
                binaryBody = localBody
            }.body).isEqualTo("{\"message\":\"Test\",\"input\":{}}")
        }

        @Test
        fun `it sets the body as a binary if the other bodies are null and binaryBody is not`() {

        }
    }
}