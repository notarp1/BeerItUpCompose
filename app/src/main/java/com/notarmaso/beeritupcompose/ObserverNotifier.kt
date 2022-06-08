package com.notarmaso.beeritupcompose

import androidx.lifecycle.ViewModel
import com.notarmaso.beeritupcompose.interfaces.*
import com.notarmaso.beeritupcompose.interfaces.UserObserver



class Observer: UserObserver<ViewModel> {

    private val subscribers: ArrayList<Observerable> = arrayListOf()

    override fun register(subscriber: Observerable) {
        subscribers.add(subscriber)
    }

    override fun remove(subscriber: Observerable) {
        subscribers.remove(subscriber)
    }

    override fun notifySubscribers(funcToRun: FuncToRun) {
        subscribers.forEach {
            it.update(funcToRun)
        }
    }
}
