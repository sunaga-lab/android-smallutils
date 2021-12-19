package com.sunagalab.smallutils

import androidx.lifecycle.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.withTimeoutOrNull
import java.lang.AssertionError


object LiveDataTools {

    fun <T1, T2, TResult> compose(p1: LiveData<T1>, p2: LiveData<T2>, composer: (T1, T2)->TResult): LiveData<TResult> {
        val result = MediatorLiveData<TResult>()
        var p1Val: T1? = null
        var p2Val: T2? = null
        fun update() {
            if(p1Val != null && p2Val != null) {
                result.value = composer(p1Val!!, p2Val!!)
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
                result.value = composer(p1Val!!, p2Val!!, p3Val!!)
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
                result.value = composer(p1Val!!, p2Val!!, p3Val!!, p4Val!!)
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

    class Unsettable<T>() {

        val isSet: Boolean
            get() = !valueWrapped.isEmpty()
        private var valueWrapped = listOf<T>()

        fun unsetValue() {
            valueWrapped = listOf()
        }

        var value: T
            get() {
                assert(!valueWrapped.isEmpty())
                return valueWrapped[0]
            }
            set(newValue) {
                valueWrapped = listOf(newValue)
            }

    }

    fun <T1, T2, TResult> composeNullable(p1: LiveData<T1>, p2: LiveData<T2>, composer: (T1, T2)->TResult): LiveData<TResult> {
        val result = MediatorLiveData<TResult>()
        val p1Val = Unsettable<T1>()
        val p2Val = Unsettable<T2>()
        fun<T> addSrc(liveData: LiveData<T>, stockValue: Unsettable<T>) {
            result.addSource(liveData) {
                stockValue.value = it
                if(p1Val.isSet && p2Val.isSet) {
                    result.value = composer(p1Val.value, p2Val.value)
                }
            }
        }
        addSrc(p1, p1Val)
        addSrc(p2, p2Val)
        return result
    }

    fun <T1, T2, T3, TResult> composeNullable(p1: LiveData<T1>, p2: LiveData<T2>, p3: LiveData<T3>, composer: (T1, T2, T3)->TResult): LiveData<TResult> {
        val result = MediatorLiveData<TResult>()
        val p1Val = Unsettable<T1>()
        val p2Val = Unsettable<T2>()
        val p3Val = Unsettable<T3>()
        fun<T> addSrc(liveData: LiveData<T>, stockValue: Unsettable<T>) {
            result.addSource(liveData) {
                stockValue.value = it
                if(p1Val.isSet && p2Val.isSet && p3Val.isSet) {
                    result.value = composer(p1Val.value, p2Val.value, p3Val.value)
                }
            }
        }
        addSrc(p1, p1Val)
        addSrc(p2, p2Val)
        addSrc(p3, p3Val)
        return result
    }

    fun <T1, T2, T3, T4, TResult> composeNullable(p1: LiveData<T1>, p2: LiveData<T2>, p3: LiveData<T3>, p4: LiveData<T4>, composer: (T1, T2, T3, T4)->TResult): LiveData<TResult> {
        val result = MediatorLiveData<TResult>()
        val p1Val = Unsettable<T1>()
        val p2Val = Unsettable<T2>()
        val p3Val = Unsettable<T3>()
        val p4Val = Unsettable<T4>()
        fun<T> addSrc(liveData: LiveData<T>, stockValue: Unsettable<T>) {
            result.addSource(liveData) {
                stockValue.value = it
                if(p1Val.isSet && p2Val.isSet && p3Val.isSet && p4Val.isSet) {
                    result.value = composer(p1Val.value, p2Val.value, p3Val.value, p4Val.value)
                }
            }
        }
        addSrc(p1, p1Val)
        addSrc(p2, p2Val)
        addSrc(p3, p3Val)
        addSrc(p4, p4Val)
        return result
    }

    fun <TItem, TResult> composeList(items: List<LiveData<TItem>>, composer: (Iterable<TItem>) -> TResult): LiveData<TResult> {
        val result = MediatorLiveData<TResult>()

        val unsettableList = (0..items.count()).map {
            Unsettable<TItem>()
        }.toList()

        items.forEachIndexed { index, liveData ->
            result.addSource(liveData) {
                unsettableList[index].value = it

                if (unsettableList.all { it.isSet }) {
                    result.value = composer(unsettableList.map { it.value })
                }
            }
        }
        return result
    }

    fun or(a: LiveData<Boolean>, b: LiveData<Boolean>) = composeNullable(a, b) { va, vb -> va || vb }
    fun or(a: LiveData<Boolean>, b: LiveData<Boolean>, c: LiveData<Boolean>) = composeNullable(a, b, c) { va, vb, vc -> va || vb || vc}
    fun or(a: LiveData<Boolean>, b: LiveData<Boolean>, c: LiveData<Boolean>, d: LiveData<Boolean>) = composeNullable(a, b, c, d) { va, vb, vc, vd -> va || vb || vc || vd}
    fun and(a: LiveData<Boolean>, b: LiveData<Boolean>) = composeNullable(a, b) { va, vb -> va && vb }
    fun and(a: LiveData<Boolean>, b: LiveData<Boolean>, c: LiveData<Boolean>) = composeNullable(a, b, c) { va, vb, vc -> va && vb && vc}
    fun and(a: LiveData<Boolean>, b: LiveData<Boolean>, c: LiveData<Boolean>, d: LiveData<Boolean>) = composeNullable(a, b, c, d) { va, vb, vc, vd -> va && vb && vc && vd}

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


