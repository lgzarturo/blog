package com.lgzarturo.blog

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.test.web.servlet.ResultActions

inline fun <reified T> ResultActions.bodyTo(mapper: ObjectMapper = jacksonObjectMapper()): T {
   return mapper.readValue(this.andReturn().response.contentAsByteArray, object : TypeReference<T>() {})
}
