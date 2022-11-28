package com.lgzarturo.blog.webflux

import com.google.common.base.Supplier
import org.assertj.core.util.Arrays
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class FactoryTest {

    private val names: List<String> = listOf("Luffy", "Zoro", "Nami", "Ussop", "Sanji", "Chopper", "Nico", "Franky", "Brook", "Jimbe")

    @Test
    fun fluxIterable() {
        val namesInFlux: Flux<String> = Flux.fromIterable(names).log()
        StepVerifier.create(namesInFlux)
            .expectNextCount(10)
            .verifyComplete()
    }

    @Test
    fun fluxUsingArray() {
        val names: Array<String> = Arrays.array("Nikanika", "ItoIto")
        val fluxNames = Flux.fromArray(names).log()
        StepVerifier.create(fluxNames)
            .expectNext("Nikanika")
            .expectNext("ItoIto")
            .verifyComplete()
    }

    @Test
    fun fluxUsingStream() {
        val fluxNames = Flux.fromStream(names.stream()).log()
        StepVerifier.create(fluxNames)
            .expectNext("Luffy")
            .expectNextCount(8)
            .expectNext("Jimbe")
            .verifyComplete()
    }

    @Test
    fun monoUsingJustOrEmpty() {
        val name = Mono.justOrEmpty<String>(null).log()
        StepVerifier.create(name)
            .verifyComplete()
    }

    @Test
    fun monoUsingSupplier() {
        val name = "Luffy"
        val supplier: Supplier<String> = Supplier { name }
        val itemMono = Mono.fromSupplier(supplier).log()
        StepVerifier.create(itemMono)
            .expectNext(name)
            .verifyComplete()
    }
}
