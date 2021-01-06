package com.stasenkots.logic.entity

import androidx.lifecycle.MutableLiveData
import com.stasenkots.logic.utils.MODE_STUDENT

object User {
    var id: String = ""
    var mode: Int = MODE_STUDENT
    var groupId = ""
    var name = ""
    val updated = MutableLiveData<Unit>()
}