/*
 * Copyright 2021 dalodev
 */
package es.littledavity.database.chorbo.tables

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.littledavity.database.chorbo.entities.Contact
import kotlinx.coroutines.flow.Flow

/**
 * The data access object for the [Contact] class.
 *
 * @see Dao
 */
@Dao
internal interface ContactDao {

    @Query(
        """
        SELECT * FROM ${Contact.Schema.TABLE_NAME}
        WHERE LOWER(${Contact.Schema.NAME}) LIKE (:searchQuery || '%')
        ORDER BY ${Contact.Schema.ID} ASC
        LIMIT :offset, :limit
        """
    )
    suspend fun searchContacts(searchQuery: String, offset: Int, limit: Int): List<Contact>

    @Query(
        """
        SELECT * FROM ${Contact.Schema.TABLE_NAME}
        ORDER BY ${Contact.Schema.ID} ASC
        LIMIT :offset, :limit
        """
    )
    fun observeContacts(offset: Int, limit: Int): Flow<List<Contact>>

    @Query("SELECT * FROM ${Contact.Schema.TABLE_NAME} WHERE ${Contact.Schema.ID} = :chorboId")
    suspend fun getChorbo(chorboId: Int): Contact?

    @Query("SELECT * FROM ${Contact.Schema.TABLE_NAME} WHERE ${Contact.Schema.ID} = :chorboId")
    fun getChorboFlow(chorboId: Int): Flow<Contact>

    @Query("SELECT * FROM ${Contact.Schema.TABLE_NAME} ORDER BY ${Contact.Schema.NAME}")
    suspend fun getChorbos(): List<Contact>

    @Query("DELETE FROM ${Contact.Schema.TABLE_NAME} WHERE ${Contact.Schema.ID} = :chorboId")
    suspend fun deleteChorboById(chorboId: Int)

    @Query("DELETE FROM ${Contact.Schema.TABLE_NAME} where ${Contact.Schema.ID} in (:idList)")
    suspend fun deleteChorbosById(idList: List<Int>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChorbos(chorbo: List<Contact>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChorbo(chorbo: Contact): Long
}
