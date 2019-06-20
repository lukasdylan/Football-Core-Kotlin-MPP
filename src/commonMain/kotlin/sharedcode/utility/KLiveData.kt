package sharedcode.utility

expect open class KLiveData<T> {

    open val value : T?

    fun hasObservers() : Boolean

    fun observe(lifecycle: KLifecycle, block: (T) -> Unit)

    fun observeForever(block: (T) -> Unit)
}