package com.icarasia.social.socialmediaapp.Login

class UserDetails(attr: String, valu: String) {
    var attribute : String = attr
    var value : String =valu
}

data class User(
        val id: Int = 0,
        val name: String = "",
        val username: String= "",
        val email: String= "",
        val address: Address = Address(),
        val phone: String= "",
        val website: String= "",
        val company: Company = Company(),
        var todosNumber: Int = 0,
        var albumsNumber: Int = 0
)

data class Address(
        val street: String= "",
        val suite: String= "",
        val city: String= "",
        val zipcode: String= "",
        val geo: Geo = Geo()
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
        val lat: String= "",
        val lng: String= ""
){
    override fun toString(): String {
        return "lat : $lat\n" +
                "lng : $lng"
    }
}

data class Company(
        val name: String= "",
        val catchPhrase: String= "",
        val bs: String= ""
){
    override fun toString(): String {
        return "$name\n" +
                "$catchPhrase\n" +
                "$bs"
    }
}