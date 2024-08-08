package sg.carousell.news.conventions.utils

import sg.carousell.news.conventions.utils.Constant.GLOBAL_TAG

fun printMessage(
    msg: String,
    showIntentional: Boolean = false,
) {
    println("$GLOBAL_TAG $msg")
    if (showIntentional) intentionalMessage()
}

fun intentionalMessage() {
    println("$GLOBAL_TAG if this is intentional then you may skip this message")
}
