package com.lgzarturo.blog.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.lgzarturo.blog.body
import com.lgzarturo.blog.bodyTo
import com.lgzarturo.blog.models.dtos.CategoryRequest
import com.lgzarturo.blog.models.entities.Post
import com.lgzarturo.blog.services.AuthorService
import com.lgzarturo.blog.services.impl.JpaPostService
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.streams.asSequence

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Autowired
    private lateinit var modelMapper: ModelMapper

    @Autowired
    private lateinit var postService: JpaPostService

    @Autowired
    private lateinit var authorService: AuthorService

    private val endpointUri = "/posts"

    @Test
    fun itShouldList_allPosts() {
        val postsFromService = postService.all().map { it.id }
        val categories = mockMvc.perform(MockMvcRequestBuilders.get(endpointUri))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .bodyTo<List<Post>>(mapper)
        MatcherAssert.assertThat(postsFromService, Matchers.`is`(Matchers.equalTo(categories.map { it.id })))
    }

    @Test
    fun itShouldFinById() {
        val postsFromService = postService.all()
        assert(postsFromService.isNotEmpty())
        val post = postsFromService.first()
        mockMvc.perform(MockMvcRequestBuilders.get("$endpointUri/${post.id}"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.`is`(post.title)))
    }

    @Test
    fun itShouldFinById_notFound() {
        mockMvc.perform(MockMvcRequestBuilders.get("$endpointUri/99181771"))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist())
    }

    @Test
    fun itShouldCreatePost(): Post {
        val author = authorService.getAll().first()
        val randomString = randomString()
        val post = Post(
            title = "Web Development $randomString",
            slug = "web-dev-${randomString.lowercase()}",
            summary = "web development programming",
            author = author
        )
        val result = mockMvc.perform(MockMvcRequestBuilders.post(endpointUri).body(data = post, mapper = mapper))
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .bodyTo<Post>(mapper)
        assertNotNull(result)
        return result
    }

    @Test
    fun itShouldCreatePost_fail() {
        val postsFromService = postService.all()
        assert(postsFromService.isNotEmpty())
        val post = postsFromService.first()
        val categoryRequest = modelMapper.map(post, CategoryRequest::class.java)
        mockMvc.perform(MockMvcRequestBuilders.post(endpointUri).body(data = categoryRequest, mapper = mapper))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    @Disabled
    fun itShouldUpdatePost() {
        val postsFromService = postService.all()
        assert(postsFromService.isNotEmpty())
        val post = postsFromService.first().copy(title = "Nueva categoría")
        val result = mockMvc.perform(
            MockMvcRequestBuilders.put("$endpointUri/${post.id}").body(data = post, mapper = mapper))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .bodyTo<Post>(mapper)
        assertNotNull(result)
    }

    @Test
    fun itShouldUpdatePost_fail() {
        val postsFromService = postService.all()
        assert(postsFromService.isNotEmpty())
        val post = postsFromService.first().copy(title = "Nueva categoría")
        mockMvc.perform(MockMvcRequestBuilders.put("$endpointUri/121213").body(data = post, mapper = mapper))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    @Disabled
    fun itShouldDeletePost() {
        val post = itShouldCreatePost()
        val result = mockMvc.perform(MockMvcRequestBuilders.delete("$endpointUri/${post.id}"))
            .andExpect(MockMvcResultMatchers.status().isNoContent)
        assertNotNull(result)
        assert(!postService.all().contains(post))
    }


    @Test
    fun itShouldDeleteCategory_badRequest() {
        mockMvc.perform(MockMvcRequestBuilders.delete("$endpointUri/${UUID.randomUUID()}"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    fun randomString() = ThreadLocalRandom.current()
        .ints(10.toLong(), 0, charPool.size)
        .asSequence()
        .map(charPool::get)
        .joinToString("")
}
