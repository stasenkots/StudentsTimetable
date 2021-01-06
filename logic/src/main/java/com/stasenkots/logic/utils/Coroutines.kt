package com.stasenkots.logic.utils

import kotlinx.coroutines.*

fun launchIO( task:suspend ()->Unit):Job{
    return CoroutineScope(Dispatchers.IO).async {
        task()
    }
}
fun <T> launchAsync(task:suspend ()->T):Deferred<T> {
    return CoroutineScope(Dispatchers.IO).async {
        task()
    }
}
fun launchUI(task:suspend ()->Unit):Job{
    return CoroutineScope(Dispatchers.Main).launch {
        task()
    }
}