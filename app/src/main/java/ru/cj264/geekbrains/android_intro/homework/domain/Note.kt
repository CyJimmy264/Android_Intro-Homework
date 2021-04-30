package ru.cj264.geekbrains.android_intro.homework.domain

import java.time.LocalDateTime
import java.util.*

class Note(val id: String, var title: String, var description: String, val creationDateTime: LocalDateTime) {
    var imageUrl: String? = null
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val note = o as Note
        return id == note.id && creationDateTime == note.creationDateTime &&
                title == note.title &&
                description == note.description &&
                imageUrl == note.imageUrl
    }

    override fun hashCode(): Int {
        return Objects.hash(id, creationDateTime, title, description, imageUrl)
    }
}