package com.lgzarturo.blog

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder

inline fun <reified T> ResultActions.bodyTo(mapper: ObjectMapper = jacksonObjectMapper()): T {
   return mapper.readValue(this.andReturn().response.contentAsByteArray, object : TypeReference<T>() {})
}


fun MockHttpServletRequestBuilder.body(data: Any, mapper: ObjectMapper = jacksonObjectMapper(), mediaType: MediaType = MediaType.APPLICATION_JSON): MockHttpServletRequestBuilder {
    return this.content(mapper.writeValueAsBytes(data)).contentType(mediaType)
}
