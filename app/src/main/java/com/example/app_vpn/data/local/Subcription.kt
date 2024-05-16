package com.example.app_vpn.data.local

import java.time.Duration

data class Subcription(
    val number: Int,
    val duration: String,
    val price : String,
    var selected: Boolean = false
)
