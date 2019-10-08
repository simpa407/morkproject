package com.example.tokyoartbeat

import android.provider.BaseColumns

object DBContract {
    class NewsEntity: BaseColumns{
        companion object{
            const val NEWS_TABLE = "news"
            const val COLUMN_ID = "id"
            const val COLUMN_FEED = "feed"
            const val COLUMN_TITLE = "title"
            const val COLUMN_AUTHOR = "author"
            const val COLUMN_PUBLISH_DATE = "publish_date"
            const val COLUMN_DETAIL_URL = "detail_url"
            const val COLUMN_DESCRIPTION = "description"
            const val COLUMN_THUMB = "thumb"
        }
    }
    class EventEntity : BaseColumns {
        companion object {
            const val EVENT_TABLE = "events"
            const val COLUMN_VENUE = "venue"
            const val COLUMN_ARTIST = "artist"
            const val COLUMN_NAME = "name"
            const val COLUMN_DESCRIPTION = "description_html"
            const val COLUMN_SCHEDULE_START_DATE = "schedule_start_date"
            const val COLUMN_SCHEDULE_END_DATE = "schedule_end_date"
            const val COLUMN_GOING_COUNT = "going_count"
            const val COLUMN_ADDRESS = "address"
            const val COLUMN_CATEGORY = "category"
        }
    }
    class UserEntity : BaseColumns{
        companion object {
            const val USER_TABLE = "users"
            const val COLUMN_NAME = "name"
            const val COLUMN_EMAIL = "email"
            const val COLUMN_PASSWORD = "password"
        }
    }
}