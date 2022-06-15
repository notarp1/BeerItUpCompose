package com.notarmaso.beeritup

import androidx.lifecycle.ViewModel
import com.notarmaso.beeritup.interfaces.*
import com.notarmaso.beeritup.interfaces.UserObserver



class Observer: UserObserver<ViewModel> {

    private val subscribers: ArrayList<Observable> = arrayListOf()

    override fun register(subscriber: Observable) {
        subscribers.add(subscriber)
    }

    override fun remove(subscriber: Observable) {
        subscribers.remove(subscriber)
    }

    override fun notifySubscribers(funcToRun: FuncToRun) {
        subscribers.forEach {
            it.update(funcToRun)
        }
    }
}
