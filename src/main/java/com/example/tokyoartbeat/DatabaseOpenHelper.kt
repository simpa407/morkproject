package com.example.tokyoartbeat

import android.content.Context
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

class DatabaseOpenHelper(context: Context) : SQLiteAssetHelper(context, DATABASE_NAME, null, 1) {
    companion object {
        private const val DATABASE_NAME = "tokyo_art_beat.db"
    }
}
