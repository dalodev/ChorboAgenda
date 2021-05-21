package es.littledavity.commons.ui.widgets.contacts

import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.domain.contacts.Contact
import javax.inject.Inject

interface ContactsModelMapper {
    fun mapToContactModel(contact: Contact): ContactModel
}

@BindType(installIn = BindType.Component.VIEW_MODEL)
internal class ContactsModelMapperImpl @Inject constructor(
) : ContactsModelMapper {
    override fun mapToContactModel(contact: Contact) = ContactModel(
        id = contact.id,
        image = contact.image,
        name = contact.name,
        phone = contact.phone
    )
}