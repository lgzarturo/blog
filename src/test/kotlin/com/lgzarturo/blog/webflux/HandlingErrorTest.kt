package com.lgzarturo.blog.webflux

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import reactor.util.retry.Retry
import java.time.Duration

class HandlingErrorTest {

    @Test
    fun error() {
        val dataFlux = Flux.just("Superman", "Batman", "Spawn")
            .concatWith(Flux.error(RuntimeException("Spawn no es de DC Comics")))
            .concatWith(Flux.just("Wonder Woman"))
            .onErrorResume { e -> print("Exception is ${e.message}"); return@onErrorResume Flux.just("Flash", "Shazam") }
            .log()
        StepVerifier.create(dataFlux)
            .expectSubscription()
            .expectNext("Superman", "Batman", "Spawn")
            .expectNext("Flash", "Shazam")
            //.expectError(RuntimeException::class.java)
            .verifyComplete()
    }

    @Test
    fun error_onErrorReturn() {
        val dataFlux = Flux.just("Superman", "Batman", "Spawn")
            .concatWith(Flux.error(RuntimeException("Spawn no es de DC Comics")))
            .concatWith(Flux.just("Wonder Woman"))
            .onErrorReturn("Flash")
            .log()
        StepVerifier.create(dataFlux)
            .expectSubscription()
            .expectNext("Superman", "Batman", "Spawn")
            .expectNext("Flash")
            .verifyComplete()
    }

    @Test
    fun error_onErrorMap() {
        val dataFlux = Flux.just("Superman", "Batman", "Spawn")
            .concatWith(Flux.error(RuntimeException("Spawn no es de DC Comics")))
            .concatWith(Flux.just("Wonder Woman"))
            .onErrorMap { e -> CustomException(e) }
            .log()
        StepVerifier.create(dataFlux)
            .expectSubscription()
            .expectNext("Superman", "Batman", "Spawn")
            .expectError(CustomException::class.java)
            .verify()
    }

    @Test
    fun error_onErrorMapRetry() {
        val dataFlux = Flux.just("Superman", "Batman", "Spawn")
            .concatWith(Flux.error(RuntimeException("Spawn no es de DC Comics")))
            .concatWith(Flux.just("Wonder Woman"))
            .onErrorMap { e -> CustomException(e) }
            .retry(2)
            .log()
        StepVerifier.create(dataFlux)
            .expectSubscription()
            .expectNext("Superman", "Batman", "Spawn")
            .expectNext("Superman", "Batman", "Spawn")
            .expectNext("Superman", "Batman", "Spawn")
            .expectError(CustomException::class.java)
            .verify()
    }

    @Test
    fun error_onErrorMapRetryBackoff() {
        val dataFlux = Flux.just("Superman", "Batman", "Spawn")
            .concatWith(Flux.error(RuntimeException("Spawn no es de DC Comics")))
            .concatWith(Flux.just("Wonder Woman"))
            .onErrorMap { e -> CustomException(e) }
            .retryWhen(Retry.backoff(1, Duration.ofSeconds(1)))
            .log()
        StepVerifier.create(dataFlux)
            .expectSubscription()
            .expectNext("Superman", "Batman", "Spawn")
            .expectNext("Superman", "Batman", "Spawn")
            .expectError(IllegalStateException::class.java)
            .verify()
    }
}


open class CustomException(exception: Throwable) : Throwable() {
    final override var message: String?
    init {
        message = exception.message
    }
}
