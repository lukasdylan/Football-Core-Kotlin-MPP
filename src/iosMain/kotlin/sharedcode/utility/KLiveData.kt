package sharedcode.utility

actual open class KLiveData<T> {

    private val foreverObservers = mutableListOf<(T) -> Unit>()
    private val foreverLiveDataObservers = mutableMapOf<KLiveData<*>, MutableList<(T) -> Unit>>()
    private val lifecycleObservers = mutableMapOf<KLifecycle, LifecycleAndObserver<T>>()

    internal var _value: T? = null
        set(value) {
            field = value
            notifyObservers()
        }

    actual open val value: T?
        get() = _value

    fun removeObserver() {
        foreverObservers.clear()
    }

    actual fun observeForever(block: (T) -> Unit) {
        foreverObservers.add(block)

        value?.let {
            block(it)
        }
    }

    internal fun addLiveDataObserver(liveDataObserver: KLiveData<*>, block: (T) -> Unit) {
        val listObservers = foreverLiveDataObservers[liveDataObserver]
        if (listObservers == null) {
            foreverLiveDataObservers[liveDataObserver] = mutableListOf(block)
        } else {
            listObservers.add(block)
        }

        foreverObservers.add(block)

        value?.let {
            block(it)
        }
    }

    internal fun removeLiveDataObserver(liveDataObserver: KLiveData<*>) {
        val listObservers = foreverLiveDataObservers[liveDataObserver]
        if (listObservers != null) {
            listObservers.forEach {
                foreverObservers.remove(it)
            }
            listObservers.clear()
            foreverLiveDataObservers.remove(liveDataObserver)
        }
    }

    actual fun hasObservers(): Boolean = foreverObservers.isNotEmpty() && lifecycleObservers.isNotEmpty()

    actual fun observe(lifecycle: KLifecycle, block: (T) -> Unit) {
        this.addObserver(lifecycle, block)
    }

    private fun notifyObservers() {
        value?.let { value ->
            foreverObservers.forEach {
                it(value)
            }
            lifecycleObservers.values.forEach {
                it.observers.forEach { it2 ->
                    it2(value)
                }
            }
        }
    }

    private fun addObserver(lifecycle: KLifecycle, block: (T) -> Unit) {
        var lifecycleAndObserver = this.lifecycleObservers[lifecycle]
        if (lifecycleAndObserver == null) {
            lifecycleAndObserver = LifecycleAndObserver(lifecycle)
            this.lifecycleObservers[lifecycle] = lifecycleAndObserver
        }
        lifecycleAndObserver.observers.add(block)

        value?.let {
            block(it)
        }
    }
}