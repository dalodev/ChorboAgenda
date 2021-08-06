package es.littledavity.features.edit.widgets.model

internal data class ContactInfoModel(
    val id: Int,
    val headerModel: ContactInfoHeaderModel,
    val info: List<ContactInfoItemModel>
)