package br.thiago.core

import com.google.gson.annotations.SerializedName

class BsGetDeviceModel(
    @SerializedName("os") val os: String,
    @SerializedName("os_version") val os_version: String,
    @SerializedName("device") val device: String
)
