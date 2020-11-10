/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.chorboList.detail

import android.net.Uri
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import es.littledavity.commons.ui.base.BaseViewModel
import es.littledavity.core.database.chorbo.ChorboRepository
import es.littledavity.dynamicfeatures.chorboList.detail.model.ChorboDetailItem
import es.littledavity.dynamicfeatures.chorboList.detail.model.ChorboDetailMapper
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * View model responsible for preparing and managing the data for [ChorboDetailFragment].
 *
 * @see BaseViewModel
 */
class ChorboDetailViewModel @Inject constructor(
    @VisibleForTesting(otherwise = PRIVATE)
    val chorboRepository: ChorboRepository,
    @VisibleForTesting(otherwise = PRIVATE)
    val mapper: ChorboDetailMapper
) : BaseViewModel() {

    var chorboDetail = MutableLiveData<ChorboDetailItem>()
    private val _viewState = MutableLiveData<ChorboDetailViewState>()
    val viewState: LiveData<ChorboDetailViewState>
        get() = _viewState

    fun loadData(id: Long) {
        viewModelScope.launch {
            val chorbo = chorboRepository.getChorbo(id)
            chorbo?.let {
                val chorboDetailItem = mapper.map(it)
                chorboDetail.postValue(chorboDetailItem)
                _viewState.postValue(ChorboDetailViewState.Loaded(chorboDetailItem))
            }
        }
    }

    fun getImageUri(): Uri = Uri.parse(chorboDetail.value?.image)
}
