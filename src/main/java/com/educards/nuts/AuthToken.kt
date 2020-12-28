package com.educards.nuts

import java.net.URI

data class AuthToken(
    val uri: URI,
    val authSessionId: String,
    val expires: Long?,
    val userCredentials: UserCredentials?
) {

    data class UserCredentials(
        val firstName: String?,
        val lastName: String?,
        val login: String?,
        val email: String?
    )

}
