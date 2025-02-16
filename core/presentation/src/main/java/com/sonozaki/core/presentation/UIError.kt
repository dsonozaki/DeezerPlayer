package com.sonozaki.core.presentation

import android.content.Context
import androidx.annotation.StringRes


data class UIError(@StringRes val textResId: Int, val customText: String? = null) {
    fun getString(context: Context) = if (customText == null){
        context.getString(textResId)
    } else {
        context.getString(textResId, customText)
    }
}
