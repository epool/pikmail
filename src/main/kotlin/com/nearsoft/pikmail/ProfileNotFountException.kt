package com.nearsoft.pikmail

class ProfileNotFountException(errorMessage: String, cause: Throwable) : Exception("Profile Not Found: $errorMessage", cause)