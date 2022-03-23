package com.notarmaso.beeritupcompose

import androidx.lifecycle.ViewModel
import com.notarmaso.beeritupcompose.interfaces.BeerObserver
import com.notarmaso.beeritupcompose.interfaces.UserObserver
import com.notarmaso.beeritupcompose.interfaces.ViewModelFunction

class UserObserverNotifier: UserObserver<ViewModel> {

    private val subscribers: ArrayList<ViewModelFunction> = arrayListOf()

    override fun register(subscriber: ViewModelFunction) {
        subscribers.add(subscriber)
    }

    override fun remove(subscriber: ViewModelFunction) {
        subscribers.remove(subscriber)
    }

    override fun notifySubscribers() {
        subscribers.forEach {
            it.update()
        }
    }
}

class BeerObserverNotifier: BeerObserver<ViewModel> {
    private val subscribers: ArrayList<ViewModelFunction> = arrayListOf()

    override fun register(subscriber: ViewModelFunction) {
        subscribers.add(subscriber)
    }

    override fun remove(subscriber: ViewModelFunction) {
        subscribers.remove(subscriber)
    }

    override fun notifySubscribers() {
        subscribers.forEach {
            it.update()
        }
    }

}