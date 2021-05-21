/*
 * Copyright 2020 littledavity
 */
package es.littledavity.database.chorbo

import es.littledavity.database.chorbo.entities.Chorbo
import org.junit.Assert.assertEquals
import org.junit.Test

class ChorboTest {

    @Test
    fun createChorbo_ShouldAddCorrectAttributes() {
        val chorboId = 0L
        val chorboName = "David"
        val image = "test"
        val code = "test"
        val countryName = "test"
        val flag = "test"
        val whatsapp = "test"
        val instagram = "test"

        val chorboFavorite = Chorbo(
            id = chorboId,
            name = chorboName,
            image = image,
            countryCode = code,
            countryName = countryName,
            flag = flag,
            whatsapp = whatsapp,
            instagram = instagram
        )

        assertEquals(chorboId, chorboFavorite.id)
        assertEquals(chorboName, chorboFavorite.name)
    }
}
