package com.stripe.aod.sampleapp.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import com.stripe.aod.sampleapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun navOptions(): NavOptions {
    return NavOptions.Builder()
        .setEnterAnim(R.anim.slide_right_in)
        .setExitAnim(R.anim.slide_left_out)
        .setPopEnterAnim(R.anim.slide_left_in)
        .setPopExitAnim(R.anim.slide_right_out)
        .build()
}

inline fun Fragment.launchAndRepeatWithViewLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.() -> Unit
): Job {
    return viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(minActiveState) {
            block()
        }
    }
}
