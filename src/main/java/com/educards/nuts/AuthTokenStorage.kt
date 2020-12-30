package com.educards.nuts

interface AuthTokenStorage {

    fun getAuthToken(): AuthToken?

    fun saveAuthToken(authToken: AuthToken)

    fun removeAuthToken()

}
