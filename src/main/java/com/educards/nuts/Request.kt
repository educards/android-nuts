package com.educards.nuts

import java.net.URI

/**
 * Generic (protocol independent) representation of a single network request.
 */
open class Request(
    val protocol: Protocol,
    val uri: URI
)
