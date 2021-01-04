package com.educards.nuts

// TODO documentation
//      - in memory storage
//      - google credentials storage
//      - shared memory storage (?)
interface AuthTokenStorage {

    fun getAuthToken(): AuthToken?

    fun saveAuthToken(authToken: AuthToken)

    fun removeAuthToken()

    /**
     * @param [now] Time in milliseconds provided by relevant time service.
     * @return [true] if [authToken][getAuthToken] exists and is
     * still considered valid in a provided time ([now]).
     */
    fun isTokenValid(now: Long): Boolean

}
