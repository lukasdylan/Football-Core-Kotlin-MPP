package sharedcode.utility

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

private const val lifeCycleOwnerTag = "LifecycleOwner"

actual open class KLiveData<T> {

    internal val liveData = MediatorLiveData<T>()
    actual open val value: T?
        get() = liveData.value

    actual fun observeForever(block: (T) -> Unit) {
        liveData.observeForever {
            block(it)
        }
    }

    actual fun hasObservers(): Boolean = liveData.hasObservers()

    actual fun observe(lifecycle: KLifecycle, block: (T) -> Unit) {
        val realLifecycleOwner = lifecycle.tags[lifeCycleOwnerTag]
        if (realLifecycleOwner == null || realLifecycleOwner !is LifecycleOwner) {
            throw Exception("Please use LifecycleOwner.Lifecycle() to create your lifecycle, or LiveData.observe(LifecycleOwner, (T) -> Unit) on Android Platform")
        } else {
            lifecycle.tags.remove(lifeCycleOwnerTag)
            observe(realLifecycleOwner, block)
        }
    }

    fun asNativeLiveData(): LiveData<T> = this.liveData

    private fun observe(lifecycle: LifecycleOwner, block: (T) -> Unit) {
        liveData.observe(lifecycle, Observer<T> {
            block(it)
        })
    }
}