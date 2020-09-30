package pl.dzielins42.bloxyz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pl.dzielins42.bloxyz.lce.HasLce
import pl.dzielins42.bloxyz.lce.LceViewMixin
import pl.dzielins42.bloxyz.lce.setLceContentView

class MainActivity : AppCompatActivity(), HasLce {

    private lateinit var lceMixin: LceViewMixin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLceContentView(R.layout.activity_main)
        lceMixin = LceViewMixin(this)
    }

    //region HasLce

    override fun showLoading(animate: Boolean) =
        lceMixin.showLoading(animate)

    override fun showContent(animate: Boolean) =
        lceMixin.showContent(animate)

    override fun showError(error: Throwable?, animate: Boolean) =
        lceMixin.showError(error, animate)

    //endregion
}