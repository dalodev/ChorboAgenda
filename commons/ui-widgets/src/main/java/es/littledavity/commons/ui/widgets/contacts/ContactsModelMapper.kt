/*
 * Copyright 2021 dev.id
 */
package es.littledavity.commons.ui.widgets.contacts

import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.core.factories.IgdbImageSize
import es.littledavity.core.factories.IgdbImageUrlFactory
import es.littledavity.core.formatters.ContactCreationDateFormatter
import es.littledavity.domain.contacts.entities.Contact
import javax.inject.Inject

interface ContactsModelMapper {
    fun mapToContactModel(contact: Contact): ContactModel
}

@BindType(installIn = BindType.Component.VIEW_MODEL)
internal class ContactsModelMapperImpl @Inject constructor(
    private val creationDateFormatter: ContactCreationDateFormatter,
    private val igdbImageUrlFactory: IgdbImageUrlFactory,
) : ContactsModelMapper {
    override fun mapToContactModel(contact: Contact) = ContactModel(
        id = contact.id,
        coverImageUrl = contact.createImageUrl(),
        name = contact.name,
        phone = contact.phone,
        creationDate = contact.formatCreationDate(),
    )

    private fun Contact.createImageUrl(): String? {
        return image?.let { cover ->
            igdbImageUrlFactory.createUrl(
                cover,
                IgdbImageUrlFactory.Config(IgdbImageSize.BIG_COVER)
            )
        }
    }

    private fun Contact.formatCreationDate() = creationDateFormatter.formatReleaseDate(this)
}
