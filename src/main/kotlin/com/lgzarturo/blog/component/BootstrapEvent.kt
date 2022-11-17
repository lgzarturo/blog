package com.lgzarturo.blog.component

import com.lgzarturo.blog.models.enums.PostType
import com.lgzarturo.blog.models.enums.UserType
import com.lgzarturo.blog.models.entities.*
import com.lgzarturo.blog.repositories.*
import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import java.time.LocalDate


/**
 * Este componente define un evento para que se ejecute
 * al terminar el proceso de arranque de Spring Boot.
 *
 * En el orden de arranque primero se inicia el CommandLineRunner
 * y después se invoca el evento cuando esta lista la aplicación
 * mediante el evento ApplicationReadyEvent.
 *
 * En el ejemplo del proyecto primero se ejecuta la clase
 * DevelopmentInitializer, luego la clase StartProject y por último
 * se invoca la clase BootstrapEvent.
 */
@Component
class BootstrapEvent(
    private val authorRepository: AuthorRepository,
    private val categoryRepository: CategoryRepository,
    private val commentRepository: CommentRepository,
    private val likeCounterRepository: LikeCounterRepository,
    private val postRepository: PostRepository,
    private val tagRepository: TagRepository,
    private val userRepository: UserRepository) : ApplicationListener<ApplicationReadyEvent> {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        log.info("Se ejecuta al iniciar el proyecto")
        val authorOne = Author()
        authorOne.name = "Miguel"
        authorOne.avatarImage = "https://picsum.photos/seed/picsum/200/300"
        authorOne.description = "Autor y creador de contenido"
        val authorTwo = Author()
        authorTwo.name = "John Doe"
        authorTwo.avatarImage = "https://picsum.photos/seed/picsum/200/300"
        authorTwo.description = "Escritor de novelas"
        val categoryOne = Category(title = "Programación", slug = "programming", description = "Artículos sobre programación")
        val categoryTwo = Category(title = "Diseño", slug = "design", description = "Artículos sobre diseño")
        val tagOne = Tag(
            name = "Programación",
            slug = "programming"
        )
        val tagTwo = Tag(
            name = "Administración",
            slug = "company-management"
        )
        tagRepository.save(tagOne)
        tagRepository.save(tagTwo)
        val postOne = Post(
            title = "Programar es un arte",
            slug = "programar-es-un-arte",
            coverImage = "https://picsum.photos/seed/picsum/300/200",
            summary = "Hablemos sobre la programación en general.",
            content = "<h1>Programación</h1><p>Datos importantes</p>",
            status = "active",
            postType = PostType.ARTICLE,
            hasPage = false,
            menuOrder = 0,
            author = authorOne,
            category = categoryOne,
            publishedAt = LocalDate.now()
        )
        val userOne = User(
            email = "abc@gmail.com",
            password = "12345",
            userType = UserType.AUTHOR,
            isActive = true,
            author = authorOne
        )
        val commentOne = Comment(
            post = postOne,
            author = userOne.author?.name,
            authorEmail = userOne.email,
            ipAddress = "0.0.0.0",
            content = "Buenos comentarios",
            isApproved = true
        )
        authorRepository.save(authorOne)
        authorRepository.save(authorTwo)
        categoryRepository.save(categoryOne)
        categoryRepository.save(categoryTwo)
        postRepository.save(postOne)
        userRepository.save(userOne)
        commentRepository.save(commentOne)
        likeCounterRepository.save(
            LikeCounter(
            contentType = Post::class.java.simpleName,
            contentId = 1,
            likesNumber = 10
        )
        )
        likeCounterRepository.save(
            LikeCounter(
            contentType = Comment::class.java.simpleName,
            contentId = 1,
            likesNumber = 2
        )
        )
        postOne.tags.add(tagOne)
        postOne.tags.add(tagTwo)
        postRepository.save(postOne)
    }
}
