package io.benaychh.ticketsales

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.log4j.Logger
import java.nio.charset.StandardCharsets
import java.util.Collections
import java.util.Base64

class ApiGatewayResponse(
    val statusCode: Int = 200,
    var body: String? = null,
    val headers: Map<String, String>? = Collections.emptyMap(),
    val isBase64Encoded: Boolean = false
) {
    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        var LOG: Logger = Logger.getLogger(Builder::class.java)
        var objectMapper: ObjectMapper = ObjectMapper()

        var statusCode: Int = 200
        var rawBody: String? = null
        var headers: Map<String, String>? = Collections.emptyMap()
        var objectBody: Response? = null
        var binaryBody: ByteArray? = null
        var base64Encoded: Boolean = false

        fun build(): ApiGatewayResponse {
            var body: String? = null

            if (rawBody != null) {
                body = rawBody as String
            } else if (objectBody != null) {
                try {
                    body = objectMapper.writeValueAsString(objectBody)
                } catch (e: JsonProcessingException) {
                    LOG.error("failed to serialize object", e)
                    throw RuntimeException(e)
                }
            } else if (binaryBody != null) {
                body = String(Base64.getEncoder().encode(binaryBody), StandardCharsets.UTF_8)
            }
            return ApiGatewayResponse(statusCode, body, headers, base64Encoded)
        }
    }
}