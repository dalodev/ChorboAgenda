package es.littledavity.commons.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<B : ViewDataBinding>(
    @LayoutRes
    private val layoutId: Int
) : AppCompatActivity() {

    var viewBinding: B? = null
    private val binding
        get() = viewBinding!!

    var activityToolbar: Toolbar? = null

    /**
     * Called when the activity is starting. This is where most initialization should go: calling
     * [AppCompatActivity.setContentView] to inflate the activity's UI, using
     * [AppCompatActivity.findViewById] to programmatically interact with widgets in the UI.
     *
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     * @see AppCompatActivity.onCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, layoutId)
    }

    protected fun setupToolbar(toolbar: Toolbar?) {
        activityToolbar = toolbar
        setSupportActionBar(activityToolbar)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

}