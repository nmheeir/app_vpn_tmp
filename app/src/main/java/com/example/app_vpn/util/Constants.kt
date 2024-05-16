package com.example.app_vpn.util

import com.example.app_vpn.data.local.Benefit
import com.example.app_vpn.data.local.Subcription

const val BASE_URL = "http://192.168.1.14:8080/"

val listSubcription = listOf(
    Subcription(1, "Month", "23000"),
    Subcription(6, "Month", "109000"),
    Subcription(1, "Year", "209000"),
    Subcription(2, "Year", "359000")
)

val listBenefit = listOf(
    Benefit("Multi-Device", "Use on Multiple Devices."),
    Benefit("Faster", "Unlimited bandwidth."),
    Benefit("All Server", "All servers in 100+ countries.")
)
