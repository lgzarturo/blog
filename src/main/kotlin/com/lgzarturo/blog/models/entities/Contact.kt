package com.lgzarturo.blog.models.entities

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "contacts")
class Contact {
    @Id
    var id: UUID? = null
    @NotBlank
    var name: String? = null
    @NotBlank
    var phoneNumber: String? = null
    val website: String? = null
}
