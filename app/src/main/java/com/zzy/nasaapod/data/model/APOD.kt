package com.zzy.nasaapod.data.model

import android.os.Parcelable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

//{
//    "copyright": "Steve Mandel",
//    "date": "2024-08-31",
//    "explanation": "Galaxies of the NGC 7771 Group are featured in this intriguing skyscape. Some 200 million light-years distant toward the constellation Pegasus, NGC 7771 is the large, edge-on spiral near center, about 75,000 light-years across, with two smaller galaxies below it. Large spiral NGC 7769 is seen face-on to the right. Galaxies of the NGC 7771 group are interacting, making repeated close passages that will ultimately result in galaxy-galaxy mergers on a cosmic timescale. The interactions can be traced by distortions in the shape of the galaxies themselves and faint streams of stars created by their mutual gravitational tides. But a clear view of this galaxy group is difficult to come by as the deep image also reveals extensive clouds of foreground dust sweeping across the field of view. The dim, dusty galactic cirrus clouds are known as Integrated Flux Nebulae. The faint IFN reflect starlight from our own Milky Way Galaxy and lie only a few hundred light-years above the galactic plane.",
//    "hdurl": "https://apod.nasa.gov/apod/image/2408/NGC7769_70_71_Mandel.jpg",
//    "media_type": "image",
//    "service_version": "v1",
//    "title": "IFN and the NGC 7771 Group",
//    "url": "https://apod.nasa.gov/apod/image/2408/NGC7769_70_71_Mandel_1024.jpg"
//  },

@JsonClass(generateAdapter = true)
@Entity(tableName = "apod")
@Parcelize
data class APOD(
    @PrimaryKey val date: String = "", //We are getting only 1 pic per day so this is unique
    val copyright: String = "",
    val explanation: String = "",
    val hdurl: String = "",
    @Json(name = "media_type") val mediaType: String = "",
    val title: String = "",
    val url: String = "",
    var localPath: String? = null
): Parcelable {

    @IgnoredOnParcel
    var mutableLocalPath by mutableStateOf(localPath)

    fun updateLocalPath(path: String?) {
        localPath = path
        mutableLocalPath = path
    }

    fun isImage() = mediaType == "image"
}