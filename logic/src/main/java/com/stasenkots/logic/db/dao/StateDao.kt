package com.stasenkots.logic.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.stasenkots.logic.db.entity.StateDb
import com.stasenkots.logic.db.entity.SubjectDb
@Dao
interface StateDao{
    @Insert
    suspend fun insertStates(statesDb:List<StateDb>)

    @Query("SELECT * FROM state_table")
    suspend fun getStates():List<StateDb>

    @Update
    suspend fun updateState(stateDb: StateDb)

    @Insert
    suspend fun insertState(stateDb: StateDb)

    @Query("DELETE FROM state_table WHERE objectId=:objectId")
    suspend fun deleteState(objectId:String)

    @Query("DELETE FROM state_table")
    suspend fun cleanDb()
}
