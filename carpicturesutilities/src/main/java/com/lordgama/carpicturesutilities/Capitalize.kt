package com.lordgama.carpicturesutilities

import java.util.regex.Pattern

fun String.capitalize(): String {
    val capBuffer = StringBuffer()
    val capMatcher = Pattern.compile("([a-z-éá])([a-z-éá]*)", Pattern.CASE_INSENSITIVE).matcher(this)
    while (capMatcher.find()) {
        capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase())
    }
    return capMatcher.appendTail(capBuffer).toString()
}