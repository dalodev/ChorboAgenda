/*
 * Copyright 2021 dalodev
 */
package es.littledavity.core.providers

import com.paulrybitskyi.hiltbinder.BindType
import java.util.concurrent.TimeUnit
import javax.inject.Inject

interface TimestampProvider {
    fun getUnixTimestamp(timeUnit: TimeUnit = TimeUnit.MILLISECONDS): Long
}

@BindType
internal class TimestampProviderImpl @Inject constructor() : TimestampProvider {

    override fun getUnixTimestamp(timeUnit: TimeUnit) =
        timeUnit.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
}
