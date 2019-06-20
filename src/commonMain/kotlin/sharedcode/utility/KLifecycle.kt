package sharedcode.utility

class KLifecycle {

    private val lifecycleStopObservers = mutableListOf<() -> Unit>()
    val tags = mutableMapOf<String, Any>()

    private var isStarted: Boolean = false

    fun start() {
        isStarted = true
    }

    fun stop() {
        isStarted = false
        notifyObserversStop()
    }

    fun addStopObserver(block: () -> Unit) {
        lifecycleStopObservers.add(block)
    }

    private fun notifyObserversStop() {
        lifecycleStopObservers.forEach {
            it()
        }
        lifecycleStopObservers.clear()
    }
}

class LifecycleAndObserver<T>(lifecycle: KLifecycle) {

    val observers = mutableListOf<(T) -> Unit>()

    init {
        lifecycle.addStopObserver {
            observers.clear()
        }
    }
}