/*
 * Copyright 2021 dev.id
 */
package es.littledavity.commons.ui.bindings

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T
): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater(layoutInflater)
    }
}


fun <T : ViewBinding> Fragment.viewBinding(
    viewBindingFactory: (View) -> T
): FragmentViewBindingDelegate<T> {
    return FragmentViewBindingDelegate(this, viewBindingFactory)
}


class FragmentViewBindingDelegate<T : ViewBinding>(
    private val fragment: Fragment,
    private val viewBindingFactory: (View) -> T
) : ReadOnlyProperty<Fragment, T> {


    private var binding: T? = null


    init {
        // Keeping a view reference up until Fragment's onDestroy is called
        // to prevent its recreation when the back stack changes
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {

            override fun onDestroy(owner: LifecycleOwner) {
                binding = null
            }

        })
    }


    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        if(binding != null) {
            return checkNotNull(binding)
        }

        val lifecycle = fragment.viewLifecycleOwner.lifecycle

        if(!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            throw IllegalStateException("Could not retrieve a view binding when the fragment is not initialized.")
        }

        return viewBindingFactory(thisRef.requireView())
            .also { binding = it }
    }


}
