package com.example.pruebadeingreso.model

class User(
    var name: String? = null,
    var phone: String? = null,
    var email: String? = null) {


    fun getName(): String { return this.name!! }
    fun setName(name: String){this.name = name}

    fun getPhone(): String {return this.phone!! }
    fun setPhone(phone: String){this.phone = phone}

    fun getEmail(): String {return this.email!! }
    fun setEmail(email: String){this.email = email}

}
