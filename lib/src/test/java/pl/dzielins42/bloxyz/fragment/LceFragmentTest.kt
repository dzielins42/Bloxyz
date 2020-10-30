package pl.dzielins42.bloxyz.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import pl.dzielins42.bloxyz.R

@RunWith(RobolectricTestRunner::class)
class LceFragmentTest {
    @Test
    fun `When fragment starts, it is in loading state`() {
        // Arrange
        val scenario = launchFragmentInContainer<LceFragment>(factory = LceFragmentFactory())

        // Act

        // Assert
        assertLoadingState()
    }

    //region State change tests
    //region Starting from Loading
    @Test
    fun `When fragment is in loading state and showLoading is called, fragment stays in loading state`() {
        // Arrange
        val scenario = launchFragmentInContainer<LceFragment>(factory = LceFragmentFactory())
        scenario.withFragment { showLoading() }

        // Act
        scenario.withFragment {
            showLoading()
        }

        // Assert
        assertLoadingState()
    }

    @Test
    fun `When fragment is in loading state and showContent is called, fragment changes to content state`() {
        // Arrange
        val scenario = launchFragmentInContainer<LceFragment>(factory = LceFragmentFactory())
        scenario.withFragment { showLoading() }

        // Act
        scenario.withFragment {
            showContent()
        }

        // Assert
        assertContentState()
    }

    @Test
    fun `When fragment is in loading state and showError is called, fragment changes to error state`() {
        // Arrange
        val scenario = launchFragmentInContainer<LceFragment>(factory = LceFragmentFactory())
        scenario.withFragment { showLoading() }

        // Act
        scenario.withFragment {
            showError()
        }

        // Assert
        assertErrorState()
    }
    //endregion

    //region Starting from Content
    @Test
    fun `When fragment is in content state and showLoading is called, fragment changes to  loading state`() {
        // Arrange
        val scenario = launchFragmentInContainer<LceFragment>(factory = LceFragmentFactory())
        scenario.withFragment { showContent() }

        // Act
        scenario.withFragment {
            showLoading()
        }

        // Assert
        assertLoadingState()
    }

    @Test
    fun `When fragment is in content state and showContent is called, fragment stays in content state`() {
        // Arrange
        val scenario = launchFragmentInContainer<LceFragment>(factory = LceFragmentFactory())
        scenario.withFragment { showContent() }

        // Act
        scenario.withFragment {
            showContent()
        }

        // Assert
        assertContentState()
    }

    @Test
    fun `When fragment is in content state and showError is called, fragment changes to error state`() {
        // Arrange
        val scenario = launchFragmentInContainer<LceFragment>(factory = LceFragmentFactory())
        scenario.withFragment { showContent() }

        // Act
        scenario.withFragment {
            showError()
        }

        // Assert
        assertErrorState()
    }
    //endregion

    //region Starting from Error
    @Test
    fun `When fragment is in error state and showLoading is called, fragment changes to  loading state`() {
        // Arrange
        val scenario = launchFragmentInContainer<LceFragment>(factory = LceFragmentFactory())
        scenario.withFragment { showError() }

        // Act
        scenario.withFragment {
            showLoading()
        }

        // Assert
        assertLoadingState()
    }

    @Test
    fun `When fragment is in error state and showContent is called, fragment changes to content state`() {
        // Arrange
        val scenario = launchFragmentInContainer<LceFragment>(factory = LceFragmentFactory())
        scenario.withFragment { showError() }

        // Act
        scenario.withFragment {
            showContent()
        }

        // Assert
        assertContentState()
    }

    @Test
    fun `When fragment is in error state and showError is called, fragment stays in error state`() {
        // Arrange
        val scenario = launchFragmentInContainer<LceFragment>(factory = LceFragmentFactory())
        scenario.withFragment { showError() }

        // Act
        scenario.withFragment {
            showError()
        }

        // Assert
        assertErrorState()
    }
    //endregion
    //endregion

    //region State assert methods
    private fun assertLoadingState() {
        onView(withId(R.id.loadingView))
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(withId(R.id.contentView))
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        onView(withId(R.id.errorView))
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
    }

    private fun assertContentState() {
        onView(withId(R.id.loadingView))
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        onView(withId(R.id.contentView))
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(withId(R.id.errorView))
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
    }

    private fun assertErrorState() {
        onView(withId(R.id.loadingView))
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        onView(withId(R.id.contentView))
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        onView(withId(R.id.errorView))
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }
    //endregion

    class LceFragmentFactory : FragmentFactory() {
        override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
            return LceFragment(R.layout.view_lce_error)
        }
    }
}