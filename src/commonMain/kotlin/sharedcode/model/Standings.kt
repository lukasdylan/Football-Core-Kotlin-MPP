package sharedcode.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import sharedcode.utility.Parcelable
import sharedcode.utility.Parcelize

@Serializable
@Parcelize
data class Standings(
    val name: String? = null,
    val teamid: String? = null,
    val played: Int = 0,
    val win: Int = 0,
    val draw: Int = 0,
    val loss: Int = 0,
    val total: Int = 0
) : Parcelable