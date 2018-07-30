package com.lordgama.carpicturesutilities

/**
 * Created by Daniel on 27/04/2018.
 * @see { @link https://stackoverflow.com/questions/41463508/get-enum-constant-using-its-value }
 */
enum class Status constructor(val status: String){
    PENDING_CAPTURE("PP"),
    PENDING_TEST("PT"),
    PENDING_STATUS("PS"),
    COMPLETE_STATUS("CP");

    companion object {
        fun from(s: String): Status? = values().find { it.status == s }
    }
}