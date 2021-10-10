/*
 * Copyright 2021 dev.id
 */
package es.littledavity.features.info.widgets.header

import android.content.Context
import android.telephony.PhoneNumberUtils
import android.text.TextUtils
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import es.littledavity.commons.ui.extensions.DimensionSnapshotType
import es.littledavity.commons.ui.extensions.addTransitionListener
import es.littledavity.commons.ui.extensions.doOnApplyWindowInsets
import es.littledavity.commons.ui.extensions.getDimension
import es.littledavity.commons.ui.extensions.getDimensionPixelSize
import es.littledavity.commons.ui.extensions.isChecked
import es.littledavity.commons.ui.extensions.makeGone
import es.littledavity.commons.ui.extensions.observeChanges
import es.littledavity.commons.ui.extensions.onClick
import es.littledavity.commons.ui.extensions.postAction
import es.littledavity.commons.ui.extensions.updateConstraintSets
import es.littledavity.core.providers.StringProvider
import es.littledavity.domain.contacts.entities.Contact
import es.littledavity.features.info.R
import es.littledavity.features.info.databinding.ViewContactInfoBinding
import es.littledavity.features.info.widgets.model.ContactInfoHeaderModel
import es.littledavity.features.info.widgets.mapToContactGalleryModels

private const val KEY_PHONE_QUERY = "phone"
private const val KEY_INSTAGRAM_QUERY = "instagram"
private const val KEY_RATING_QUERY = "rating"
private const val KEY_COUNTRY_QUERY = "country"
private const val KEY_AGE_QUERY = "age"


internal class ContactHeaderController(
    context: Context,
    private val currentContact: Contact?,
    private val binding: ViewContactInfoBinding,
    private val stringProvider: StringProvider
) {

    private val pageIndicatorTopMargin =
        context.getDimensionPixelSize(R.dimen.contact_info_header_page_indicator_margin)

    private var areWindowInsetsApplied = false

    private val hasDefaultBackgroundImage: Boolean
        get() = (
                (backgroundImageModels.isEmpty()) &&
                        (backgroundImageModels.single() is ContactHeaderImageModel.DefaultImage)
                )

    private val isPageIndicatorEnabled: Boolean
        get() = binding.galleryView.galleryModels.isNotEmpty()

    private var isLiked by observeChanges(false) { _, newValue ->
        binding.likeBtn.isChecked = newValue
    }

    private var isPhoneVisible: Boolean
        set(value) {
            binding.phoneTv.isVisible = value
        }
        get() = binding.phoneTv.isVisible

    private var isInstagramVisible: Boolean
        set(value) {
            binding.instagramTv.isVisible = value
        }
        get() = binding.instagramTv.isVisible

    private var coverImageUrl: String? = null
        set(value) {
            field = value
            binding.coverView.imageUrl = value
        }

    var name by observeChanges("") { oldName, newName ->
        onNameChanged(oldName, newName)
    }

    var phone: CharSequence?
        set(value) {
            with(binding.phoneTv) {
                setText(PhoneNumberUtils.formatNumber(value.toString(), "ES"))
                setOnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus) setText(
                        PhoneNumberUtils.formatNumber(
                            binding.phoneTv.text.toString(),
                            "ES"
                        )
                    )
                }
            }
            isPhoneVisible = value != null
        }
        get() = binding.phoneTv.text

    private var creationDate: CharSequence
        set(value) {
            binding.creationDateTv.text = value
        }
        get() = binding.creationDateTv.text

    var instagram: CharSequence?
        set(value) {
            binding.instagramTv.setText(value)
            isInstagramVisible = value != null

        }
        get() = binding.instagramTv.text

    var rating: CharSequence = ""
        set(value) {
            field = value
            binding.ratingIv.titleText = value
        }
        get() = binding.ratingIv.titleText

    var age: CharSequence = ""
        set(value) {
            field = value
            binding.ageTv.titleText = value
        }
        get() = binding.ageTv.titleText

    var country: CharSequence = ""
        set(value) {
            field = value
            binding.countryNameIv.titleText = value
        }
        get() = binding.countryNameIv.titleText

    private var backgroundImageModels by observeChanges<List<ContactHeaderImageModel>>(emptyList()) { _, newItems ->
        disableScrimConstraintIfNeeded()
        binding.galleryView.galleryModels = newItems.mapToContactGalleryModels()
        binding.pageIndicatorTv.isVisible = newItems.isNotEmpty()
    }

    var onGalleryClicked: ((Int) -> Unit)? = null
    var onBackButtonClicked: (() -> Unit)? = null
    var onCoverClicked: (() -> Unit)? = null
    var onChangeImageClicked: (() -> Unit)? = null
    var onLikeButtonClicked: (() -> Unit)? = null
    var onAddGalleryClicked: (() -> Unit)? = null

    init {
        initMotionLayout()
        initGalleryView()
        initBackButton()
        initCoverView()
        initLikeButton()
        initAddGalleryButton()
        initChangeImageButton()
    }

    fun bindModel(model: ContactInfoHeaderModel) {
        coverImageUrl = model.imageUrl

        if (isLiked != model.isLiked) {
            isLiked = model.isLiked
        } else {
            // See onAttachedToWindow method's comment. This crutch is exactly like that,
            // with the only difference is that icon resets its state when pressing home
            // button and then coming back.
            if (isLiked) {
                isLiked = false
                isLiked = true
            }
        }

        if (backgroundImageModels != model.backgroundImageModels) backgroundImageModels =
            model.backgroundImageModels
        if (name != model.name) name = model.name
        if (phone != model.phone) phone = model.phone
        if (creationDate != model.creationDate) creationDate = model.creationDate
        if (instagram != model.instagram) instagram = model.instagram
        if (rating != model.rating) rating = model.rating.orEmpty()
        if (country != model.country) country = model.country
        if (age != model.age) age = model.age
    }

    fun onAttachedToWindow() {
        // This is a crutch solution to fix a very strange bug, where a user likes a contact,
        // goes to a another screen (e.g., a related contact) and comes back, then the like
        // button resets its icon from a filled heart to an empty heart. To fix it, when
        // the user comes back and this view gets reattached to the window, we are asking
        // the button to reset its state and then go to the liked state again.
        if (isLiked) {
            binding.likeBtn.postAction {
                isLiked = false
                isLiked = true
            }
        }
    }

    private fun initMotionLayout() {
        // Fixes a weird behavior where interacting with one UI element actually
        // interacts with another one (e.g., swiping and clicking on the "Details"
        // card causes items of the "Screenshots" card to scroll)
        // https://stackoverflow.com/questions/59504422/motionlayout-problems-with-children-intercepting-touch-events
        binding.mainView.isInteractionEnabled = false

        initMotionLayoutListener()
        initMotionLayoutInsets()
    }

    private fun initMotionLayoutListener() {
        binding.mainView.addTransitionListener(
            onTransitionTrigger = { triggerId, positive, _ ->
                onTransitionTrigger(
                    triggerId,
                    positive
                )
            }
        )
    }

    private fun onTransitionTrigger(triggerId: Int, positive: Boolean) {
        when (triggerId) {
            R.id.configureGallery -> {
                binding.galleryView.isScrollingEnabled = !positive
                binding.galleryView.isGalleryClickEnabled = !positive
            }

            R.id.trimFirstTitle -> {
                binding.nameTv.ellipsize = (if (positive) TextUtils.TruncateAt.END else null)
            }
        }
    }

    // Deprecation suppression should be removed after a new version
    // of the ktx lib is released and used here to abstract all the
    // complexity of setting insets
    @Suppress("DEPRECATION")
    private fun initMotionLayoutInsets() = with(binding.mainView) {
        // Traditional inset applying does not work with views that
        // are direct children of MotionLayout. Have to manually update
        // the constraint sets within it.
        doOnApplyWindowInsets(DimensionSnapshotType.MARGINS) { _, insets, _ ->
            // This is solely an optimization technique. updateConstraintSets method
            // is not very cheap to invoke, since it updates all constraint sets.
            // Apart from that, window insets listener may be invoked multiple times
            // for some reason. Combining this all creates a sluggish user experience
            // and cripples any animations that may be running. To mitigate this,
            // we apply insets only one time and exit in all other cases.
            if (areWindowInsetsApplied) {
                return@doOnApplyWindowInsets
            } else {
                areWindowInsetsApplied = true
            }

            updateConstraintSets { id, set ->
                set.setMargin(R.id.backBtnIv, ConstraintSet.TOP, insets.systemWindowInsetTop)
                set.setMargin(
                    R.id.pageIndicatorTv,
                    ConstraintSet.TOP,
                    (pageIndicatorTopMargin + insets.systemWindowInsetTop)
                )

                if (id == R.id.collapsed) {
                    val toolbarHeight = getDimensionPixelSize(R.dimen.toolbar_height)
                    val statusBarHeight = insets.systemWindowInsetTop
                    val totalHeight = (toolbarHeight + statusBarHeight)

                    set.setMargin(R.id.nameTv, ConstraintSet.TOP, statusBarHeight)
                    set.constrainHeight(R.id.galleryView, totalHeight)
                }
            }
        }
    }

    private fun onNameChanged(oldTitle: String, newTitle: String) {
        if ((oldTitle == newTitle) || newTitle.isBlank()) return

        val firstNameTv = binding.nameTv
        firstNameTv.setText(newTitle)
    }

    private fun initGalleryView() = with(binding.galleryView) {
        onGalleryChanged = ::updateGalleryPageIndicator
        onGalleryClicked = { this@ContactHeaderController.onGalleryClicked?.invoke(it) }
    }

    private fun updateGalleryPageIndicator(newPosition: Int) {
        if (!isPageIndicatorEnabled) return

        val oneIndexedPosition = (newPosition + 1)
        val totalCount = binding.galleryView.galleryModels.size
        val text = stringProvider.getString(
            R.string.contact_info_header_page_indicator_template,
            oneIndexedPosition,
            totalCount
        )

        // Simple text setting does not work here for some reason
        binding.pageIndicatorTv.postAction {
            binding.pageIndicatorTv.text = text
        }
    }

    private fun initBackButton() = with(binding.backBtnIv) {
        onClick { onBackButtonClicked?.invoke() }
    }

    private fun initCoverView() = with(binding.coverView) {
        cardElevation = getDimension(R.dimen.contact_info_header_backdrop_elevation)
        isTitleVisible = false
        onClick { onCoverClicked?.invoke() }
    }

    private fun initLikeButton() {
        binding.likeBtn.onClick { onLikeButtonClicked?.invoke() }
    }

    private fun initAddGalleryButton() {
        binding.addGalleryImagesBtn.onClick { onAddGalleryClicked?.invoke() }
    }

    private fun initChangeImageButton() {
        binding.changeImageBtn.onClick { onChangeImageClicked?.invoke() }
    }

    private fun disableScrimConstraintIfNeeded() {
        if (!hasDefaultBackgroundImage) return

        binding.mainView.updateConstraintSets { _, constraintSet ->
            constraintSet.clear(R.id.galleryScrimView)
        }

        binding.galleryScrimView.makeGone()
    }
}
