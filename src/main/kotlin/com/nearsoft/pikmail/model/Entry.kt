package com.nearsoft.pikmail.model

import com.google.gson.annotations.SerializedName

internal data class Entry(
        @SerializedName("gphoto\$nickname") val nickname: Nickname,
        @SerializedName("gphoto\$thumbnail") val thumbnail: Thumbnail
)