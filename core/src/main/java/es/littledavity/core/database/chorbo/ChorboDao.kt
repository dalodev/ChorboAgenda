package es.littledavity.core.database.chorbo

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

/**
 * The data access object for the [Chorbo] class.
 *
 * @see Dao
 */
@Dao
interface ChorboDao {

    /**
     * Obtain all database added chorbo ordering by name field.
     *
     * @return [LiveData] List with favorite characters.
     */
    @Query("SELECT * FROM chorbo ORDER BY name")
    fun getAllChorboLiveData(): LiveData<List<Chorbo>>
}