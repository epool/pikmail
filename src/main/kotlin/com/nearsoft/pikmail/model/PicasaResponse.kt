package com.nearsoft.pikmail.model

import com.google.gson.annotations.SerializedName

internal data class PicasaResponse(@SerializedName("entry") val entry: Entry)