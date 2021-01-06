package com.stasenkots.studentstimetable.ui.setup.mode

import androidx.lifecycle.ViewModel
import com.stasenkots.logic.entity.User

class ModeViewModel : ViewModel() {
    fun setUserMode(mode: Int) {
       User.mode=mode
    }
    fun setUserName(name:String){
        User.name=name
    }
}