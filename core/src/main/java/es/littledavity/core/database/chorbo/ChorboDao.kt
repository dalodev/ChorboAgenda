/*
 * Copyright 2020 littledavity
 */
package es.littledavity.core.database.chorbo

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

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
     * @return [LiveData] List with favorite chorbos.
     */
    @Query("SELECT * FROM chorbo ORDER BY name")
    fun getAllChorbosLiveData(): LiveData<List<Chorbo>>

    /**
     * Obtain database chorbo by identifier.
     *
     * @param chorboId chorbo identifier.
     *
     * @return chorbo if exist, otherwise null.
     */
    @Query("SELECT * FROM chorbo WHERE id = :chorboId")
    suspend fun getChorbo(chorboId: Long): Chorbo?

    /**
     * Obtain all database added chorbos ordering by name field.
     *
     * @return List with chorbo.
     */
    @Query("SELECT * FROM chorbo ORDER BY name")
    suspend fun getChorbos(): List<Chorbo>

    /**
     * Obtain all database added chorbos ordering by name field.
     *
     * @return List with chorbo.
     */
    @Query("SELECT * FROM chorbo ORDER BY name LIMIT :limit OFFSET :offset")
    suspend fun getChorbos(offset: Int, limit: Int): List<Chorbo>

    /**
     * Delete all database chorbo.
     */
    @Query("DELETE FROM chorbo")
    suspend fun deleteAllChorbos()

    /**
     * Delete database chorbo by identifier.
     *
     * @param chorboId Chorbo identifier.
     */
    @Query("DELETE FROM chorbo WHERE id = :chorboId")
    suspend fun deleteChorboById(chorboId: Long)

    /**
     * Delete database chorbos by identifier.
     *
     * @param idList Chorbos identifier.
     */
    @Query("DELETE FROM chorbo where id in (:idList)")
    suspend fun deleteChorbosById(idList: List<Long>)

    /**
     * Delete database Chorbo.
     *
     * @param chorbo Chorbo.
     */
    @Delete
    suspend fun deleteChorbo(chorbo: Chorbo)

    /**
     * Add to database a list of chorbos.
     *
     * @param chorbo List of chorbo.
     */
    @Insert
    suspend fun insertChorbos(chorbo: List<Chorbo>)

    /**
     * Add to database a chorbo.
     *
     * @param chorbo Chorbo.
     */
    @Insert
    suspend fun insertChorbo(chorbo: Chorbo)

    /**
     * Update a chorbo list.
     *
     * @param chorbos Chorbo list.
     */
    @Update
    fun updateChorbos(vararg chorbos: Chorbo)

    /**
     * Update a single chorbo.
     *
     * @param chorbo Chorbo.
     */
    @Update
    fun updateChorbo(chorbo: Chorbo)
}
