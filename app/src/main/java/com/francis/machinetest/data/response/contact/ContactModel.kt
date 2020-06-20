package com.francis.machinetest.data.response.contact

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ContactModel")
data class ContactModel(
    @ColumnInfo(name = "id") @PrimaryKey var id: Int,
    @ColumnInfo(name = "contactName") var contactName: String?,
    @ColumnInfo(name = "phoneNumber") var phoneNumber: String?
) {
}