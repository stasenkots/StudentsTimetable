package com.stasenkots.logic.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stasenkots.logic.db.dao.LessonDao
import com.stasenkots.logic.db.entity.LessonDb

private const val DATABASE_NAME="Lesson Database"
@Database(entities = [LessonDb::class], version = 1)
abstract class LessonDatabase : RoomDatabase() {
    abstract fun getDao(): LessonDao
}

object LessonDatabaseProvider {
    fun provide(context: Context) =
        Room.databaseBuilder(context, LessonDatabase::class.java, DATABASE_NAME ).build()
}