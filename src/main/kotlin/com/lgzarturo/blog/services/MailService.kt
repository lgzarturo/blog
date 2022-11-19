package com.lgzarturo.blog.services

interface MailService {
    fun sendEmail(email: String, subject: String, body: String)
}
