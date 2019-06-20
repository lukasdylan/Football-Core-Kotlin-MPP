package sharedcode.utility

expect open class KMutableLiveData<T>() : KLiveData<T> {
    override var value : T?
}