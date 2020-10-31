/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.chorboList.detail

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import es.littledavity.commons.ui.base.BaseViewModel
import es.littledavity.core.database.chorbo.ChorboRepository
import es.littledavity.dynamicfeatures.chorboList.detail.model.ChorboDetailContent
import es.littledavity.dynamicfeatures.chorboList.detail.model.ChorboDetailItem
import es.littledavity.dynamicfeatures.chorboList.detail.model.ChorboDetailMapper
import kotlinx.coroutines.launch
import java.io.File
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

    fun getImageFile(imagePath: String?) = imagePath?.let(::File)

    fun createListWithHeader(): List<ChorboDetailContent> {
        val groupedList = chorboDetail.value?.content?.groupBy { it.section }
        val myList = ArrayList<ChorboDetailContent>()
        groupedList?.let {
            for (i in groupedList.keys) {
                myList.add(ChorboDetailContent.Section(i))
                for (v in groupedList.getValue(i)) {
                    val content = v as ChorboDetailContent.Content
                    myList.add(
                        ChorboDetailContent.Content(
                            content.section,
                            content.content.orEmpty(),
                            content.sectionContent
                        )
                    )
                }
            }
        }
        return myList
    }
}
