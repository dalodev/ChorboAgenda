package es.littledavity.features.edit.widgets.mapping

import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.core.factories.IgdbImageSize
import es.littledavity.core.factories.IgdbImageUrlFactory
import es.littledavity.core.formatters.ContactCreationDateFormatter
import es.littledavity.domain.contacts.entities.Contact
import es.littledavity.features.edit.widgets.model.ContactInfoHeaderModel
import javax.inject.Inject

internal interface EditContactHeaderModelFactory {
    fun createHeaderModel(contact: Contact): ContactInfoHeaderModel
}

@BindType(installIn = BindType.Component.VIEW_MODEL)
internal class ContactInfoHeaderModelFactoryImpl @Inject constructor(
    private val igdbImageUrlFactory: IgdbImageUrlFactory,
    private val creationDateFormatter: ContactCreationDateFormatter,
) : EditContactHeaderModelFactory {

    override fun createHeaderModel(contact: Contact) = ContactInfoHeaderModel(
        galleryImages = contact.gallery,
        imageUrl = contact.createImageUrl(),
        name = contact.name,
        creationDate = contact.formatCreationDate(),
        instagram = contact.instagram,
        rating = contact.rating,
        age = contact.age,
        country = contact.country,
        phone = contact.phone
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