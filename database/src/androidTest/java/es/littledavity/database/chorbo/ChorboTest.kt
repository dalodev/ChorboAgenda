/*
 * Copyright 2021 dev.id
 */
package es.littledavity.database.chorbo

import es.littledavity.database.chorbo.entities.Contact
import es.littledavity.database.chorbo.entities.CreationDate
import es.littledavity.database.chorbo.entities.CreationDateCategory
import es.littledavity.database.chorbo.entities.Image
import org.junit.Assert.assertEquals
import org.junit.Test

class ChorboTest {

    @Test
    fun createChorbo_ShouldAddCorrectAttributes() {
        val chorboId = 0
        val chorboName = "David"
        val image = Image("test", 1, 1)
        val whatsapp = "test"
        val chorboFavorite = Contact(
            id = chorboId,
            name = chorboName,
            image = image,
            phone = whatsapp,
            createTimestamp = 500L,
            age = "18",
            artworks = emptyList(),
            country = "test",
            creationDate = CreationDate(1L, 2021, CreationDateCategory.YYYY_MMMM_DD),
            instagram = "test",
            rating = "10",
            screenshots = emptyList(),
            infoList = emptyList()
        )
        assertEquals(chorboId, chorboFavorite.id)
        assertEquals(chorboName, chorboFavorite.name)
    }
}
