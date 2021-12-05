package es.littledavity.features.add

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import es.littledavity.commons.ui.extensions.layoutInflater
import es.littledavity.features.add.databinding.ViewAddContactBinding
import es.littledavity.features.add.widgets.AddContactView
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.spyk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock


class AddContactViewTest {

    @MockK
    private lateinit var context: Context

    @MockK
    private lateinit var layoutInflater: LayoutInflater

    @MockK
    private lateinit var viewGroup: ViewGroup

    @MockK
    private lateinit var mainView: ConstraintLayout

    @MockK
    private lateinit var name: TextInputLayout

    @MockK
    private lateinit var phone: TextInputLayout

    @MockK
    private lateinit var addButton: FloatingActionButton

    @MockK
    private lateinit var viewBinding: ViewAddContactBinding

    private lateinit var view: AddContactView

   /* @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        mockkStatic(LayoutInflater::class)
        mockkStatic(ViewAddContactBinding::class)
        every { LayoutInflater.from(context) } returns layoutInflater
        every { LayoutInflater.from(context).inflate(any<Int>(), any()) } returns mockk()
        every { ViewAddContactBinding.inflate(any(), any()) } returns viewBinding

        view = spyk(AddContactView(context))

        every { view.context } returns context
        every { view.isVisible } returns false
        every { viewBinding.nameLayout } returns name
        every { viewBinding.phoneLayout } returns phone
        every { viewBinding.addDoneBtn } returns addButton
    }

    @Test
    fun addButtonClickedShouldInvokeDoneClicked() {
        every { view.uiState } returns AddContactUiState.New
        every { name.editText?.text.toString() } returns "test"
        every { phone.editText?.text.toString() } returns "test"

        val clickSlot = slot<View.OnClickListener>()
        verify(exactly = 1) {
            addButton.setOnClickListener(capture(clickSlot))
            view.onDoneClicked?.invoke()
        }
        clickSlot.captured.onClick(addButton)
        assertThat(name.editText?.text.toString()).isEqualTo("test")
    }*/
}