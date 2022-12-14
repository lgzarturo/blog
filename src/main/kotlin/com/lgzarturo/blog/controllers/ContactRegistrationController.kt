package com.lgzarturo.blog.controllers

import com.lgzarturo.blog.models.dtos.ContactRegistrationRequest
import com.lgzarturo.blog.services.impl.JpaContactService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("api/v1/contact-registration")
class ContactRegistrationController(private val contactService: JpaContactService) {

    @PostMapping
    fun registerNewContact(@Valid @RequestBody request: ContactRegistrationRequest) {
        contactService.registerContact(request)
    }
}
