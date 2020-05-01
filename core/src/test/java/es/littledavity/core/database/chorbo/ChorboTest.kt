package es.littledavity.core.database.chorbo

import org.junit.Assert.assertEquals
import org.junit.Test

class ChorboTest {

    @Test
    fun createChorbo_ShouldAddCorrectAttributes() {
        val characterId = 0L
        val characterName = "David"

        val characterFavorite = Chorbo(
            id = characterId,
            name = characterName
        )

        assertEquals(characterId, characterFavorite.id)
        assertEquals(characterName, characterFavorite.name)
    }
}