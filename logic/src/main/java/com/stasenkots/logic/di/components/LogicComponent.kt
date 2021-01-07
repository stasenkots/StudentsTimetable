package com.stasenkots.logic.di.components

import com.stasenkots.logic.domain.all_data.db.CleanDatabaseUseCase
import com.stasenkots.logic.domain.all_data.db.LoadAllDataFromDatabaseUseCase
import com.stasenkots.logic.domain.all_data.db.SaveDataToDatabaseUseCase
import com.stasenkots.logic.di.modules.LogicModule
import com.stasenkots.logic.domain.all_data.server.LoadAllDataUseCase
import com.stasenkots.logic.domain.group.HasAnyGroupWithUseCase
import com.stasenkots.logic.domain.group.SaveGroupUseCase
import com.stasenkots.logic.domain.lesson.DeleteLessonUseCase
import com.stasenkots.logic.domain.lesson.LoadLessonFromDbUseCase
import com.stasenkots.logic.domain.lesson.LoadLessonsUseCase
import com.stasenkots.logic.domain.lesson.SaveLessonsToDbUseCase
import com.stasenkots.logic.domain.lesson_item.GetLessonItemUseCase
import com.stasenkots.logic.domain.lesson_item.GetLessonItemsUseCase
import com.stasenkots.logic.domain.lesson_item.SendLessonItemUseCase
import com.stasenkots.logic.domain.lesson_item.SetLiveQueryUseCase
import com.stasenkots.logic.domain.states.*
import com.stasenkots.logic.domain.student.LoadStudentsFromDbUseCase
import com.stasenkots.logic.domain.student.LoadStudentsUseCase
import com.stasenkots.logic.domain.student.SaveStudentsToDbUseCase
import com.stasenkots.logic.domain.subject.LoadSubjectsFromDbUseCase
import com.stasenkots.logic.domain.subject.LoadSubjectsUseCase
import com.stasenkots.logic.domain.subject.SaveSubjectsToDbUseCase
import com.stasenkots.logic.domain.user.CheckUserRegistrationUseCase
import com.stasenkots.logic.domain.user.LoginUserUseCase
import com.stasenkots.logic.domain.user.SaveUserUseCase
import dagger.Component

@Component(modules = [LogicModule::class])
interface LogicComponent {
    fun initializeLoadLessonsUseCase(
        loadLessonsUseCase: LoadLessonsUseCase
    )

    fun initializeLoadLessonsFromDbUseCase(
        loadLessonFromDbUseCase: LoadLessonFromDbUseCase
    )
    fun initializeSaveLessonsFromDbUseCase(
        saveLessonsToDbUseCase: SaveLessonsToDbUseCase
    )

    fun initializeLoadSubjectsUseCase(
        loadSubjectsUseCase: LoadSubjectsUseCase
    )

    fun initializeLoadSubjectsFromDbUseCase(
        loadSubjectsFromDbUseCase: LoadSubjectsFromDbUseCase
    )
    fun initializeSaveSubjectsToDbUseCase(
        saveSubjectsToDbUseCase: SaveSubjectsToDbUseCase
    )
    fun initializeLoadStatesUseCase(
        loadStatesUseCase: LoadStatesUseCase
    )

    fun initializeLoadStatesFromDbUseCase(
        loadStatesFromDbUseCase: LoadStatesFromDbUseCase
    )
    fun initializeSaveStatesToDbUseCase(
        saveStatesToDbUseCase: SaveStatesToDbUseCase
    )
    fun initializeDeleteStateUseCase(
        deleteStateUseCase: DeleteStateUseCase
    )

    fun initializeSendStatesUseCase(
        sendStateUseCase: SendStateUseCase
    )

    fun initializeGetStudentsUseCase(
        loadStudentsUseCase: LoadStudentsUseCase
    )

    fun initializeLoadStudentsFromDbUseCase(
        loadStudentsFromDbUseCase: LoadStudentsFromDbUseCase
    )
    fun initializeSaveDataToDatabaseUseCase(
        saveDataToDatabaseUseCase: SaveDataToDatabaseUseCase
    )
    fun initializeCleanDatabaseUseCase(
        cleanDatabaseUseCase: CleanDatabaseUseCase
    )

    fun initializeLoadAllDataFromDatabaseUseCase(
        loadAllDataFromDatabaseUseCase: LoadAllDataFromDatabaseUseCase
    )
    fun initializeLoadAllDataUseCase(
        loadAllDataUseCase: LoadAllDataUseCase
    )
    fun initializeSaveStudentsToDbUseCase(
        saveStudentsToDbUseCase: SaveStudentsToDbUseCase
    )

    fun initializeLoginUserUseCase(
        loginUserUseCase: LoginUserUseCase
    )

    fun initializeHasAnyGroupWithIdUseCase(
        hasAnyGroupWithUseCase: HasAnyGroupWithUseCase
    )

    fun initializeSaveUserUseCase(
        saveUserUseCase: SaveUserUseCase
    )
    fun initializeCheckUserRegistrationUseCase(
        checkUserRegistrationUseCase: CheckUserRegistrationUseCase
    )
    fun initializeSaveGroupUseCase(
        saveGroupUseCase: SaveGroupUseCase
    )

    fun initializeGetLessonItemsUseCase(
        getLessonItemsUseCase: GetLessonItemsUseCase
    )

    fun initializeGetLessonItemUseCase(
        getLessonItemUseCase: GetLessonItemUseCase
    )

    fun initializeSendLessonItemUseCase(
        sendLessonItemUseCase: SendLessonItemUseCase
    )

    fun initializeDeleteLessonUseCase(
        deleteLessonUseCase: DeleteLessonUseCase
    )

    fun initializeSetLiveQueryUseCase(
        setLiveQueryUseCase: SetLiveQueryUseCase
    )

}