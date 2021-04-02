package com.stasenkots.logic.repository.user


import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.parse.ParseUser
import com.stasenkots.logic.*
import com.stasenkots.logic.entity.Group
import com.stasenkots.logic.entity.User
import com.stasenkots.logic.utils.launchAsync
import com.stasenkots.logic.utils.launchIO
import com.stasenkots.logic.utils.toDate
import javax.inject.Inject


class UserDataSource @Inject constructor() {
    fun isUserRegistered(sharedPrefs: SharedPrefs): Boolean {
        if (ParseUser.getCurrentUser() == null) {
            return false
        }
        setUserData(sharedPrefs)
        return true

    }

    private fun setUserData(sharedPrefs: SharedPrefs?) {
        val user = ParseUser.getCurrentUser()
        User.mode = user.getInt("mode")
        User.groupId = user.getString("group_id").orEmpty()
        Group.mGroup.groupId = user.getString("group_id").orEmpty()
        User.id = user.objectId
        if (User.groupId.isNotEmpty() && sharedPrefs != null) {
            Group.mGroup.semStartDate = sharedPrefs.getStartSemDate().toDate()
        }
        User.name = user.getString("name").orEmpty()
        User.updated.postValue(Unit)
    }

    suspend fun loginUser(data: Intent) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        val account = task.getResult(ApiException::class.java)
        val authCode = account!!.serverAuthCode
        val tokenResponse = launchAsync {
            GoogleAuthorizationCodeTokenRequest(
                NetHttpTransport(),
                JacksonFactory.getDefaultInstance(),
                TOKEN_SERVER_ENCODED_URL,
                CLIENT_ID,
                CLIENT_SECRET,
                authCode,
                REDIRECT_URL
            )
                .execute()
        }.await()
        val accessToken: String = tokenResponse.accessToken
        val authData = mapOf(
            "access_token" to accessToken,
            "id" to account.id
        )
      ParseUser.logInWithInBackground("google", authData).continueWith {
            if (it.error == null) {
                setUserData(null)
            } else {
                throw it.error
            }
        }

    }

    fun saveData() {
        val user = ParseUser.getCurrentUser()
        user.put("mode", User.mode)
        user.put("group_id", User.groupId)
        user.put("name", User.name)
        user.saveInBackground()
    }

}