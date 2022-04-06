package com.notarmaso.beeritupcompose

import androidx.lifecycle.ViewModel
import com.notarmaso.beeritupcompose.interfaces.*
import com.notarmaso.beeritupcompose.interfaces.UserObserver



class Observer: UserObserver<ViewModel> {

    private val subscribers: ArrayList<ViewModelFunction> = arrayListOf()

    override fun register(subscriber: ViewModelFunction) {
        subscribers.add(subscriber)
    }

    override fun remove(subscriber: ViewModelFunction) {
        subscribers.remove(subscriber)
    }

    override fun notifySubscribers(page: String) {
        subscribers.forEach {
            it.update(page)
        }
    }
}
