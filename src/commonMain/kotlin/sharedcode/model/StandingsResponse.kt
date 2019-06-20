package sharedcode.model

import kotlinx.serialization.Serializable
import sharedcode.utility.Parcelable
import sharedcode.utility.Parcelize

@Parcelize
@Serializable
internal data class StandingResponse(val table: List<Standings> = mutableListOf()) : Parcelable