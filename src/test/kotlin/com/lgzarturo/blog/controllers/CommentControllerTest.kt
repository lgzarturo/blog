package com.lgzarturo.blog.controllers

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CommentControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun itShouldTestGetAllComments() {
        val mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/comments"))
            .andExpect(MockMvcResultMatchers.request().asyncStarted())
            .andDo(MockMvcResultHandlers.log())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        mockMvc.perform(asyncDispatch(mvcResult))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(content().string("[1,2,3,4,5,6,7,8,9,10,11,12]"))
    }

    @Test
    fun itShouldGetAllCommentsTestWithStream_thenReturnOk() {
        val mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/comments/stream"))
            .andExpect(MockMvcResultMatchers.request().asyncStarted())
            .andDo(MockMvcResultHandlers.log())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        mockMvc.perform(asyncDispatch(mvcResult))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON_VALUE))
    }
}
