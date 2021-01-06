package com.stasenkots.logic.repository.states

import com.stasenkots.logic.db.dao.StateDao
import com.stasenkots.logic.db.entity.StateDb
import com.stasenkots.logic.db.entity.SubjectDb
import com.stasenkots.logic.network.dto.state.request.StateRequest
import com.stasenkots.logic.network.dto.state.response.StateResponse
import com.stasenkots.logic.network.dto.state.response.StatesResponse
import com.stasenkots.logic.network.networking.StateApi
import retrofit2.Response
import javax.inject.Inject

class StatesDataSource @Inject constructor(
    private val statesApi: StateApi
) {
    suspend fun getStates(): Response<StatesResponse> {
        return statesApi.getStates()
    }
    suspend fun getStatesFromDb(dao: StateDao):List<StateDb>{
        return dao.getStates()
    }
    suspend fun cleanDb(dao: StateDao){
        dao.cleanDb()
    }
    suspend fun insertStateInDb(stateDb: StateDb,dao: StateDao){
        dao.insertState(stateDb)
    }
    suspend fun putState(stateRequest: StateRequest) {
        statesApi.putState(stateRequest)
    }

    suspend fun updateState(objectId:String,stateRequest: StateRequest) {
        statesApi.setState(objectId,stateRequest)
    }
    suspend fun deleteStateFromDb(id:String){
        statesApi.deleteState(id)
    }
    suspend fun saveStatesToDb(dao: StateDao,list: List<StateDb>){
        dao.insertStates(list)
    }
    suspend fun updateStateInDb(stateDb: StateDb,dao: StateDao){
        dao.updateState(stateDb)
    }
    suspend fun deleteStateFromDb(id:String, dao: StateDao){
        dao.deleteState(id)
    }
}