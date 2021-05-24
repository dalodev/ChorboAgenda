/*
 * Copyright 2021 dev.id
 */
package es.littledavity.database.chorbo

import es.littledavity.database.chorbo.entities.Contact
import org.junit.Assert.assertEquals
import org.junit.Test

class ChorboTest {

    @Test
    fun createChorbo_ShouldAddCorrectAttributes() {
        val chorboId = 0
        val chorboName = "David"
        val image = "test"
        val whatsapp = "test"
        val chorboFavorite = Contact(
            id = chorboId,
            name = chorboName,
            image = image,
            phone = whatsapp,
            createTimestamp = 500L

        )
        assertEquals(chorboId, chorboFavorite.id)
        assertEquals(chorboName, chorboFavorite.name)
    }
}
