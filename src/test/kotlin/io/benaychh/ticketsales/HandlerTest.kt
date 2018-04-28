package io.benaychh.ticketsales

import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.*


internal class HandlerTest {

    val testContext = contextFactory()
    val input = HashMap<String, String>()

    @Test
    fun `responds with a 200 status code`() {
        val handler = Handler()
        val results = handler.handleRequest(input, testContext)
        assertThat(results.statusCode).isEqualTo(200)
    }

    @Test
    fun `has the correct body as response`() {
        val handler = Handler()
        val results = handler.handleRequest(input, testContext)
        val expected = "{\"message\":\"Go Serverless v1.x! Your Kotlin function executed successfully!\",\"input\":{}}"
        assertThat(results.body).isEqualTo(expected)
    }
}