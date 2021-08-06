package es.littledavity.features.edit.widgets.mapping

import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.domain.contacts.entities.Contact
import es.littledavity.features.edit.EditContactUiState
import javax.inject.Inject

internal interface EditContactUiStateFactory {
    fun createWithLoadingState(): EditContactUiState
    fun createWithErrorState(): EditContactUiState
    fun createWithResultState(contact: Contact): EditContactUiState
}

@BindType(installIn = BindType.Component.VIEW_MODEL)
internal class EditContactUiStateFactoryImpl @Inject constructor(
    private val contactModelMapper: EditContactModelFactory
) : EditContactUiStateFactory {
    override fun createWithLoadingState() = EditContactUiState.Loading

    override fun createWithErrorState() = EditContactUiState.Error

    override fun createWithResultState(contact: Contact) = EditContactUiState.Result(
        contactModelMapper.createInfoModel(contact)
    )
}