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

    /**
     * Checks whether token is considered expired in a given time.
     *
     * **Note**: If the token provider didn't explicitly specify the validity
     * of this token, then it's validity can't be checked locally and this
     * method always returns [false].
     *
     * @param nowMs Current time provided by relevant time provider.
     */
    fun isExpired(nowMs: Long): Boolean {
        return expires != null && expires < nowMs
    }

}
