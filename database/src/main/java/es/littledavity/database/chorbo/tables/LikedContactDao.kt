/*
 * Copyright 2021 dalodev
 */
package es.littledavity.database.chorbo.tables

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.littledavity.database.chorbo.entities.Contact
import es.littledavity.database.chorbo.entities.LikedContact
import kotlinx.coroutines.flow.Flow

@Dao
internal interface LikedContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLikeContact(likeContact: LikedContact)

    @Query(
        """
        DELETE FROM ${LikedContact.Schema.TABLE_NAME}
        WHERE ${LikedContact.Schema.CONTACT_ID} = :contactId
        """
    )
    suspend fun deleteLikedContact(contactId: Int)

    @Query(
        """
        SELECT COUNT(*) FROM ${LikedContact.Schema.TABLE_NAME}
        WHERE ${LikedContact.Schema.CONTACT_ID} = :contactId
        """
    )
    suspend fun isContactLiked(contactId: Int): Boolean

    @Query(
        """
        SELECT COUNT(*) FROM ${LikedContact.Schema.TABLE_NAME}
        WHERE ${LikedContact.Schema.CONTACT_ID} = :contactId
        """
    )
    fun observeContactLikeState(contactId: Int): Flow<Boolean>

    @Query(
        """
        SELECT c.* FROM ${Contact.Schema.TABLE_NAME} AS c
        INNER JOIN ${LikedContact.Schema.TABLE_NAME} AS lc
        ON lc.${LikedContact.Schema.CONTACT_ID} = c.${Contact.Schema.ID}
        ORDER BY lc.${LikedContact.Schema.LIKE_TIMESTAMP} DESC
        LIMIT :offset, :limit
        """
    )
    fun observeLikedContacts(offset: Int, limit: Int): Flow<List<Contact>>
}
