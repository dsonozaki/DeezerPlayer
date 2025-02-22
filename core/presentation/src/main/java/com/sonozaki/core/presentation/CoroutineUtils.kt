package com.sonozaki.core.presentation

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

fun LifecycleOwner.launchLifecycleAwareCoroutine(coroutine: suspend () -> Unit) {
    with(this) {
        this.lifecycleScope.launch {
            this@with.repeatOnLifecycle(Lifecycle.State.STARTED) {
                coroutine()
            }
        }
    }
}