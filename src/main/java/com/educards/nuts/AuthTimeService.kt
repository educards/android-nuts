package com.educards.nuts

/**
 * Time service which is used to validate [AuthToken] validity by checking
 * token's [expiration timestamp][AuthToken.expires].
 */
interface AuthTimeService {

    fun now(): Long

}
