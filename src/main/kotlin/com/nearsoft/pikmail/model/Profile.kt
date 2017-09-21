package com.nearsoft.pikmail.model

data class Profile(val profilePictureUrl: String, val nickname: String) {

    fun resizeProfilePictureUrl(size: Int? = 0): String {
        return if (size != null && size > 0) "$profilePictureUrl?size=$size" else profilePictureUrl
    }

}