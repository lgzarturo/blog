package com.lgzarturo.blog.config

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.gmail.Gmail
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader
import java.security.GeneralSecurityException
import kotlin.jvm.Throws

@Configuration
class MailConfiguration {

    @Throws(IOException::class)
    private fun getCredentials(HTTP_TRANSPORT: NetHttpTransport): Credential {
        val inputStream = MailConfiguration::class.java.getResourceAsStream(Constants.Mail.CREDENTIALS_FILE_PATH)
            ?: throw FileNotFoundException("Credentials file not found")

        val clientSecrets: GoogleClientSecrets = GoogleClientSecrets
            .load(Constants.Mail.JSON_FACTORY, InputStreamReader(inputStream))

        val flow: GoogleAuthorizationCodeFlow = GoogleAuthorizationCodeFlow
            .Builder(HTTP_TRANSPORT, Constants.Mail.JSON_FACTORY, clientSecrets, Constants.Mail.SCOPES)
            .setDataStoreFactory(FileDataStoreFactory(File(Constants.Mail.TOKEN_DIRECTORY_PATH)))
            .setAccessType(Constants.Mail.ACCESS_TYPE).build()

        val receiver: LocalServerReceiver = LocalServerReceiver.Builder().setPort(9090).build()
        return AuthorizationCodeInstalledApp(flow, receiver).authorize(Constants.Mail.USER_ID_AUTHORIZE)
    }

    @Bean
    fun getService(): Gmail? {
        val httpTransport: NetHttpTransport
        return try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport()
            Gmail.Builder(httpTransport, Constants.Mail.JSON_FACTORY, getCredentials(httpTransport))
                .setApplicationName(Constants.Mail.APPLICATION_NAME).build()
        } catch (e: GeneralSecurityException) {
            e.printStackTrace()
            null
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}
