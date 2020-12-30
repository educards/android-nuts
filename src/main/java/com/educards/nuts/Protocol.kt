package com.educards.nuts

/**
 * Enumeration of Nuts supported networking/communication protocols.
 */
enum class Protocol {

    /**
     * Implementations:
     * * [Retrofit2](https://github.com/educards-com/android-nuts-retrofit2)
     */
    HTTP,

    /**
     * Implementations:
     * * [RabbitMq](https://github.com/educards-com/android-nuts-rabbitmq)
     */
    AMQP

}
