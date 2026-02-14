package com.tech.core.common.extension

import android.app.Activity
import android.content.Context

fun Context.killApp() {
    (this as? Activity)?.finish()
}