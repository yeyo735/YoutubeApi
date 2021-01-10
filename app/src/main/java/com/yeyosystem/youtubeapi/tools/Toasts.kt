package com.yeyosystem.youtubeapi.tools

import android.content.Context
import android.widget.Toast

fun Context.showShortToast(message: CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(message: CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}