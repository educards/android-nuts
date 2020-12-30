package com.educards.nuts

/**
 * Template is a contract between network layer on UI.
 *
 * @param S Data type of a successful network response. In case of HTTP call this is
 * usually the response body returned for code 200.
 */
interface Template<S> {

    fun onRequestInProgress()

    fun onRequestSucceeded(responseData: S?)

    fun onRequestFailed(requestFailData: RequestFailData)

}
