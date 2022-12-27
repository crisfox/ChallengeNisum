package com.nisum.challenge.common

import androidx.core.net.toUri

fun String.getLasPath(): String? = this.toUri().lastPathSegment
