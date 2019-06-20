package sharedcode.model

import kotlinx.serialization.Serializable
import sharedcode.utility.Keep

@Serializable
@Keep
internal data class LeagueResponse(val leagues: List<League>? = emptyList())

internal fun List<League>?.soccerOnly(): List<League> {
    return this
        ?.asSequence()
        ?.filter { it.strSport.equals("Soccer", true) }
        ?.toList()
        ?: emptyList()
}