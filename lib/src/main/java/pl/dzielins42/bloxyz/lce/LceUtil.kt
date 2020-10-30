package pl.dzielins42.bloxyz.lce

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import pl.dzielins42.bloxyz.R

fun wrapInLce(
    inflater: LayoutInflater,
    container: ViewGroup?,
    @LayoutRes contentLayoutId: Int
): View {
    val lceView = inflater.inflate(R.layout.view_lce, container, false)
    lceView.findViewById<ViewGroup>(R.id.contentView)?.let {
        inflater.inflate(contentLayoutId, it)
    } ?: throw IllegalStateException("ViewGroup with R.id.contentView not found")
    return lceView
}

fun Activity.setLceContentView(@LayoutRes contentLayoutId: Int) {
    setContentView(R.layout.view_lce)
    val inflater = LayoutInflater.from(this)
    findViewById<ViewGroup>(R.id.contentView)?.let {
        inflater.inflate(contentLayoutId, it)
    } ?: throw IllegalStateException("ViewGroup with R.id.contentView not found")
}