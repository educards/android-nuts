package com.educards.nuts

data class RequestFailData(
    val request: Request,
    val requestFailReason: RequestFailReason
)
