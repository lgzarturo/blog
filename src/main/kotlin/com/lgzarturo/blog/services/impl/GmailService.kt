package com.lgzarturo.blog.services.impl

import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.services.gmail.Gmail
import com.lgzarturo.blog.config.Constants
import com.lgzarturo.blog.exceptions.EmailFailedToSendException
import com.lgzarturo.blog.services.MailService
import org.apache.commons.codec.binary.Base64
import org.apache.http.entity.ContentType
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.util.Properties
import javax.mail.Message
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import kotlin.jvm.Throws

@Service
class GmailService(private val gmail: Gmail) : MailService {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @Throws(Exception::class)
    override fun sendEmail(email: String, subject: String, body: String) {
        val properties = Properties()
        val session: Session = Session.getInstance(properties, null)
        val emailObject = MimeMessage(session)
        try {
            emailObject.subject = subject
            emailObject.setFrom(InternetAddress("arthurolg@gmail.com"))
            emailObject.addRecipient(Message.RecipientType.TO, InternetAddress(email))
            emailObject.setContent(body, ContentType.TEXT_HTML.mimeType)
            emailObject.setText(body)
            val buffer = ByteArrayOutputStream()
            emailObject.writeTo(buffer)
            val rawMessage = buffer.toByteArray()
            val encodedEmail = Base64.encodeBase64URLSafeString(rawMessage)
            val message = com.google.api.services.gmail.model.Message()
            message.raw = encodedEmail
            gmail.users().messages().send(Constants.Mail.USER_ID_SEND, message).execute()
        } catch (e: GoogleJsonResponseException) {
            val error = e.details
            log.error("Respuesta: ${error.message}, Código: ${error.code}")
            if (error.code == 403) {
                throw e
            }
        } catch (e: java.lang.Exception) {
            throw EmailFailedToSendException()
        }
    }
}
