package com.tech.core.common.extension

import android.app.Activity
import android.content.Context
// Safely finishes the Activity if the Context is an Activity
fun Context.killApp() {
    (this as? Activity)?.finish()
}

