package es.littledavity.features.likes

import es.littledavity.commons.ui.widgets.contacts.ContactModel
import es.littledavity.commons.ui.widgets.contacts.ContactsModelMapper
import es.littledavity.commons.ui.widgets.contacts.ContactsUiState
import es.littledavity.core.providers.StringProvider
import es.littledavity.testUtils.DOMAIN_CONTACTS
import es.littledavity.testUtils.FakeStringProvider
import io.mockk.coEvery
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class LikedContactsUiStateFactoryTest {

    private lateinit var factory: LikedContactsUiStateFactoryImpl
    private lateinit var stringProvider: StringProvider
    private lateinit var likedContactModelMapper: ContactsModelMapper

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        stringProvider = FakeStringProvider()
        likedContactModelMapper = mockk()
        factory = LikedContactsUiStateFactoryImpl(stringProvider, likedContactModelMapper)
    }

    @Test
    fun createWithEmptyState_shouldReturnEmptyState() {
        val result = factory.createWithEmptyState()
        assertThat(result).isEqualTo(
            ContactsUiState.Empty(
                iconId = R.drawable.contact_outline,
                title = stringProvider.getString(R.string.liked_contacts_fragment_info_title)
            )
        )
    }

    @Test
    fun createWithLoadingState_shouldReturnLoadingState() {
        val result = factory.createWithLoadingState()
        assertThat(result).isEqualTo(ContactsUiState.Loading)
    }

    @Test
    fun createWithResultState_whenNotEmpty_shouldReturnResultState() {
        coEvery { likedContactModelMapper.mapToContactModel(any()) } returns ContactModel(
            1,
            "test",
            "name",
            "phone",
            "date"
        )
        val model = DOMAIN_CONTACTS.map(likedContactModelMapper::mapToContactModel)
        val result = factory.createWithResultState(DOMAIN_CONTACTS)
        assertThat(result).isEqualTo(ContactsUiState.Result(model))
    }

    @Test
    fun createWithResultState_whenEmpty_shouldReturnResultState() {
        val result = factory.createWithResultState(emptyList())
        assertThat(result).isEqualTo(
            ContactsUiState.Empty(
                iconId = R.drawable.contact_outline,
                title = stringProvider.getString(R.string.liked_contacts_fragment_info_title)
            )
        )
    }
}
