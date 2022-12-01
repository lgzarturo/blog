package com.lgzarturo.blog.webflux

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.time.Duration

class TimeTest {
    @Test
    fun infiniteSequence() {
        val infinityNumbers: Flux<Long> = Flux.interval(Duration.ofMillis(200)).log()
        infinityNumbers.subscribe { element -> print("Value $element") }
        Thread.sleep(3000)
    }

    @Test
    fun infiniteSequence_withTake() {
        val infinityNumbers: Flux<Long> = Flux.interval(Duration.ofMillis(200))
            .take(3)
            .log()
        StepVerifier.create(infinityNumbers)
            .expectSubscription()
            .expectNext(0L, 1L, 2L)
            .verifyComplete()
    }


    @Test
    fun infiniteSequence_withMap() {
        val infinityNumbers: Flux<Int> = Flux.interval(Duration.ofMillis(200))
            .map { l -> l.toInt() }
            .take(3)
            .log()
        StepVerifier.create(infinityNumbers)
            .expectSubscription()
            .expectNext(0, 1, 2)
            .verifyComplete()
    }

    @Test
    fun infiniteSequence_withMap_andDelay() {
        val infinityNumbers: Flux<Int> = Flux.interval(Duration.ofMillis(200))
            .delayElements(Duration.ofSeconds(1))
            .map { l -> l.toInt() }
            .take(3)
            .log()
        StepVerifier.create(infinityNumbers)
            .expectSubscription()
            .expectNext(0, 1, 2)
            .verifyComplete()
    }
}
