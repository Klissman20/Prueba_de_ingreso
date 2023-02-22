package com.example.pruebadeingreso.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class User(
    @SerializedName("id")
    @Expose
    @PrimaryKey
    var id: Int,
    @SerializedName("name")
    @Expose
    @ColumnInfo(name = "name")
    var name: String,
    @SerializedName("phone")
    @Expose
    @ColumnInfo (name = "phone")
    var phone: String,
    @SerializedName("email")
    @Expose
    @ColumnInfo (name = "email")
    var email: String) {

    /*fun getId(): Int { return this.id!! }

    fun getName(): String { return this.name!! }
    //fun setName(name: String){this.name = name}

    fun getPhone(): String {return this.phone!! }
    //fun setPhone(phone: String){this.phone = phone}

    fun getEmail(): String {return this.email!! }
    //fun setEmail(email: String){this.email = email}*/

}
