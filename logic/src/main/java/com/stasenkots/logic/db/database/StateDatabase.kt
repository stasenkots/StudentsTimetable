package com.stasenkots.logic.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stasenkots.logic.db.dao.LessonDao
import com.stasenkots.logic.db.dao.StateDao
import com.stasenkots.logic.db.entity.LessonDb
import com.stasenkots.logic.db.entity.StateDb

private const val DATABASE_NAME="State Database"
@Database(entities = [StateDb::class], version = 1)
abstract class StateDatabase : RoomDatabase() {
    abstract fun getDao(): StateDao
}

object StateDatabaseProvider {
    fun provide(context: Context) =
        Room.databaseBuilder(context, StateDatabase::class.java, DATABASE_NAME ).build()
}