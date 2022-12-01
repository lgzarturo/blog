package com.lgzarturo.blog.webflux

import org.junit.jupiter.api.Test
import reactor.core.publisher.BaseSubscriber
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

class BackPressureTest {
    @Test
    fun backPressureTest() {
        val fluxNumbers: Flux<Int> = Flux.range(1, 10).log()
        StepVerifier.create(fluxNumbers)
            .expectSubscription()
            .thenRequest(1)
            .expectNext(1)
            .thenRequest(1)
            .expectNext(2)
            .thenCancel()
            .verify()
    }

    @Test
    fun backPressureTest_withException() {
        val fluxNumbers: Flux<Int> = Flux.range(1, 10).log()
        with(fluxNumbers) {
            return@with subscribe(
                { element -> println("Element is $element") },
                { e: Throwable? -> println("Exception $e") },
                { println("Done")},
                { subscription -> subscription.request(2) }
            )
        }
    }

    @Test
    fun backPressureTest_withException_andCancel() {
        val fluxNumbers: Flux<Int> = Flux.range(1, 10).log()
        with(fluxNumbers) {
            return@with subscribe(
                { element -> println("Element is $element") },
                { e: Throwable? -> println("Exception $e") },
                { println("Done")},
                { subscription -> subscription.cancel() }
            )
        }
    }

    @Test
    fun backPressureTest_customized() {
        val fluxNumbers: Flux<Int> = Flux.range(1, 10).log()
        fluxNumbers.subscribe(object : BaseSubscriber<Int>() {
            override fun hookOnNext(value: Int) {
                request(1)
                println("Value received is $value")
                if (value == 5) {
                    cancel()
                }
            }
        })
    }
}
