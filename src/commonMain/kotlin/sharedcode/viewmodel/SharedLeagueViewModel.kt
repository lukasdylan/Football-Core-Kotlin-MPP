package sharedcode.viewmodel

import io.ktor.client.engine.HttpClientEngine
import kotlinx.coroutines.launch
import kotlinx.serialization.UnstableDefault
import sharedcode.extension.fold
import sharedcode.model.League
import sharedcode.utility.KLiveData
import sharedcode.utility.KMutableLiveData

@UnstableDefault
open class SharedLeagueViewModel(engine: HttpClientEngine? = null) : BaseViewModel(engine) {

    private val _leagueList = KMutableLiveData<List<League>>()
    val leagueList: KLiveData<List<League>> = _leagueList

    fun loadLeagueList() {
        launch {
            setLoadingState(true)
            footballService.getLeagueList { either ->
                setLoadingState(false)
                either.fold(
                    onFailed = { setErrorSnackBarEvent(it.message) },
                    onSuccess = { _leagueList.value = it }
                )
            }
        }
    }
}