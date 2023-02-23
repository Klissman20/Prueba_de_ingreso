package com.example.pruebadeingreso.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Publish(
    @SerializedName("id")
    @Expose
    var id: Int,

    @SerializedName("userId")
    @Expose
    var userId: String,

    @SerializedName("title")
    @Expose
    var title: String,

    @SerializedName("body")
    @Expose
    var body: String
)
