package net.laggedhero.myreceipts.persistence.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import net.laggedhero.myreceipts.core.data.model.Address
import net.laggedhero.myreceipts.core.data.model.Company
import net.laggedhero.myreceipts.core.data.model.Geo
import net.laggedhero.myreceipts.core.data.model.User

@Entity
data class UserEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val lat: String,
    val lng: String,
    val phone: String,
    val website: String,
    @ColumnInfo(name = "company_name") val companyName: String,
    @ColumnInfo(name = "catch_phrase") val catchPhrase: String,
    val bs: String
) {
    fun toUser() = User(
        id,
        name,
        username,
        email,
        Address(street, suite, city, zipcode, Geo(lat, lng)),
        phone,
        website,
        Company(companyName, catchPhrase, bs)
    )
}

fun User.toEntity() = UserEntity(
    id,
    name,
    username,
    email,
    address.street,
    address.suite,
    address.city,
    address.zipcode,
    address.geo.lat,
    address.geo.lng,
    phone,
    website,
    company.name,
    company.catchPhrase,
    company.bs
)