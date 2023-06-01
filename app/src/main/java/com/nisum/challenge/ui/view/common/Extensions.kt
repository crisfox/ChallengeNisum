package com.nisum.challenge.ui.view.common

import androidx.core.net.toUri

fun String.getLastPath(): String? = this.toUri().lastPathSegment
