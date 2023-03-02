package com.lgzarturo.blog.repositories

import com.lgzarturo.blog.models.entities.Contact
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.dao.DataIntegrityViolationException
import java.util.UUID

@DataJpaTest
class ContactRepositoryTest {

    @Autowired
    private lateinit var contactRepository: ContactRepository
    private var contactId: UUID = UUID.randomUUID()
    private var contact: Contact? = null

    @BeforeEach
    fun setUp() {
        contact = Contact(contactId, "John Doe", "9981918282", "")
    }

    @Test
    fun itShouldSelectContactByPhoneNumber() {
        contactRepository.save(this.contact!!)
        val savedContact = contactRepository.findContactByPhoneNumber("9981918282")
        Assertions.assertThat(savedContact).isPresent
    }

    @Test
    fun itShouldSaveContact() {
        contactRepository.save(this.contact!!)
        val savedContact = contactRepository.findById(contactId)
        Assertions.assertThat(savedContact).isPresent.hasValueSatisfying {
            /*
            // Comparando campo por campo
            Assertions.assertThat(it.id).isEqualTo(id)
            Assertions.assertThat(it.name).isEqualTo("John Doe")
            Assertions.assertThat(it.phoneNumber).isEqualTo("9981918282")
            Assertions.assertThat(it.website).isNull()
             */
            // Comparando todos los campos de un objeto
            Assertions.assertThat(it).usingRecursiveComparison().isEqualTo(contact)
        }
    }

    @Test
    fun itShouldNotSaveContactWhenPhoneNumberIsBlank() {
        contact = Contact(contactId, "John Doe", "", "")
        contactRepository.save(this.contact!!)
        val data = contactRepository.findById(contactId).get()
        Assertions.assertThat(data).isNotNull
    }
}
