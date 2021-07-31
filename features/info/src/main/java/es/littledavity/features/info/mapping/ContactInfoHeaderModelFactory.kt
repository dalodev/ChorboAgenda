/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.info.mapping

import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.core.factories.IgdbImageSize
import es.littledavity.core.factories.IgdbImageUrlFactory
import es.littledavity.core.factories.createUrls
import es.littledavity.core.formatters.ContactCreationDateFormatter
import es.littledavity.domain.contacts.entities.Contact
import es.littledavity.features.info.widgets.main.header.ContactHeaderImageModel
import es.littledavity.features.info.widgets.main.model.ContactInfoHeaderModel
import javax.inject.Inject

internal interface ContactInfoHeaderModelFactory {
    fun createHeaderModel(contact: Contact, isLiked: Boolean): ContactInfoHeaderModel
}

@BindType(installIn = BindType.Component.VIEW_MODEL)
internal class ContactInfoHeaderModelFactoryImpl @Inject constructor(
    private val igdbImageUrlFactory: IgdbImageUrlFactory,
    private val creationDateFormatter: ContactCreationDateFormatter,
) : ContactInfoHeaderModelFactory {
    override fun createHeaderModel(contact: Contact, isLiked: Boolean) = ContactInfoHeaderModel(
        backgroundImageModels = contact.createBackgroundImageModels(),
        isLiked = isLiked,
        imageUrl = contact.createImageUrl(),
        name = contact.name,
        creationDate = contact.formatCreationDate(),
        instagram = contact.instagram,
        rating = contact.rating,
        age = contact.age,
        country = contact.country,
        phone = contact.phone
    )

    private fun Contact.createBackgroundImageModels(): List<ContactHeaderImageModel> {
        if (gallery.isEmpty()) return listOf(ContactHeaderImageModel.DefaultImage)

        return igdbImageUrlFactory
            .createUrls(gallery, IgdbImageUrlFactory.Config(IgdbImageSize.BIG_SCREENSHOT))
            .map(ContactHeaderImageModel::UrlImage)
    }


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
