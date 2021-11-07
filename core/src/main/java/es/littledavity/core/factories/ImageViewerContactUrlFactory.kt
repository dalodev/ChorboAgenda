/*
 * Copyright 2021 dalodev
 */
package es.littledavity.core.factories

import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.domain.contacts.entities.Contact
import javax.inject.Inject

interface ImageViewerContactUrlFactory {
    fun createCoverImageUrl(contact: Contact): String?
    fun createGalleryImageUrls(contact: Contact): List<String>
    fun createScreenShotImageUrls(contact: Contact): List<String>
}

@BindType
internal class ImageViewerContactUrlFactoryImpl @Inject constructor(
    private val igdbImageUrlFactory: IgdbImageUrlFactory
) : ImageViewerContactUrlFactory {
    private companion object {
        private val IMAGE_SIZE = IgdbImageSize.HD
    }

    override fun createCoverImageUrl(contact: Contact) = contact.image?.let { cover ->
        igdbImageUrlFactory.createUrl(cover, IgdbImageUrlFactory.Config(IMAGE_SIZE))
    }

    override fun createGalleryImageUrls(contact: Contact) = igdbImageUrlFactory.createUrls(
        contact.gallery, IgdbImageUrlFactory.Config(IMAGE_SIZE)
    )

    override fun createScreenShotImageUrls(contact: Contact) = igdbImageUrlFactory.createUrls(
        contact.screenshots,
        IgdbImageUrlFactory.Config(IMAGE_SIZE)
    )
}
