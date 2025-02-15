package com.sonozaki.tracklist.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.widget.TextView
import androidx.appcompat.R
import androidx.appcompat.widget.SearchView

/**
 * SearchView with fixed query text listener. Query text listener of original Search view doesn't notify subscribers if user entered empty string.
 */
class EmptySubmitSearchView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    SearchView(context, attrs, defStyleAttr) {
    constructor(context: Context, attrs: AttributeSet?) : this(
        context,
        attrs,
        R.attr.searchViewStyle
    )

    constructor(context: Context) : this(context, null)

    @SuppressLint("RestrictedApi")
    var mSearchSrcTextView: SearchAutoComplete? = null
    private var listener: OnQueryTextListener? = null


    override fun setOnQueryTextListener(listener: OnQueryTextListener?) {
        super.setOnQueryTextListener(listener)

        this.listener = listener
        mSearchSrcTextView = this.findViewById(R.id.search_src_text)
        mSearchSrcTextView?.setOnEditorActionListener { textView: TextView?, i: Int, keyEvent: KeyEvent? ->
            listener?.onQueryTextSubmit(query.toString())
            true
        }
    }
}