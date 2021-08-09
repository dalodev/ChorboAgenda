package es.littledavity.features.info.widgets.details

import android.content.Context
import android.util.AttributeSet
import androidx.core.view.isVisible
import androidx.core.view.marginTop
import com.google.android.material.card.MaterialCardView
import dagger.hilt.android.AndroidEntryPoint
import es.littledavity.commons.ui.extensions.getColor
import es.littledavity.commons.ui.extensions.getDimension
import es.littledavity.commons.ui.extensions.getDimensionPixelSize
import es.littledavity.commons.ui.extensions.layoutInflater
import es.littledavity.commons.ui.extensions.onClick
import es.littledavity.commons.ui.extensions.onTextChanged
import es.littledavity.commons.ui.extensions.setMargins
import es.littledavity.commons.ui.extensions.setVerticalMargin
import es.littledavity.commons.ui.extensions.topMargin
import es.littledavity.features.info.R
import es.littledavity.features.info.databinding.ViewContactInfoItemBinding

@AndroidEntryPoint
internal class ContactInfoDetailView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    private val binding = ViewContactInfoItemBinding.inflate(context.layoutInflater, this)

    var title: CharSequence
        set(value) {
            binding.titleTv.setText(value)
        }
        get() = binding.titleTv.text.toString()

    var description: CharSequence
        set(value) {
            binding.descriptionTv.setText(value)
        }
        get() = binding.descriptionTv.text.toString()

    var onInfoClicked: (() -> Unit)? = null

    var onTitleTextChange: ((String) -> Unit)? = null
    var onDescriptionTextChanged: ((String) -> Unit)? = null

    init {
        initCard()
    }

    private fun initCard() {
        setCardBackgroundColor(getColor(R.color.contact_info_item_card_background_color))
        cardElevation = getDimension(R.dimen.contact_info_item_card_elevation)
        radius = getDimension(R.dimen.contact_info_item_corner_radius)
        onClick {
            onInfoClicked?.invoke()
            binding.descriptionTv.requestFocus()
        }
        binding.titleTv.onTextChanged {
            onTitleTextChange?.invoke(it)
        }
        binding.descriptionTv.onTextChanged {
            onDescriptionTextChanged?.invoke(it)
        }
    }
}