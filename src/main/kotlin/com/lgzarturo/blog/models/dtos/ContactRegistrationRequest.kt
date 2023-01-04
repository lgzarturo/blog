package com.lgzarturo.blog.models.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import com.lgzarturo.blog.models.entities.Contact

class ContactRegistrationRequest(
    @JsonProperty("contact")
    val contact: Contact
)
