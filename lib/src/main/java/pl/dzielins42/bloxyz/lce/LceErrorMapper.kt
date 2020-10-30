package pl.dzielins42.bloxyz.lce

interface LceErrorMapper {
    fun getErrorMessage(error: Throwable?): String?
}