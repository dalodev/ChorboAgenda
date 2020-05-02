package es.littledavity.core.database.chorbo

import org.junit.Assert.assertEquals
import org.junit.Test

class ChorboTest {

    @Test
    fun createChorbo_ShouldAddCorrectAttributes() {
        val chorboId = 0L
        val chorboName = "David"

        val chorboFavorite = Chorbo(
            id = chorboId,
            name = chorboName
        )

        assertEquals(chorboId, chorboFavorite.id)
        assertEquals(chorboName, chorboFavorite.name)
    }
}