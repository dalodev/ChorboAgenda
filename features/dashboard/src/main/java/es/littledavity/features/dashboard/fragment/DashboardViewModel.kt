/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.dashboard.fragment

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.littledavity.commons.ui.base.BaseViewModel
import es.littledavity.domain.contacts.entities.Contact
import es.littledavity.domain.contacts.entities.CreationDate
import es.littledavity.domain.contacts.entities.CreationDateCategory
import es.littledavity.domain.contacts.usecases.SaveContactUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor() : BaseViewModel() {

    fun onToolbarRightButtonClicked() {
        route(DashboardRoute.Search)
    }

    fun onAddContactButtonClicked() {
        route(DashboardRoute.Add)
    }
}
