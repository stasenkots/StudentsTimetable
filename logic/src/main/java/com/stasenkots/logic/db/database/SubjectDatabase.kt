package com.stasenkots.logic.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stasenkots.logic.db.dao.SubjectDao
import com.stasenkots.logic.db.entity.SubjectDb

private const val DATABASE_NAME="Subject Database"
@Database(entities = [SubjectDb::class],version = 1)
abstract class SubjectDatabase:RoomDatabase() {
    abstract fun getDao():SubjectDao
}
object SubjectDatabaseProvider{
    fun provide(context: Context)=
        Room.databaseBuilder(context,SubjectDatabase::class.java, DATABASE_NAME).build()
}