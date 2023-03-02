package com.lgzarturo.blog.models.entities

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(name = "contacts")
class Contact(
    @Id var id: UUID,
    @NotBlank
    @NotNull
    @Column(nullable = false)
    var name: String,
    @NotBlank
    @NotNull
    @Column(nullable = false)
    var phoneNumber: String,
    var website: String
)
