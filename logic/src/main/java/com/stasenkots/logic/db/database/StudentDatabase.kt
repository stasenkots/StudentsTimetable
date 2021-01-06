package com.stasenkots.logic.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stasenkots.logic.db.dao.LessonDao
import com.stasenkots.logic.db.dao.StudentDao
import com.stasenkots.logic.db.entity.LessonDb
import com.stasenkots.logic.db.entity.StudentDb
import com.stasenkots.logic.entity.student.Student

private const val DATABASE_NAME="Student Database"
@Database(entities = [StudentDb::class], version = 1)
abstract class StudentDatabase : RoomDatabase() {
    abstract fun getDao(): StudentDao
}

object StudentDatabaseProvider {
    fun provide(context: Context) =
        Room.databaseBuilder(context, StudentDatabase::class.java, DATABASE_NAME ).build()
}