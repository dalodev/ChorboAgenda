package es.littledavity.commons.ui.extensions

import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintSet

inline fun MotionLayout.updateConstraintSets(action: (Int, ConstraintSet) -> Unit) {
    for(constraintSetId in constraintSetIds) {
        updateState(
            constraintSetId,
            getConstraintSet(constraintSetId).also { action(constraintSetId, it) }
        )
    }
}


inline fun MotionLayout.addTransitionListener(
    crossinline onTransitionStarted: (startId: Int, endId: Int) -> Unit = { _, _ -> },
    crossinline onTransitionChange: (startId: Int, endId: Int, progress: Float) -> Unit = { _, _, _ -> },
    crossinline onTransitionCompleted: (currentId: Int) -> Unit = {},
    crossinline onTransitionTrigger: (triggerId: Int, positive: Boolean, progress: Float) -> Unit = { _, _, _ -> }
): MotionLayout.TransitionListener {
    return object : MotionLayout.TransitionListener {

        override fun onTransitionStarted(ml: MotionLayout, startId: Int, endId: Int) {
            onTransitionStarted(startId, endId)
        }

        override fun onTransitionChange(ml: MotionLayout, startId: Int, endId: Int, progress: Float) {
            onTransitionChange(startId, endId, progress)
        }

        override fun onTransitionCompleted(ml: MotionLayout, currentId: Int) {
            onTransitionCompleted(currentId)
        }

        override fun onTransitionTrigger(
            ml: MotionLayout,
            triggerId: Int,
            positive: Boolean,
            progress: Float
        ) {
            onTransitionTrigger(triggerId, positive, progress)
        }

    }
        .also(::addTransitionListener)
}