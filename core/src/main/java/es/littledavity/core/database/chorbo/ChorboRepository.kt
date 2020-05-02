package es.littledavity.core.database.chorbo

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
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
    fun getAllChorboLiveData(): LiveData<List<Chorbo>> =
        chorboDao.getAllChorbosLiveData()

    /**
     * Obtain all database added Chorbo ordering by name field.
     *
     * @return List with chorbos.
     */
    suspend fun getChorbos() =
        chorboDao.getChorbos()

    /**
     * Obtain database Chorbo by identifier.
     *
     * @param chorboId Chorbo identifier.
     *
     * @return Chorbo if exist, otherwise null
     */
    suspend fun getChorbo(chorboId: Long): Chorbo? =
        chorboDao.getChorbo(chorboId)

    /**
     * Obtain all database added Chorbo ordering by name field.
     *
     * @param offset Skip the specified number of resources in the result set.
     * @param limit Limit the result set to the specified number of resources.
     * @return List with chorbos.
     */
    suspend fun getChorbos(offset: Int, limit: Int) =
        chorboDao.getChorbos(offset, limit)

    /**
     * Delete all database chorbo.
     */
    suspend fun deleteAllChorbos() =
        chorboDao.deleteAllChorbos()

    /**
     * Delete database chorbo by identifier.
     *
     * @param chorboId chorbo identifier.
     */
    suspend fun deleteChorboById(chorboId: Long) =
        chorboDao.deleteChorboById(chorboId)

    /**
     * Delete database chorbo.
     *
     * @param chorbo Chorbos.
     */
    suspend fun deleteChorbo(chorbo: Chorbo) =
        chorboDao.deleteChorbo(chorbo)

    /**
     * Add to database a list of chorbos.
     *
     * @param chorbos List of chorbos.
     */
    suspend fun insertChorbos(chorbos: List<Chorbo>) =
        chorboDao.insertChorbos(chorbos)

    /**
     * Add to database a chrobo.
     *
     * @param id Chorbo identifier.
     * @param name Chorbo name.
     */
    suspend fun insertChorbo(id: Long, name: String) {
        val chorboFavorite = Chorbo(
            id = id,
            name = name
        )
        chorboDao.insertChorbo(chorboFavorite)
    }
}