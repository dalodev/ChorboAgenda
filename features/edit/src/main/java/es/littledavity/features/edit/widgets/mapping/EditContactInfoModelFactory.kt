package es.littledavity.features.edit.widgets.mapping

import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.domain.contacts.entities.Info
import es.littledavity.features.edit.widgets.model.ContactInfoItemModel
import javax.inject.Inject

internal interface EditContactInfoModelFactory {
    fun createInfoModel(infoList: List<Info>): List<ContactInfoItemModel>
}

@BindType(installIn = BindType.Component.VIEW_MODEL)
internal class EditContactInfoModelFactoryImpl @Inject constructor(
) : EditContactInfoModelFactory {

    override fun createInfoModel(infoList: List<Info>) = infoList.map {
        ContactInfoItemModel(title = it.title, description = it.description)
    }
}