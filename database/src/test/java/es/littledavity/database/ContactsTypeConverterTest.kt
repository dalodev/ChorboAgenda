/*
 * Copyright 2021 dalodev
 */
package es.littledavity.database

import es.littledavity.core.JsonConverter
import es.littledavity.database.chorbo.ContactsTypeConverter
import es.littledavity.database.chorbo.DatabaseContact
import es.littledavity.database.chorbo.DatabaseImage
import es.littledavity.database.chorbo.entities.CreationDate
import es.littledavity.database.chorbo.entities.CreationDateCategory
import es.littledavity.database.chorbo.entities.Image
import es.littledavity.database.chorbo.entities.Info
import es.littledavity.database.commons.RoomTypeConverter
import io.mockk.coEvery
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class ContactsTypeConverterTest {

    private lateinit var jsonConverter: JsonConverter
    private lateinit var typeConverter: Set<RoomTypeConverter>

    @Before
    fun setup() {
        jsonConverter = mockk()
        typeConverter = listOf(
            ContactsTypeConverter(
                jsonConverter
            )
        ).toSet()
        coEvery { jsonConverter.json } returns mockk()
        coEvery { jsonConverter.json.serializersModule } returns mockk()
    }

    @Test
    fun fromInfoList_shouldReturnString() {
        val converter = typeConverter.first() as ContactsTypeConverter
        coEvery { jsonConverter.json.encodeToString<List<Info>?>(any(), any()) } returns "test"
        val result = converter.fromInfoList(databaseContact.infoList)
        assertThat(result).isEqualTo("test")
    }

    @Test
    fun toInfoList_whenEmptyJson_shouldReturnList() {
        val converter = typeConverter.first() as ContactsTypeConverter
        val result = converter.toInfoList("")
        assertThat(result).isEqualTo(emptyList<Info>())
    }

    @Test
    fun toInfoList_whenNotEmpty_shouldReturnList() {
        val list = listOf(Info("a", "b"))
        val converter = typeConverter.first() as ContactsTypeConverter
        coEvery { jsonConverter.json.decodeFromString<List<Info>>(any(), any()) } returns list
        val result = converter.toInfoList("")
        assertThat(result).isEqualTo(list)
    }

    @Test
    fun fromImage_shouldReturnString() {
        val converter = typeConverter.first() as ContactsTypeConverter
        coEvery { jsonConverter.json.encodeToString<Image?>(any(), any()) } returns "test"
        val result = converter.fromImage(mockk())
        assertThat(result).isEqualTo("test")
    }

    @Test
    fun toImage_shouldReturnImage() {
        val image = Image("1", 100, 100, false)
        val converter = typeConverter.first() as ContactsTypeConverter
        coEvery { jsonConverter.json.decodeFromString<Image>(any(), any()) } returns image
        val result = converter.toImage("")
        assertThat(result).isEqualTo(image)
    }

    @Test
    fun fromImages_shouldReturnString() {
        val converter = typeConverter.first() as ContactsTypeConverter
        coEvery { jsonConverter.json.encodeToString<MutableList<Image>?>(any(), any()) } returns "test"
        val result = converter.fromImages(mockk())
        assertThat(result).isEqualTo("test")
    }

    @Test
    fun toImages_shouldReturnImage() {
        val images = mutableListOf(Image("1", 100, 100, false))
        val converter = typeConverter.first() as ContactsTypeConverter
        coEvery { jsonConverter.json.decodeFromString<MutableList<Image>>(any(), any()) } returns images
        val result = converter.toImages("")
        assertThat(result).isEqualTo(images)
    }

    @Test
    fun fromCreationDate_shouldReturnString() {
        val converter = typeConverter.first() as ContactsTypeConverter
        coEvery { jsonConverter.json.encodeToString<CreationDate>(any(), any()) } returns "test"
        val result = converter.fromCreationDate(mockk())
        assertThat(result).isEqualTo("test")
    }

    @Test
    fun toReleaseDates_shouldReturnImage() {
        val date = CreationDate(1L, 1992, CreationDateCategory.YYYY_MMMM_DD)
        val converter = typeConverter.first() as ContactsTypeConverter
        coEvery { jsonConverter.json.decodeFromString<CreationDate>(any(), any()) } returns date
        val result = converter.toReleaseDates("")
        assertThat(result).isEqualTo(date)
    }

    @Test
    fun fromCreationDateCategory_shouldReturnString() {
        val converter = typeConverter.first() as ContactsTypeConverter
        coEvery { jsonConverter.json.encodeToString<CreationDateCategory>(any(), any()) } returns "test"
        val result = converter.fromCreationDateCategory(mockk())
        assertThat(result).isEqualTo("test")
    }

    @Test
    fun toCreationDateCategory_shouldReturnImage() {
        val category = CreationDateCategory.YYYY_MMMM_DD
        val converter = typeConverter.first() as ContactsTypeConverter
        coEvery { jsonConverter.json.decodeFromString<CreationDateCategory>(any(), any()) } returns category
        val result = converter.toCreationDateCategory("")
        assertThat(result).isEqualTo(category)
    }

    private val databaseContact = DatabaseContact(
        id = 1,
        name = "Contact1",
        phone = "12345123",
        image = DatabaseImage("test", 1, 1, true),
        artworks = mutableListOf(),
        screenshots = mutableListOf(),
        creationDate = CreationDate(1L, 1, CreationDateCategory.YYYY_MMMM_DD),
        age = "18",
        country = "Espana",
        rating = "10/10",
        instagram = "@Littledavity",
        infoList = emptyList(),
        createTimestamp = 1000L

    )
}
