package es.littledavity.features.add

import es.littledavity.commons.ui.widgets.contacts.ContactModel
import es.littledavity.commons.ui.widgets.contacts.ContactsModelMapper
import es.littledavity.testUtils.DOMAIN_CONTACT
import io.mockk.coEvery
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.MockitoAnnotations

class AddContactUiStateFactoryImplTest {

    private lateinit var factory: AddContactUiStateFactoryImpl
    private lateinit var contactModelMapper: ContactsModelMapper

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        contactModelMapper = mockk()
        factory = AddContactUiStateFactoryImpl(contactModelMapper)
    }

    @Test
    fun createWithNewState_shouldReturnNew() {
        val result = factory.createWithNewState()
        assertThat(result).isEqualTo(AddContactUiState.New)
    }

    @Test
    fun createWithLoadingState_shouldReturnNew() {
        val result = factory.createWithLoadingState()
        assertThat(result).isEqualTo(AddContactUiState.Loading)
    }

    @Test
    fun createWithErrorState_whenNameError_shouldReturnErrorState() {
        val result = factory.createWithErrorState(nameError = true, phoneError = false)
        assertThat(result).isEqualTo(AddContactUiState.Error(nameError = true, phoneError = false))
    }

    @Test
    fun createWithErrorState_whenPhoneError_shouldReturnErrorState() {
        val result = factory.createWithErrorState(nameError = false, phoneError = true)
        assertThat(result).isEqualTo(AddContactUiState.Error(nameError = false, phoneError = true))
    }

    @Test
    fun createWithPermissionError_souldReturnPermissionError() {
        val unit = {}
        val result = factory.createWithPermissionError(unit)
        assertThat(result).isEqualTo(AddContactUiState.ErrorPermission(unit))
    }

    @Test
    fun createWithResultState_shouldReturnResultState() {
        coEvery { contactModelMapper.mapToContactModel(any()) } returns ContactModel(
            id = 1,
            coverImageUrl = "test",
            name = "name",
            phone = "phone",
            creationDate = "date",
        )
        val model = contactModelMapper.mapToContactModel(DOMAIN_CONTACT)
        val result = factory.createWithResultState(DOMAIN_CONTACT)
        assertThat(result).isEqualTo(AddContactUiState.Result(model))
    }
}
