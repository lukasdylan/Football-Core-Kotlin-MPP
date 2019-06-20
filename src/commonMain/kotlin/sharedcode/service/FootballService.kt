package sharedcode.service

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.client.request.url
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import sharedcode.extension.NetworkListener
import sharedcode.model.Either
import sharedcode.model.League
import sharedcode.model.LeagueResponse
import sharedcode.model.soccerOnly

internal interface FootballService {
    suspend fun getLeagueList(listener: NetworkListener<Exception, List<League>>)
}

@UnstableDefault
@Suppress("Unused")
class FootballServiceImpl(engine: HttpClientEngine?) : FootballService {

    private val httpClient by lazy {
        return@lazy if (engine != null) {
            HttpClient(engine) {
                installConfig()
            }
        } else {
            HttpClient {
                installConfig()
            }
        }
    }

    override suspend fun getLeagueList(listener: NetworkListener<Exception, List<League>>) {
        try {
            val jsonResult: String = httpClient.get {
                url("https://www.thesportsdb.com/api/v1/json/1/all_leagues.php")
            }
            Json.nonstrict.parse(LeagueResponse.serializer(), jsonResult)
                .leagues.soccerOnly()
                .also { listener.invoke(Either.Success(it)) }
        } catch (ex: Exception) {
            listener.invoke(Either.Failure(ex))
        }
    }

    private fun HttpClientConfig<*>.installConfig() {
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
    }
}