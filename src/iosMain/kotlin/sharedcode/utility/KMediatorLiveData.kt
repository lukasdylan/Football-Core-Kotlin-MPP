package sharedcode.utility

actual class KMediatorLiveData<T> actual constructor() : KMutableLiveData<T>() {
    actual fun <S> addSource(other: KLiveData<S>, block: ((S) -> Unit)) {
        other.addLiveDataObserver(this, block)
    }

    actual fun removeSource(other: KLiveData<*>) {
        other.removeLiveDataObserver(this)
    }
}