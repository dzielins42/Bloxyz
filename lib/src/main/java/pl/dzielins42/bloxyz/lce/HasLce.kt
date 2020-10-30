package pl.dzielins42.bloxyz.lce

interface HasLce {
    fun showLoading(animate: Boolean = true)
    fun showContent(animate: Boolean = true)
    fun showError(error: Throwable? = null, animate: Boolean = true)
}