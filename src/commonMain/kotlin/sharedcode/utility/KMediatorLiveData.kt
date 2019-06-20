package sharedcode.utility

expect class KMediatorLiveData<T>() : KMutableLiveData<T> {
    fun <S> addSource(other: KLiveData<S>, block: ((S) -> Unit))
    fun removeSource(other: KLiveData<*>)
}