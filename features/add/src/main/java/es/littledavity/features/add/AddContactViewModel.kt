package es.littledavity.features.add

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.littledavity.commons.ui.base.BaseViewModel
import es.littledavity.core.mapper.ErrorMapper
import es.littledavity.core.providers.DispatcherProvider
import es.littledavity.core.utils.Logger
import es.littledavity.domain.contacts.entities.Contact
import es.littledavity.domain.contacts.entities.CreationDate
import es.littledavity.domain.contacts.entities.CreationDateCategory
import es.littledavity.domain.contacts.usecases.SaveContactUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddContactViewModel @Inject constructor(
    private val saveContactUseCase: SaveContactUseCase,
    private val dispatcherProvider: DispatcherProvider,
    private val logger: Logger,
    private val errorMapper: ErrorMapper
) : BaseViewModel() {

    fun onToolbarBackButtonClicked() {
        route(AddContactRoute.Back)
    }

    fun onToolbarRightButtonClicked(canDone: Boolean?) {
        if (canDone == true) {
            //TODO route to edit?????
        } else {
            //TODO show error
        }
    }

    private fun createContact() {
        viewModelScope.launch {
        }
    }
}