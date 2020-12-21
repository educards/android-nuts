package com.educards.nuts

/**
 * Generic reason of a failed network request.
 *
 * This enum is used primarily by "UI updater" of the chosen template to update the UI after
 * request fails.
 */
enum class RequestFailReason {

    /**
     * Networking is disabled locally on the device.
     *
     * This means that the networking issue may be resolved simply by turning on
     * the "WiFi" or enabling "mobile data" in settings of the device.
     */
    NETWORKING_DISABLED,

    /**
     * The request was successfully executed but server responded with
     * "authentication denied" response.
     */
    AUTH_ERROR,

    /**
     * The request was successfully executed but server failed to fulfill it due
     * to internal error.
     */
    SERVER_ERROR,

    OTHER

}
