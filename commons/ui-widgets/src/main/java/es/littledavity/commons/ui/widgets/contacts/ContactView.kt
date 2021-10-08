/*
 * Copyright 2021 dev.id
 */
package es.littledavity.commons.ui.widgets.contacts

import android.content.Context
import android.telephony.PhoneNumberUtils
import android.util.AttributeSet
import androidx.core.view.isVisible
import com.google.android.material.card.MaterialCardView
import es.littledavity.commons.ui.bindings.onLongClick
import es.littledavity.commons.ui.extensions.*
import es.littledavity.commons.ui.widgets.R
import es.littledavity.commons.ui.widgets.databinding.ViewContactBinding
import java.util.*

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

    var phone: CharSequence?
        set(value) {
            binding.phone.text = PhoneNumberUtils.formatNumber(
                value.toString(),
                Locale.getDefault().country
            )
            binding.phone.isVisible = value?.isNotEmpty().orFalse()

        }
        get() = binding.phone.text

    var onContactClicked: (() -> Unit)? = null
    var onLongContactClicked: (() -> Unit)? = null

    init {
        initCard()
        initImageView()
    }

    private fun initCard() {
        setCardBackgroundColor(getColor(R.color.contact_card_background_color))
        cardElevation = getDimension(R.dimen.contact_card_elevation)
        onClick { onContactClicked?.invoke() }
        onLongClick { onLongContactClicked?.invoke() }
    }

    private fun initImageView() {
        binding.image.isTitleVisible = false
    }
}
