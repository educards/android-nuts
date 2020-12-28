package com.educards.nuts

import android.app.Activity

interface AuthTokenProvider {

    /**
     * This method retrieves [AuthToken] from the authentication server.
     *
     * The UI representation ("login screen") of the provider is optional. The
     * provider implementation without any UI is also valid.
     *
     * @param activity The [activity][Activity] which is currently started and which initiates
     * the network request requiring authentication.
     *
     * TODO Describe all steps of authentication procedure here.
     *
     * @return Installed/cached [AuthToken] or `null` if no [valid][AuthToken.expires] cached [AuthToken]
     * exists.
     */
    fun getInstalledAuthToken(activity: Activity): AuthToken?

}
