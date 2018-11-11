package com.groep4.mindfulness.model

import java.util.Date

/**
 * Simpele Class voor chat
 */
class Message(content: String, messageUser: String) {

    var content: String? = content
    var messageUser: String? = messageUser
    var messageTime: Long = 0

    init {
        this.messageTime = Date().time
    }

}
