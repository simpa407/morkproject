package com.example.tokyoartbeat

import java.time.LocalDate

data class News(
    var title: String,
    var publicDate: LocalDate,
    var author: String,
    var feed: String,
    //var detail_url : String,
    var thumb_img : ByteArray,
    var description : String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as News

        if (title != other.title) return false
        if (publicDate != other.publicDate) return false
        if (author != other.author) return false
        if (feed != other.feed) return false
        if (!thumb_img.contentEquals(other.thumb_img)) return false
        if (description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + publicDate.hashCode()
        result = 31 * result + author.hashCode()
        result = 31 * result + feed.hashCode()
        result = 31 * result + thumb_img.contentHashCode()
        result = 31 * result + description.hashCode()
        return result
    }
}

