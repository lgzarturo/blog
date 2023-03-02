package com.lgzarturo.blog

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class CustomCodeTest {

    @Test
    fun itShouldDataSortProperly() {
        val rp1 = RatePlan(200.00)
        val rp2 = RatePlan(499.00)
        val rp3 = RatePlan(null)
        val rp4 = RatePlan(60.00)
        val rp5 = RatePlan(600.00)
        val rp6 = RatePlan(100.00)
        val rp7 = RatePlan(30.00)
        val rp8 = RatePlan(null)

        val room0 = Room(mutableListOf(rp1, rp2, rp4, rp6, rp5, rp7))
        val room1 = Room(mutableListOf(rp1, rp2, rp3, rp4))
        val room2 = Room(mutableListOf(rp5, rp6, rp7, rp8))
        val room3 = Room(mutableListOf(rp8, rp2, rp4, rp3, null))

        val hotel = Hotel(mutableListOf(room0, room1, room2, room3))

        val response = Response(mutableListOf(hotel))

        response.hotels.stream().forEach { hotel ->
            hotel.rooms = hotel.rooms.sortedBy { room -> room.ratePlans.filterNotNull().minOf { return@minOf it.totalAmount?:0.0 } }.stream().toList()
            hotel.rooms.stream().forEach { room ->
                room.ratePlans = room.ratePlans.filterNotNull().sortedBy { return@sortedBy it.totalAmount?:0.0 }
            }
        }

        Assertions.assertThat(response.hotels).isNotEmpty
    }
}


class Response(var hotels: List<Hotel> = emptyList())

class Hotel(var rooms: MutableList<Room> = mutableListOf())

class Room(var ratePlans: List<RatePlan?> = emptyList())

class RatePlan(var totalAmount: Double? = null)
