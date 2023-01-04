package com.lgzarturo.blog.repositories

import com.lgzarturo.blog.models.entities.Contact
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional
import java.util.UUID

interface ContactRepository : JpaRepository<Contact, UUID> {
    @Query("SELECT id, name, phone_number FROM contacts WHERE phone_number = :phone_number", nativeQuery = true)
    fun findContactByPhoneNumber(@Param("phone_number") phoneNumber: String): Optional<Contact>
}
