package com.nearsoft.pikmail

import com.nearsoft.pikmail.model.PicasaResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

internal interface PicasaApi {

    @GET("user/{email}?alt=json")
    fun userInfo(@Path("email") email: String): Single<PicasaResponse>

}