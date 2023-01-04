package com.lgzarturo.blog.services

import com.lgzarturo.blog.models.dtos.ContactRegistrationRequest

interface ContactService {
    fun registerContact(request: ContactRegistrationRequest)
}
