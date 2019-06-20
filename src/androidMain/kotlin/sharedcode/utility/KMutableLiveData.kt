package sharedcode.utility


actual open class KMutableLiveData<T> actual constructor() : KLiveData<T>() {
    actual override var value: T?
        get() = liveData.value
        set(value) {
            liveData.postValue(value)
        }
}