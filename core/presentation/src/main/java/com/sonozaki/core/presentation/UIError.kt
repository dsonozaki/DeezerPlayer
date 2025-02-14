package com.sonozaki.core.presentation

import androidx.annotation.StringRes


data class UIError(@StringRes val textResId: Int, val customText: String? = null)
