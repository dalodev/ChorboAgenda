/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.info

import es.littledavity.features.info.widgets.header.ContactHeaderImageModel
import es.littledavity.features.info.widgets.header.gallery.ContactGalleryModel
import es.littledavity.features.info.widgets.mapToContactGalleryModels
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ContactInfoITemMappingsTest {

    @Test
    fun mapToContactGalleryModels_whenDefault_shouldReturnListContactGalleryModel() {
        val contactList: List<ContactHeaderImageModel> =
            listOf(ContactHeaderImageModel.DefaultImage)
        val resultList = contactList.mapToContactGalleryModels()
        assertThat(resultList).isEqualTo(listOf(ContactGalleryModel.DefaultImage))
    }

    @Test
    fun mapToContactGalleryModels_whenNotDefault_shouldReturnListContactGalleryModel() {
        val contactList: List<ContactHeaderImageModel> =
            listOf(ContactHeaderImageModel.UrlImage("test"))
        val resultList = contactList.mapToContactGalleryModels()
        assertThat(resultList).isEqualTo(listOf(ContactGalleryModel.UrlImage("test")))
    }
}
