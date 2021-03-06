package pl.dzielins42.bloxyz.lce

import android.app.Activity
import android.os.Build
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
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

        fragment.requireView().findViewById<ViewGroup>(R.id.lceContainerView),
        fragment.requireView().findViewById(R.id.loadingView),
        fragment.requireView().findViewById(R.id.contentView),
        fragment.requireView().findViewById(R.id.errorView),
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
        val defaultErrorMapper: LceErrorMapper = object : LceErrorMapper {
            override fun getErrorMessage(error: Throwable?): String? {
                return error?.localizedMessage
            }
        }
    }
}