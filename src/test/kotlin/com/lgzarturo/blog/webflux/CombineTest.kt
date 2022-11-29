package com.lgzarturo.blog.webflux

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.time.Duration
import java.util.function.BiFunction

class CombineTest {
    @Test
    fun combineUsingMerge() {
        val fluxOne = Flux.just("Alvida", "Belmere", "Luccy")
        val fluxTwo = Flux.just("Boros", "Genos", "Fubuki")
        val mergeCharacters = Flux.merge(fluxOne, fluxTwo).log()
        StepVerifier.create(mergeCharacters)
            .expectSubscription()
            .expectNext("Alvida", "Belmere", "Luccy")
            .expectNext("Boros", "Genos", "Fubuki")
            .verifyComplete()
    }

    @Test
    fun combineUsingMerge_withDelay() {
        val fluxOne = Flux.just("Alvida", "Belmere", "Luccy").delayElements(Duration.ofSeconds(1))
        val fluxTwo = Flux.just("Boros", "Genos", "Fubuki").delayElements(Duration.ofSeconds(1))
        val mergeCharacters = Flux.merge(fluxOne, fluxTwo).log()
        StepVerifier.create(mergeCharacters)
            .expectSubscription()
            .expectNextCount(6)
            .verifyComplete()
    }

    @Test
    fun combineUsingMerge_usingConcat() {
        val fluxOne = Flux.just("Alvida", "Belmere", "Luccy")
        val fluxTwo = Flux.just("Boros", "Genos", "Fubuki")
        val mergeCharacters = Flux.concat(fluxOne, fluxTwo).log()
        StepVerifier.create(mergeCharacters)
            .expectSubscription()
            .expectNext("Alvida", "Belmere", "Luccy")
            .expectNext("Boros", "Genos", "Fubuki")
            .verifyComplete()
    }

    @Test
    fun combineUsingMerge_usingConcatWithDelay() {
        val fluxOne = Flux.just("Alvida", "Belmere", "Luccy").delayElements(Duration.ofSeconds(1))
        val fluxTwo = Flux.just("Boros", "Genos", "Fubuki").delayElements(Duration.ofSeconds(1))
        val mergeCharacters = Flux.concat(fluxOne, fluxTwo).log()
        StepVerifier.create(mergeCharacters)
            .expectSubscription()
            .expectNext("Alvida", "Belmere", "Luccy")
            .expectNext("Boros", "Genos", "Fubuki")
            .verifyComplete()
    }

    @Test
    fun combineUsingMerge_usingZip() {
        val fluxOne = Flux.just("Alvida", "Belmere", "Luccy")
        val fluxTwo = Flux.just("Boros", "Genos", "Fubuki")
        val concatFunction = BiFunction<String, String, String> { t1, t2 -> "$t1 vs $t2"}
        val mergeCharacters = Flux.zip(fluxOne, fluxTwo, concatFunction).log()
        StepVerifier.create(mergeCharacters)
            .expectSubscription()
            .expectNextCount(3)
            .verifyComplete()
    }

}
