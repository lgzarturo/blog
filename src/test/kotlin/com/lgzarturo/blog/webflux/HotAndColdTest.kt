package com.lgzarturo.blog.webflux

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import java.time.Duration

class HotAndColdTest {
    private val heroes = Flux.just("Spider-Man", "Wolverine", "Deadpool", "Captain America", "IronMan", "Ant-Man")
        .delayElements(Duration.ofSeconds(1)).log()

    @Test
    fun coldTest() {
        heroes.subscribe { s -> println("This is a marvel super hero: $s") }
        Thread.sleep(2000)
        heroes.subscribe { s -> println("$s is a hero Super Amazing") }
        Thread.sleep(4000)
    }

    @Test
    fun hotTest() {
        val connectionToFlux = heroes.publish()
        connectionToFlux.connect()
        connectionToFlux.subscribe { s -> println("This is a marvel super hero: $s") }
        Thread.sleep(4000)
        connectionToFlux.subscribe { s -> println("$s is a hero Super Amazing") }
        Thread.sleep(4000)
    }
}
