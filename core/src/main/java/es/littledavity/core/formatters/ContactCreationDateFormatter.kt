/*
 * Copyright 2021 dev.id
 */
package es.littledavity.core.formatters

import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.core.R
import es.littledavity.core.providers.StringProvider
import es.littledavity.domain.contacts.entities.Contact
import es.littledavity.domain.contacts.entities.CreationDate
import es.littledavity.domain.contacts.entities.CreationDateCategory
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

interface ContactCreationDateFormatter {
    fun formatReleaseDate(contact: Contact): String
}

@BindType
internal class ContactCreationDateFormatterImpl @Inject constructor(
    private val stringProvider: StringProvider,
    private val relativeDateFormatter: RelativeDateFormatter
) : ContactCreationDateFormatter {

    private companion object {
        private const val COMPLETE_DATE_FORMATTING_PATTERN = "MMM dd, yyyy"
    }

    override fun formatReleaseDate(contact: Contact): String {
        val date = contact.creationDate ?: return stringProvider.getString(R.string.unknown)

        return when (val category = date.category) {
            CreationDateCategory.YYYY_MMMM_DD -> date.formatCompleteDate()
            else -> throw IllegalStateException("Unknown category: $category.")
        }
    }

    private fun CreationDate.formatCompleteDate(): String {
        val releaseLocalDateTime = toLocalDateTime()
        val formattedReleaseDate = DateTimeFormatter
            .ofPattern(COMPLETE_DATE_FORMATTING_PATTERN)
            .format(releaseLocalDateTime)

        return buildString {
            append(formattedReleaseDate)
            append(" (")
            append(relativeDateFormatter.formatRelativeDate(releaseLocalDateTime))
            append(")")
        }
    }

    private fun CreationDate.toLocalDateTime(): LocalDateTime {
        return LocalDateTime.ofInstant(
            Instant.ofEpochMilli(checkNotNull(date)),
            ZoneId.systemDefault()
        )
    }
}
