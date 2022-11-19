package com.lgzarturo.blog.config

import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.gmail.GmailScopes
import java.util.*

class Constants {
    object Mail {
        const val APPLICATION_NAME = "blog-app-springboot"
        const val ACCESS_TYPE = "offline"
        const val USER_ID_AUTHORIZE = "user"
        const val USER_ID_SEND = "me"
        const val TOKEN_DIRECTORY_PATH = "tokens"
        const val CREDENTIALS_FILE_PATH = "/credentials.json"
        val JSON_FACTORY: JsonFactory = GsonFactory.getDefaultInstance()
        val SCOPES: List<String> = Collections.singletonList(GmailScopes.GMAIL_SEND)
    }
}
