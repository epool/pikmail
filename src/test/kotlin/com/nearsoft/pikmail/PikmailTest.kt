package com.nearsoft.pikmail

import com.nearsoft.pikmail.model.Profile
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object PikmailTest : Spek({

    val expectedProfile = Profile(
            "https://lh3.googleusercontent.com/-lNnqhGfaSJM/AAAAAAAAAAI/AAAAAAAAAAA/0cHYLCmUmIk/s64-c/101290539395397957916.jpg",
            "Eduardo Pool"
    )
    val gmailAddressUnderTest = "eduardo.alejandro.pool.ake@gmail.com"

    given("a Pikmail object") {

        on("get a profile for an invalid Gmail address") {

            val invalidGmailAddress = "test@mail.com"
            val testObservable = Pikmail.getProfile(invalidGmailAddress).test()

            it("should throw a ProfileNotFountException") {
                testObservable.assertError(ProfileNotFountException::class.java)
            }

        }

        on("get a profile for a valid Gmail address") {

            val testObservable = Pikmail.getProfile(gmailAddressUnderTest).test()

            it("should not fail") {
                testObservable.assertNoErrors()
            }

            it("should match the expected profile for the given Gmail address") {
                testObservable.assertValue { it == expectedProfile }
            }

        }

        on("get a profile picture url for a valid Gmail address without a specific size") {

            val testObservable = Pikmail.getProfilePictureUrl(gmailAddressUnderTest).test()

            it("should not fail") {
                testObservable.assertNoErrors()
            }

            it("should match with the expected profile picture url with its default size") {
                testObservable.assertValue { it == expectedProfile.profilePictureUrl }
            }

        }

        on("get a profile picture url for a valid Gmail address with a specific size") {

            val size = 500
            val testObservable = Pikmail.getProfilePictureUrl(gmailAddressUnderTest, size).test()

            it("should not fail") {
                testObservable.assertNoErrors()
            }

            it("should match with the expected profile picture url with the specified size") {
                testObservable.assertValue { it == expectedProfile.resizeProfilePictureUrl(size) }
            }

        }

        on("get a profile nickname for a valid Gmail address") {

            val testObservable = Pikmail.getProfileNickname(gmailAddressUnderTest).test()

            it("should not fail") {
                testObservable.assertNoErrors()
            }

            it("should match with the expected profile nickname") {
                testObservable.assertValue { it == expectedProfile.nickname }
            }

        }

    }

})