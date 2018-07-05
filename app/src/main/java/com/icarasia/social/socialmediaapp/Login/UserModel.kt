package com.icarasia.social.socialmediaapp.Login

class UserDetails(attr: String, valu: String) {
    var attribute : String = attr
    var value : String =valu
}

data class User(
        val id: Int = 0,
        val name: String,
        val username: String,
        val email: String,
        val address: Address,
        val phone: String,
        val website: String,
        val company: Company,
        var todosNumber: Int,
        var albumsNumber: Int
)

data class Address(
        val street: String,
        val suite: String,
        val city: String,
        val zipcode: String,
        val geo: Geo
){
    override fun toString() : String{
        return "$city\n" +
                "$suite\n" +
                "$street\n" +
                "$zipcode\n" +
                "${geo}"
    }
}

data class Geo(
        val lat: String,
        val lng: String
){
    override fun toString(): String {
        return "lat : $lat\n" +
                "lng : $lng"
    }
}

data class Company(
        val name: String,
        val catchPhrase: String,
        val bs: String
){
    override fun toString(): String {
        return "$name\n" +
                "$catchPhrase\n" +
                "$bs"
    }
}