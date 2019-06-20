package sharedcode.utility

actual open class KMutableLiveData<T> actual constructor() : KLiveData<T>() {

    actual override var value: T?
        get() = _value
        set(value) {
            _value = value
        }
}