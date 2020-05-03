package es.littledavity.dynamicfeatures.create

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.lifecycle.ViewModel
import es.littledavity.commons.ui.base.BaseViewModel
import es.littledavity.core.database.chorbo.ChorboRepository
import javax.inject.Inject

/**
 * View model responsible for preparing and managing the data for [CreateFragment].
 *
 * @see ViewModel
 */
class CreateViewModel @Inject constructor(
    @VisibleForTesting(otherwise = PRIVATE)
    val chorboRepository: ChorboRepository
) : BaseViewModel() {

}