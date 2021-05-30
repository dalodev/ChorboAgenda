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
class DashboardViewModel @Inject constructor(
    private val saveContactUseCase: SaveContactUseCase
) : BaseViewModel() {

    fun onToolbarRightButtonClicked() {
        route(DashboardRoute.Search)
    }

    fun onExtraToolbarRightButtonClicked() {
        viewModelScope.launch {
            saveContactUseCase.execute(
                SaveContactUseCase.Params(
                    Contact(
                        id = 0,
                        image = null,
                        name = "David",
                        phone = "615870223",
                        age = "28",
                        country = "Espana",
                        creationDate = CreationDate(1L, 2021, CreationDateCategory.YYYY_MMMM_DD),
                        rating = "5/10",
                        gallery = emptyList(),
                        screenshots = emptyList()
                    )
                )
            )
        }
    }
}
