package es.littledavity.features.add

import dagger.hilt.android.lifecycle.HiltViewModel
import es.littledavity.commons.ui.base.BaseViewModel
import es.littledavity.core.mapper.ErrorMapper
import es.littledavity.core.providers.DispatcherProvider
import es.littledavity.core.utils.Logger
import javax.inject.Inject

@HiltViewModel
class AddContactViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val logger: Logger,
    private val errorMapper: ErrorMapper
) : BaseViewModel() {

    fun onToolbarBackButtonClicked() {
        route(AddContactRoute.Back)
    }

    fun onToolbarRightButtonClicked() {
        //TODO create contact
    }
}