package com.nearsoft.pikmail

import com.nearsoft.pikmail.model.Profile
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Utility for mapping gmail addresses to a basic profile info including profile picture url and nickname.
 *
 * @author Eduardo Pool
 */
object Pikmail {

    private val picasaApi: PicasaApi = Retrofit.Builder()
            .baseUrl("http://picasaweb.google.com/data/entry/api/")
            .client(
                    OkHttpClient.Builder()
                            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                            .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(PicasaApi::class.java)

    /**
     * Gets a [Profile] with the [Profile.profilePictureUrl] that can be loaded as an image url and [Profile.nickname] which is the user's nickname from his Google account.
     *
     * @param [email] Gmail address to obtain its info.
     * @return [Single]<[Profile]>
     */
    fun getProfile(email: String): Single<Profile> {
        return picasaApi.userInfo(email)
                .onErrorResumeNext { Single.error(ProfileNotFountException(email, it)) }
                .map { it.entry }
                .map { Profile(it.thumbnail.url, it.nickname.value) }
    }

    /**
     * Gets a the [Profile.profilePictureUrl] that can be loaded as an image url which is the user's profile picture from his Google account.
     *
     * @param [email] Gmail address to obtain its info.
     * @param [size] Size to be set on [Profile.profilePictureUrl] for resizing the profile picture. It is the default size used in the Google account.
     * @return [Single]<[String]>
     */
    fun getProfilePictureUrl(email: String, size: Int? = 0): Single<String> = getProfile(email).map { it.resizeProfilePictureUrl(size) }

    /**
     * Gets the [Profile.nickname] which is the user's nickname from his Google account.
     *
     * @param [email] Gmail address to obtain its info.
     * @return [Single]<[String]>
     */
    fun getProfileNickname(email: String): Single<String> = getProfile(email).map { it.nickname }

}