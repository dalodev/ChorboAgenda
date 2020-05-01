package es.littledavity.core.database.chorbo

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.lifecycle.LiveData
import javax.inject.Inject

/**
 * Repository module for handling chorbo data operations [ChorboDao].
 */
class ChorboRepository @Inject constructor(
    @VisibleForTesting(otherwise = PRIVATE)
    internal val chorboDao: ChorboDao
) {

    /**
     * Obtain all database added chorbo ordering by name field.
     *
     * @return [LiveData] List with chorbo.
     */
    fun getAllCharactersFavoriteLiveData(): LiveData<List<Chorbo>> =
        chorboDao.getAllChorboLiveData()
}