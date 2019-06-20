package sharedcode.utility


actual class KMediatorLiveData<T> actual constructor() : KMutableLiveData<T>() {

    actual fun <S> addSource(other: KLiveData<S>, block: ((S) -> Unit)) {
        liveData.addSource(other.liveData) {
            block(it)
        }
    }

    actual fun removeSource(other: KLiveData<*>) {
        liveData.removeSource(other.liveData)
    }
}