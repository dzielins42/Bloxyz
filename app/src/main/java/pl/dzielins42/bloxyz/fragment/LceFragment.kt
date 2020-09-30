package pl.dzielins42.bloxyz.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import pl.dzielins42.bloxyz.lce.HasLce
import pl.dzielins42.bloxyz.lce.LceErrorMapper
import pl.dzielins42.bloxyz.lce.LceViewMixin
import pl.dzielins42.bloxyz.lce.wrapInLce

open class LceFragment(
    @LayoutRes private val contentLayoutId: Int = 0
) : Fragment(), HasLce, LceErrorMapper {

    private lateinit var lceMixin: LceViewMixin

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (contentLayoutId != 0) {
            wrapInLce(inflater, container, contentLayoutId)
        } else null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lceMixin = LceViewMixin(this, this)
    }

    //region HasLce
    override fun showLoading(animate: Boolean) =
        lceMixin.showLoading(animate)

    override fun showContent(animate: Boolean) =
        lceMixin.showContent(animate)

    override fun showError(error: Throwable?, animate: Boolean) =
        lceMixin.showError(error, animate)
    //endregion

    //region LceErrorMapper
    override fun getErrorMessage(error: Throwable?): String? {
        return LceViewMixin.defaultErrorMapper.getErrorMessage(error)
    }
    //endregion
}