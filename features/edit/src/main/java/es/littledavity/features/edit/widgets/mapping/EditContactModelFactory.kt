package es.littledavity.features.edit.widgets.mapping

import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.domain.contacts.entities.Contact
import es.littledavity.features.edit.widgets.model.ContactInfoModel
import javax.inject.Inject

internal interface EditContactModelFactory {

    fun createInfoModel(
        contact: Contact,
    ): ContactInfoModel
}

@BindType(installIn = BindType.Component.VIEW_MODEL)
internal class EditContactModelFactoryImpl @Inject constructor(
    private val headerModelFactory: EditContactHeaderModelFactory,
    private val infoModelFactory: EditContactInfoModelFactory
) : EditContactModelFactory {

    override fun createInfoModel(contact: Contact) = ContactInfoModel(
        id = contact.id,
        headerModel = headerModelFactory.createHeaderModel(contact),
        info = infoModelFactory.createInfoModel(contact.info)
    )
}