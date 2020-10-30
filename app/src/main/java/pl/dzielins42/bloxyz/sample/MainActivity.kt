package pl.dzielins42.bloxyz.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pl.dzielins42.bloxyz.fragment.LceFragment
import pl.dzielins42.bloxyz.lce.LceViewMixin

class MainActivity : AppCompatActivity() {

    private lateinit var lceMixin: LceViewMixin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LceFragment(R.layout.fragment_test))
                .commitNow()
        }
    }
}