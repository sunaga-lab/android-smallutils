package com.sunagalab.smallutils

import androidx.lifecycle.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.withTimeoutOrNull


object LiveDataTools {

    fun <T1, T2, TResult> compose(p1: LiveData<T1>, p2: LiveData<T2>, composer: (T1, T2)->TResult): LiveData<TResult> {
        val result = MediatorLiveData<TResult>()
        var p1Val: T1? = null
        var p2Val: T2? = null
        fun update() {
            if(p1Val != null && p2Val != null) {
                result.postValue(composer(p1Val!!, p2Val!!))
            }
        }
        result.addSource(p1) {
            p1Val = it
            update()
        }
        result.addSource(p2) {
            p2Val = it
            update()
        }
        return result
    }

    fun <T1, T2, T3, TResult> compose(p1: LiveData<T1>, p2: LiveData<T2>, p3: LiveData<T3>, composer: (T1, T2, T3)->TResult): LiveData<TResult> {
        val result = MediatorLiveData<TResult>()
        var p1Val: T1? = null
        var p2Val: T2? = null
        var p3Val: T3? = null
        fun update() {
            if(p1Val != null && p2Val != null && p3Val != null) {
                result.postValue(composer(p1Val!!, p2Val!!, p3Val!!))
            }
        }
        result.addSource(p1) {
            p1Val = it
            update()
        }
        result.addSource(p2) {
            p2Val = it
            update()
        }
        result.addSource(p3) {
            p3Val = it
            update()
        }
        return result
    }

    fun <T1, T2, T3, T4, TResult> compose(p1: LiveData<T1>, p2: LiveData<T2>, p3: LiveData<T3>, p4: LiveData<T4>, composer: (T1, T2, T3, T4)->TResult): LiveData<TResult> {
        val result = MediatorLiveData<TResult>()
        var p1Val: T1? = null
        var p2Val: T2? = null
        var p3Val: T3? = null
        var p4Val: T4? = null
        fun update() {
            if(p1Val != null && p2Val != null && p3Val != null && p4Val != null) {
                result.postValue(composer(p1Val!!, p2Val!!, p3Val!!, p4Val!!))
            }
        }
        result.addSource(p1) {
            p1Val = it
            update()
        }
        result.addSource(p2) {
            p2Val = it
            update()
        }
        result.addSource(p3) {
            p3Val = it
            update()
        }
        result.addSource(p4) {
            p4Val = it
            update()
        }
        return result
    }

}


class ObserverChannel<T> : Observer<T> {
    private val channel = Channel<T>(Channel.UNLIMITED)
    override fun onChanged(t: T) {
        channel.offer(t)
    }

    suspend fun await(): T = channel.receive()

    suspend fun awaitWithTimeout(timeout: Long): T? {
        return withTimeoutOrNull(timeout) {
            await()
        }
    }
}

fun<T> createObserverChannelFor(liveData: LiveData<T>): ObserverChannel<T> {
    val observer = ObserverChannel<T>()
    liveData.observeForever(observer)
    return observer
}


fun<T> MutableLiveData<T>.assignValueIfDiffer(newValue: T) {
    if (this.value != newValue) {
        this.value = newValue
    }
}


