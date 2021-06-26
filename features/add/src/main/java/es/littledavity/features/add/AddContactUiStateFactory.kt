package es.littledavity.features.add

import com.paulrybitskyi.hiltbinder.BindType
import es.littledavity.commons.ui.widgets.contacts.ContactModel
import es.littledavity.core.providers.StringProvider
import es.littledavity.domain.contacts.entities.Contact
import javax.inject.Inject

interface AddContactUiStateFactory {
    fun createWithNewState(): AddContactUiState
    fun createWithLoadingState(): AddContactUiState
    fun createWithErrorState(): AddContactUiState
    fun createWithResultState(contact: Contact): AddContactUiState
}

@BindType(installIn = BindType.Component.VIEW_MODEL)
internal class AddContactUiStateFactoryImpl @Inject constructor(
    private val stringProvider: StringProvider
) : AddContactUiStateFactory {
    override fun createWithNewState() = AddContactUiState.New

    override fun createWithLoadingState() = AddContactUiState.Loading

    override fun createWithErrorState() = AddContactUiState.Error

    override fun createWithResultState(contact: Contact) = AddContactUiState.Result(
        ContactModel(
            id = 0,
            "avatarImage", //TODO file path string
            name = "name",
            phone = null,
            creationDate = "",
            instagram = null
        )
    )

}