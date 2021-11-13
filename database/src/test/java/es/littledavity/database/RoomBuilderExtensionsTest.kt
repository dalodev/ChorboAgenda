/*
 * Copyright 2021 dalodev
 */
package es.littledavity.database

import androidx.room.Room
import es.littledavity.database.chorbo.ContactsTypeConverter
import es.littledavity.database.commons.RoomTypeConverter
import es.littledavity.database.commons.addTypeConverters
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class RoomBuilderExtensionsTest {

    @Test
    fun addTypeConverters_shouldReturnRoomBuilder() {
        val builder = Room.databaseBuilder(
            mockk(),
            ChorboagendaDatabase::class.java,
            Constants.DATABASE_NAME
        )
        val typeConverter: Set<RoomTypeConverter> = listOf(ContactsTypeConverter(mockk())).toSet()
        val result = builder.addTypeConverters(typeConverter)
        assertThat(result).isEqualTo(builder)
    }
}
