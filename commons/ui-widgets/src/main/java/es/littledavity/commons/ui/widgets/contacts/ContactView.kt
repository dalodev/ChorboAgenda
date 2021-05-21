package es.littledavity.commons.ui.widgets.contacts

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.card.MaterialCardView
import com.paulrybitskyi.commons.ktx.getColor
import com.paulrybitskyi.commons.ktx.getDimension
import com.paulrybitskyi.commons.ktx.layoutInflater
import com.paulrybitskyi.commons.ktx.onClick
import es.littledavity.commons.ui.widgets.R
import es.littledavity.commons.ui.widgets.databinding.ViewContactBinding

class ContactView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    private val binding = ViewContactBinding.inflate(context.layoutInflater, this)

    var image: String? = null
        set(value) {
            field = value
            binding.image.imageUrl = value
        }

    var name: CharSequence
        set(value) {
            binding.name.text = value
        }
        get() = binding.name.text

    var phone: CharSequence
        set(value) {
            binding.phone.text = value
        }
        get() = binding.phone.text

    var onContactClicked: (() -> Unit)? = null

    init {
        initCard()
        initImageView()
    }

    private fun initCard() {
        setCardBackgroundColor(getColor(R.color.contact_card_background_color))
        cardElevation = getDimension(R.dimen.contact_card_elevation)
        onClick { onContactClicked?.invoke() }
    }

    private fun initImageView() {
        binding.image.isTitleVisible = false
    }
}