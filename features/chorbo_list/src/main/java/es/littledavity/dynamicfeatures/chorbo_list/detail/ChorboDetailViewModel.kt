package es.littledavity.dynamicfeatures.chorbo_list.detail

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.lifecycle.ViewModel
import es.littledavity.core.database.chorbo.ChorboRepository
import es.littledavity.dynamicfeatures.chorbo_list.detail.model.ChorboDetailMapper
import javax.inject.Inject

/**
 * View model responsible for preparing and managing the data for [ChorboDetailFragment].
 *
 * @see ViewModel
 */
class ChorboDetailViewModel @Inject constructor(
    @VisibleForTesting(otherwise = PRIVATE)
    val chorboRepository: ChorboRepository,
    @VisibleForTesting(otherwise = PRIVATE)
    val chorboDetailMapper: ChorboDetailMapper
) : ViewModel() {

}