package com.lgzarturo.blog.webflux

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers.parallel
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

    @Test
    fun transformationUsingFlatMap() {
        val letters = Flux.fromIterable(listOf("A", "E", "I", "O", "U", "~"))
            .flatMap { s -> Flux.fromIterable(convertToList(s)) }.log()
        StepVerifier.create(letters)
            .expectNextCount(12)
            .verifyComplete()
    }

    @Throws(InterruptedException::class)
    private fun convertToList(s: String): List<String> {
        Thread.sleep(100)
        return listOf(s, "new value")
    }

    @Test
    fun transformationUsingFlatMap_usingParallel() {
        val letters = Flux.fromIterable(listOf("A", "E", "I", "O", "U", "~"))
            .window(2)
            .flatMap { s -> s.map(this::convertToList).subscribeOn(parallel()) }
            .flatMap { s -> Flux.fromIterable(s) }.log()
        StepVerifier.create(letters)
            .expectNextCount(12)
            .verifyComplete()
    }

    @Test
    fun transformationUsingFlatMap_usingParallelMaintainOrder() {
        val letters = Flux.fromIterable(listOf("A", "E", "I", "O", "U", "~"))
            .window(2)
            .flatMapSequential { s -> s.map(this::convertToList).subscribeOn(parallel()) }
            .flatMap { s -> Flux.fromIterable(s) }.log()
        StepVerifier.create(letters)
            .expectNextCount(12)
            .verifyComplete()
    }

    @Test
    fun transformationUsingFlatMap_usingParallelMaintainOrderSlow() {
        val letters = Flux.fromIterable(listOf("A", "E", "I", "O", "U", "~"))
            .window(2)
            .concatMap { s -> s.map(this::convertToList).subscribeOn(parallel()) }
            .flatMap { s -> Flux.fromIterable(s) }.log()
        StepVerifier.create(letters)
            .expectNextCount(12)
            .verifyComplete()
    }
}
