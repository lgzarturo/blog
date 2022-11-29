package com.lgzarturo.blog.webflux

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

class TransformTest {
    private val names: List<String> = listOf("Alabasta", "Impeldown", "Triller Bark", "Dressrosa")

    @Test
    fun transformationUsingMap() {
        val sagas: Flux<String> = Flux.fromIterable(names)
            .map { s -> s.uppercase() }.log()
        StepVerifier.create(sagas)
            .expectNext("ALABASTA", "IMPELDOWN", "TRILLER BARK", "DRESSROSA")
            .verifyComplete()
    }


    @Test
    fun transformationUsingMap_toLengthOfString() {
        val sagas: Flux<Int> = Flux.fromIterable(names)
            .map { s -> s.length }.log()
        StepVerifier.create(sagas)
            .expectNext("Alabasta".length, "Impeldown".length, "Triller Bark".length, "Dressrosa".length)
            .verifyComplete()
    }

    @Test
    fun transformationUsingMap_toLengthOfStringAndRepeat() {
        val sagas: Flux<Int> = Flux.fromIterable(names)
            .map { s -> s.length }.repeat(2).log()
        StepVerifier.create(sagas)
            .expectNext("Alabasta".length, "Impeldown".length, "Triller Bark".length, "Dressrosa".length)
            .expectNext("Alabasta".length, "Impeldown".length, "Triller Bark".length, "Dressrosa".length)
            .expectNext("Alabasta".length, "Impeldown".length, "Triller Bark".length, "Dressrosa".length)
            .verifyComplete()
    }

    @Test
    fun transformationUsingMap_filter() {
        val sagas: Flux<Int> = Flux.fromIterable(names)
            .filter { s -> s.length <= 9}
            .map { s -> s.length }.log()
        StepVerifier.create(sagas)
            .expectNext("Alabasta".length, "Impeldown".length, "Dressrosa".length)
            .verifyComplete()
    }
}
