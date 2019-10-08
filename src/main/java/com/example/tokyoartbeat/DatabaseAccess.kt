package com.example.tokyoartbeat

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import android.util.EventLog
import java.sql.Blob
import java.sql.Date
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class DatabaseAccess(context: Context) {
    private val mOpenHelper: SQLiteOpenHelper
    private var mDatabase: SQLiteDatabase? = null
    var mCursor: Cursor? = null

    init {
        this.mOpenHelper = DatabaseOpenHelper(context)
    }

    private fun open() {
        this.mDatabase = mOpenHelper.writableDatabase
    }

    private fun close() {
        if (mDatabase != null) {
            this.mDatabase!!.close()
        }
    }

    companion object {
        private var intance: DatabaseAccess? = null

        fun getInstance(context: Context): DatabaseAccess {
            if (intance == null) {
                intance = DatabaseAccess(context)
            }
            return intance as DatabaseAccess
        }
    }

    fun getAllNews(): ArrayList<News> {
        var newsList: ArrayList<News> = ArrayList()
        open()
        try {
            mCursor = mDatabase?.rawQuery("select * from " + DBContract.NewsEntity.NEWS_TABLE + " order by " + DBContract.NewsEntity.COLUMN_PUBLISH_DATE + " desc", null)
        } catch (e: SQLiteException) {
            e.printStackTrace()
            return ArrayList()
        }

        var title: String
        lateinit var publicDate: LocalDate
        var author: String
        var feed: String
        var thumb: ByteArray
        var description: String
        if (mCursor!!.moveToFirst()) {
            while (!mCursor!!.isAfterLast) {
                title =
                    mCursor!!.getString(mCursor!!.getColumnIndex(DBContract.NewsEntity.COLUMN_TITLE))
                feed =
                    mCursor!!.getString(mCursor!!.getColumnIndex(DBContract.NewsEntity.COLUMN_FEED))
                author =
                    mCursor!!.getString(mCursor!!.getColumnIndex(DBContract.NewsEntity.COLUMN_AUTHOR))
                description =
                    mCursor!!.getString(mCursor!!.getColumnIndex(DBContract.NewsEntity.COLUMN_DESCRIPTION))
                val stringDate =
                    mCursor!!.getString(mCursor!!.getColumnIndex(DBContract.NewsEntity.COLUMN_PUBLISH_DATE))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    publicDate = LocalDate.parse(stringDate, DateTimeFormatter.ISO_DATE)
                }
                thumb =
                    mCursor!!.getBlob(mCursor!!.getColumnIndex(DBContract.NewsEntity.COLUMN_THUMB))
                newsList.add(News(title, publicDate, author, feed, thumb, description))
                mCursor!!.moveToNext()
            }
        }
        close()
        return newsList
    }

    fun insertUser(user: UserModel): Boolean {
        open()
        val value = ContentValues()
        value.put(DBContract.UserEntity.COLUMN_NAME, user.name)
        value.put(DBContract.UserEntity.COLUMN_EMAIL, user.email)
        value.put(DBContract.UserEntity.COLUMN_PASSWORD, user.password)

        mDatabase!!.insert(DBContract.UserEntity.USER_TABLE, null, value)
        close()
        return true
    }

    fun getAllUsers(): ArrayList<UserModel> {
        var usersList: ArrayList<UserModel> = ArrayList()
        open()
        try {
            mCursor = mDatabase?.rawQuery("select * from " + DBContract.UserEntity.USER_TABLE, null)
        } catch (e: SQLiteException) {
            e.printStackTrace()
            return ArrayList()
        }

        var name: String
        var email: String
        var password: String
        if (mCursor!!.moveToFirst()) {
            while (!mCursor!!.isAfterLast) {
                name =
                    mCursor!!.getString(mCursor!!.getColumnIndex(DBContract.UserEntity.COLUMN_NAME))
                email =
                    mCursor!!.getString(mCursor!!.getColumnIndex(DBContract.UserEntity.COLUMN_EMAIL))
                password =
                    mCursor!!.getString(mCursor!!.getColumnIndex(DBContract.UserEntity.COLUMN_PASSWORD))
                usersList.add(UserModel(name, email, password))
                mCursor!!.moveToNext()
            }
        }
        close()
        return usersList
    }

    fun getEventDetails(): ArrayList<EventDetails> {
        var eventDetails: ArrayList<EventDetails> = ArrayList()
        open()
        try {
            mCursor =
                mDatabase?.rawQuery("select * from " + DBContract.EventEntity.EVENT_TABLE, null)
        } catch (e: SQLiteException) {
            e.printStackTrace()
            return ArrayList()
        }

        var nameEvent: String
        var nameVenue: String
        lateinit var endDate: LocalDate
        lateinit var startDate: LocalDate
        var category: String
        var goingCount: String
        var description: String
        var address: String
        var artist: String
        if (mCursor!!.moveToFirst()) {
            while (!mCursor!!.isAfterLast) {
                nameEvent =
                    mCursor!!.getString(mCursor!!.getColumnIndex(DBContract.EventEntity.COLUMN_NAME))
                nameVenue =
                    mCursor!!.getString(mCursor!!.getColumnIndex(DBContract.EventEntity.COLUMN_VENUE))
                var stringDate =
                    mCursor!!.getString(mCursor!!.getColumnIndex(DBContract.EventEntity.COLUMN_SCHEDULE_START_DATE))

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startDate = LocalDate.parse(stringDate, DateTimeFormatter.ISO_DATE)
                }
                stringDate =
                    mCursor!!.getString(mCursor!!.getColumnIndex(DBContract.EventEntity.COLUMN_SCHEDULE_END_DATE))

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    endDate = LocalDate.parse(stringDate, DateTimeFormatter.ISO_DATE)
                }
                goingCount =
                    mCursor!!.getString(mCursor!!.getColumnIndex(DBContract.EventEntity.COLUMN_GOING_COUNT))
                description =
                    mCursor!!.getString(mCursor!!.getColumnIndex(DBContract.EventEntity.COLUMN_DESCRIPTION))
                artist =
                    mCursor!!.getString(mCursor!!.getColumnIndex(DBContract.EventEntity.COLUMN_ARTIST))
                address =
                    mCursor!!.getString(mCursor!!.getColumnIndex(DBContract.EventEntity.COLUMN_ADDRESS))
                category =
                    mCursor!!.getString(mCursor!!.getColumnIndex(DBContract.EventEntity.COLUMN_CATEGORY))

                eventDetails.add(
                    EventDetails(
                        nameEvent,
                        nameVenue,
                        startDate,
                        endDate,
                        goingCount,
                        description,
                        category,
                        address,
                        artist
                    )
                )
                mCursor!!.moveToNext()
            }
        }
        close()
        return eventDetails
    }
    fun changePassword(email: String, password: String){
        open()
        try {
            mCursor = mDatabase?.rawQuery("update" + DBContract.UserEntity.USER_TABLE + "set " + DBContract.UserEntity.COLUMN_PASSWORD + "=?" + " where" +
                DBContract.UserEntity.COLUMN_EMAIL + "=?", arrayOf(password, email))
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }
        close()
    }
}

