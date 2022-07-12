package com.geancarloleiva.a7_countriesmvvm

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("name")
    val name: String?,
    @SerializedName("capital")
    val capital: String?,
    @SerializedName("flagPNG")
    val flag: String?)