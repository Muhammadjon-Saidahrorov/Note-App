package uz.gita.noteappmn.utils

fun <T> T.myApply(block: T.() -> Unit) {
    block(this)
}