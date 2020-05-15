/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.create.image

import android.net.Uri
import es.littledavity.commons.ui.base.BaseViewState

/**
 * Different states for [ImageFragment].
 *
 * @see BaseViewState
 */
sealed class ImageViewState : BaseViewState {

    /**
     * Show continue
     */
    data class Continue(val uri: Uri? = null) : ImageViewState()

    /**
     * Open Gallery
     */
    object OpenGallery : ImageViewState()

    // ============================================================================================
    //  Public helpers methods
    // ============================================================================================

    /**
     * Check if current view state is [Continue].
     *
     * @return True if is empty state, otherwise false.
     */
    fun isContinue() = this is Continue
}
