package com.example.pruebadeingreso.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("id")
    @Expose
    var id: Int,
    @SerializedName("name")
    @Expose
    var name: String,
    @SerializedName("phone")
    @Expose
    var phone: String,
    @SerializedName("email")
    @Expose
    var email: String)
