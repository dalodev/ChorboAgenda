/*
 * Copyright 2021 dalodev
 */
package es.littledavity.features.add

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import es.littledavity.features.add.databinding.ViewAddContactBinding
import es.littledavity.features.add.widgets.AddContactView
import io.mockk.impl.annotations.MockK

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
