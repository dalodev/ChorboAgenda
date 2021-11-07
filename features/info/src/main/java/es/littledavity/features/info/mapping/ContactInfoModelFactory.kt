/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.info.mapping

import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.domain.contacts.entities.Contact
import es.littledavity.features.info.widgets.model.ContactInfoModel
import javax.inject.Inject

internal interface ContactInfoModelFactory {

    fun createInfoModel(
        contact: Contact,
        isLiked: Boolean
    ): ContactInfoModel
}

@BindType(installIn = BindType.Component.VIEW_MODEL)
internal class ContactInfoModelFactoryImpl @Inject constructor(
    private val headerModelFactory: ContactInfoHeaderModelFactory
) : ContactInfoModelFactory {

    override fun createInfoModel(contact: Contact, isLiked: Boolean) = ContactInfoModel(
        id = contact.id,
        headerModel = headerModelFactory.createHeaderModel(contact, isLiked),
        info = contact.info
    )
}
