/*
 * Copyright 2021 dalodev
 */
package es.littledavity.core.providers

import com.paulrybitskyi.hiltbinder.BindType
import java.time.LocalDateTime
import javax.inject.Inject

interface TimeProvider {
    fun getCurrentDateTime(): LocalDateTime
}

@BindType
internal class TimeProviderImpl @Inject constructor() : TimeProvider {

    override fun getCurrentDateTime(): LocalDateTime = LocalDateTime.now()
}
