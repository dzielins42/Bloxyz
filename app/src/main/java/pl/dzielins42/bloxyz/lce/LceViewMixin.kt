package pl.dzielins42.bloxyz.lce

import android.app.Activity
import android.os.Build
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import pl.dzielins42.bloxyz.BuildConfig
import pl.dzielins42.bloxyz.R

class LceViewMixin(
    private val lceContainerView: ViewGroup,
    private val loadingView: View,
    private val contentView: View,
    private val errorView: View,
    private val errorMapper: LceErrorMapper = defaultErrorMapper
) : HasLce {

    constructor(
        activity: Activity,
        errorMapper: LceErrorMapper = defaultErrorMapper
    ) : this(
        activity.findViewById<ViewGroup>(R.id.lceContainerView),
        activity.findViewById(R.id.loadingView),
        activity.findViewById(R.id.contentView),
        activity.findViewById(R.id.errorView),
        errorMapper
    ) {

    }

    constructor(
        fragment: Fragment,
        errorMapper: LceErrorMapper = defaultErrorMapper
    ) : this(
        fragment.view!!.findViewById<ViewGroup>(R.id.lceContainerView),
        fragment.view!!.findViewById(R.id.loadingView),
        fragment.view!!.findViewById(R.id.contentView),
        fragment.view!!.findViewById(R.id.errorView),
        errorMapper
    )

    constructor(
        view: View,
        errorMapper: LceErrorMapper = defaultErrorMapper
    ) : this(
        view.findViewById<ViewGroup>(R.id.lceContainerView),
        view.findViewById(R.id.loadingView),
        view.findViewById(R.id.contentView),
        view.findViewById(R.id.errorView),
        errorMapper
    )

    init {
        if (BuildConfig.DEBUG) {
            setupDebugControls()
        } else {
            hideDebugControls()
        }
    }

    private fun setupDebugControls() {
        lceContainerView.findViewById<View>(R.id.debugLceLoadingButton)?.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                showLoading()
            }
        }
        lceContainerView.findViewById<View>(R.id.debugLceContentButton)?.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                showContent()
            }
        }
        lceContainerView.findViewById<View>(R.id.debugLceErrorButton)?.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                showError(Exception("Generic exception"))
            }
        }
    }

    private fun hideDebugControls() {
        lceContainerView.findViewById<View>(R.id.debugLceLoadingButton)?.apply {
            visibility = View.GONE
        }
        lceContainerView.findViewById<View>(R.id.debugLceContentButton)?.apply {
            visibility = View.GONE
        }
        lceContainerView.findViewById<View>(R.id.debugLceErrorButton)?.apply {
            visibility = View.GONE
        }
    }

    override fun showLoading(animate: Boolean) {
        if (animate && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(lceContainerView)
        }
        // TODO Animation pre-KITKAT?
        loadingView.isVisible = true
        contentView.isVisible = false
        errorView.isVisible = false
    }

    override fun showContent(animate: Boolean) {
        if (animate && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(lceContainerView)
        }
        // TODO Animation pre-KITKAT?
        loadingView.isVisible = false
        contentView.isVisible = true
        errorView.isVisible = false
    }

    override fun showError(error: Throwable?, animate: Boolean) {
        val errorMsg = errorMapper.getErrorMessage(error)

        if (animate && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(lceContainerView)
        }
        // TODO Animation pre-KITKAT?
        errorView.findViewById<TextView>(android.R.id.text1)?.run {
            isVisible = !errorMsg.isNullOrBlank()
            text = errorMsg
        }
        loadingView.isVisible = false
        contentView.isVisible = false
        errorView.isVisible = true
    }

    companion object {
        private val defaultErrorMapper: LceErrorMapper = object : LceErrorMapper {
            override fun getErrorMessage(error: Throwable?): String? {
                return error?.localizedMessage
            }
        }
    }
}