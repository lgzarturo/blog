package com.lgzarturo.blog.webflux

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

class FluxAndMono {
    @Test
    fun fluxTest() {
        val items: Flux<String> = Flux.just("Spring", "Web Flux", "Reactive").log()
        items.subscribe(
            { x: String? -> println(x) }
        ) { e: Throwable? -> System.err.println(e) }
    }

    @Test
    fun fluxTest_onRaiseException() {
        val items: Flux<String> = Flux.just("Spring", "Web Flux", "Reactive")
            .concatWith(Flux.error(RuntimeException("Error en el flux")))
            .log()
        items.subscribe(
            { x: String? -> println(x) }
        ) { e: Throwable? -> System.err.println(e) }
        StepVerifier.create(items)
            .expectNext("Spring")
            .expectNext("Web Flux")
            .expectNext("Reactive")
            .expectError(RuntimeException::class.java)
            .verify()
    }

    @Test
    fun fluxTest_onCompletedWithoutError() {
        val items: Flux<String> = Flux.just("Spring", "Web Flux", "Reactive")
            .log()
        items.subscribe(
            { x: String? -> println(x) },
            { e: Throwable? -> System.err.println(e) },
            { println("Completed") }
        )
        StepVerifier.create(items)
            .expectNext("Spring")
            .expectNext("Web Flux")
            .expectNext("Reactive")
            .verifyComplete()
    }

    @Test
    fun fluxTest_countElements() {
        val errorMessage = "Error en el flux"
        val items: Flux<String> = Flux.just("Spring", "Web Flux", "Reactive")
            .concatWith(Flux.error(RuntimeException(errorMessage)))
            .log()
        items.subscribe(
            { x: String? -> println(x) },
            { e: Throwable? -> System.err.println(e) },
            { println("Completed") }
        )
        StepVerifier.create(items)
            .expectNextCount(3)
            .expectErrorMessage(errorMessage)
            .verify()
    }
}
