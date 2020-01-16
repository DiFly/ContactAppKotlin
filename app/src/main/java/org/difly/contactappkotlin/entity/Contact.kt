package org.difly.contactappkotlin.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_table")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "firstname")
    @NonNull
    val firstName: String,
    @ColumnInfo(name = "lastname")
    val  lastName: String,
    @ColumnInfo(name = "phonetype")
    val phoneType: String,
    @ColumnInfo(name = "phonenumber")
    @NonNull
    val phoneNumber: String
) {
    constructor(): this(null, "", "", "", "")
}
