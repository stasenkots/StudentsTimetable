package com.stasenkots.logic.repository.states

import androidx.lifecycle.MutableLiveData
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.livequery.ParseLiveQueryClient
import com.parse.livequery.SubscriptionHandling
import com.stasenkots.logic.db.dao.StateDao
import com.stasenkots.logic.entity.*
import com.stasenkots.logic.entity.state.State
import com.stasenkots.logic.entity.state.States
import com.stasenkots.logic.network.mappers.StateMapper
import com.stasenkots.logic.utils.convertToDayOfWeek
import java.lang.Exception
import javax.inject.Inject

class StatesRepository @Inject constructor(
    private val dataSource: StatesDataSource,
    private val mapper: StateMapper,
    private val dbMapper: com.stasenkots.logic.db.mappers.StateMapper,
    private val parseLiveQueryClient: ParseLiveQueryClient,
) {
    suspend fun getStates(): List<State> {
        val response = dataSource.getStates()
        return if (response.isSuccessful) {
            response.body()?.statesResponse?.map { stateResponse ->
                mapper.map(stateResponse)
            } ?: emptyList()
        } else {
            throw Exception(response.message())
        }
    }

    suspend fun deleteState(id: String) {
        dataSource.deleteStateFromDb(id)
    }

    suspend fun getStateFromDb(dao: StateDao): List<State> {
        return dataSource.getStatesFromDb(dao).map {
            dbMapper.map(it)
        }
    }

    suspend fun cleanDb(dao: StateDao) {
        dataSource.cleanDb(dao)
    }

    suspend fun saveStatesToDb(dao: StateDao, list: List<State>) {
        dataSource.saveStatesToDb(dao, list.map { dbMapper.map(it) })
    }

    suspend fun updateStateInDb(state: State, dao: StateDao) {
        dataSource.updateStateInDb(dbMapper.map(state), dao)
    }

    suspend fun deleteStateFromDb(id: String, dao: StateDao) {
        dataSource.deleteStateFromDb(id, dao)
    }

    fun setLiveQuery() {
        val query = ParseQuery.getQuery<ParseObject>("state")
        val subscriptionHandling: SubscriptionHandling<ParseObject> =
            parseLiveQueryClient.subscribe(query)
        subscriptionHandling.handleEvents { _, event, mObject ->
            val state = mapper.map(mObject)
            when (event) {
                SubscriptionHandling.Event.DELETE -> {
                    States.map.remove(state.id)
                    States.deletedObject.postValue(state.id)
                }
                SubscriptionHandling.Event.UPDATE -> {
                    States.map[state.id] = state
                    States.modifiedObject.postValue(state)
                }
                SubscriptionHandling.Event.CREATE -> {
                    States.map[state.id] = state
                    States.createdObject.postValue(state)
                }
            }

        }
        subscriptionHandling.handleError { _, error ->
            throw Exception(error)
        }
    }

    suspend fun insertStateInDb(state: State, dao: StateDao) {
        dataSource.insertStateInDb(dbMapper.map(state), dao)
    }


    fun moveStates(subjectId: String, oldDayOfWeek: DayOfWeek, newDayOfWeek: DayOfWeek) {
        val days = newDayOfWeek - oldDayOfWeek
        for (state in States.map.values) {
            if (state.subject == subjectId && state.date.convertToDayOfWeek() == oldDayOfWeek) {
                state.date.plusDays(days.toLong())
            }
        }
    }

    suspend fun sendState(state: State) {
        val request = mapper.map(state)
        if (state.id.isEmpty()) {
            dataSource.putState(request)
        } else {
            dataSource.updateState(state.id, request)
        }
    }

}