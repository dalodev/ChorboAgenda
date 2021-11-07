/*
 * Copyright 2021 dalodev
 */
package es.littledavity.core.mapper

import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.core.R
import es.littledavity.core.providers.StringProvider
import es.littledavity.domain.commons.DomainException
import es.littledavity.domain.commons.entities.Error
import javax.inject.Inject

interface ErrorMapper {
    fun mapToMessage(error: Throwable): String
}

@BindType
internal class ErrorMapperImpl @Inject constructor(
    private val stringProvider: StringProvider
) : ErrorMapper {

    override fun mapToMessage(error: Throwable): String {
        if (error is DomainException) return error.toMessage()

        return stringProvider.getString(R.string.error_unknown_message)
    }

    private fun DomainException.toMessage() = when (error) {
        is Error.ApiError -> (error as Error.ApiError).toMessage()
        is Error.NotFound,
        is Error.Unknown -> stringProvider.getString(R.string.error_unknown_message)
    }

    private fun Error.ApiError.toMessage() = stringProvider.getString(
        when (this) {
            is Error.ApiError.NetworkError -> R.string.error_api_network_message
            is Error.ApiError.ServiceUnavailable -> R.string.error_api_server_message
            is Error.ApiError.ClientError,
            is Error.ApiError.Unknown -> R.string.error_api_unknown_message
        }
    )
}
