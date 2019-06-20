package sharedcode.viewmodel

import io.ktor.client.engine.HttpClientEngine
import kotlinx.coroutines.*
import kotlinx.serialization.UnstableDefault
import sharedcode.service.FootballServiceImpl
import sharedcode.utility.ApplicationDispatcher
import sharedcode.utility.KLiveData
import sharedcode.utility.KMutableLiveData
import kotlin.coroutines.CoroutineContext

@UnstableDefault
open class BaseViewModel(engine: HttpClientEngine?) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = ApplicationDispatcher + SupervisorJob()

    protected val footballService by lazy {
        FootballServiceImpl(engine)
    }

    private val _loadingState = KMutableLiveData<Boolean>().apply { value = false }
    val loadingState: KLiveData<Boolean> = _loadingState

    private val _errorSnackBarEvent = KMutableLiveData<String>()
    val errorSnackBarEvent: KLiveData<String> = _errorSnackBarEvent

    open fun onStopViewModel() {
        coroutineContext.cancel()
    }

    protected fun setErrorSnackBarEvent(message: String?) {
        _errorSnackBarEvent.value = message
    }

    protected fun setLoadingState(isLoading: Boolean) {
        _loadingState.value = isLoading
    }
}