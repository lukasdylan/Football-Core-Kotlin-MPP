package sharedcode.model

import kotlinx.serialization.Serializable
import sharedcode.utility.Parcelable
import sharedcode.utility.Parcelize

@Serializable
@Parcelize
data class League(
    val idLeague: String? = null,
    val strLeague: String? = null,
    val strSport: String? = null
) : Parcelable {

    companion object {
        fun defaultLeague() = League(idLeague = "", strLeague = "")
    }
}