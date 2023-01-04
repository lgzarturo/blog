package com.lgzarturo.blog.services.impl

import com.lgzarturo.blog.models.dtos.ContactRegistrationRequest
import com.lgzarturo.blog.repositories.ContactRepository
import com.lgzarturo.blog.services.ContactService
import org.springframework.stereotype.Service

@Service
class JpaContactService(private val contactRepository: ContactRepository) : ContactService {
    override fun registerContact(request: ContactRegistrationRequest) {
        val phoneNumber = request.contact.phoneNumber ?: ""
        val contactExists = contactRepository.findContactByPhoneNumber(phoneNumber)
        if (contactExists.isPresent) {
            val contactPersisted = contactExists.get()
            if (contactPersisted.name == request.contact.name) {
                return
            }
            throw IllegalStateException("Phone number $phoneNumber is already registered")
        }
        contactRepository.save(request.contact)
    }
}
