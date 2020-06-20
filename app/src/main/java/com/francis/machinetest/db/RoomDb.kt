package com.francis.machinetest.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.francis.machinetest.data.response.contact.ContactModel

@Database(entities = [ContactModel::class], version = 1)
abstract class RoomDb :RoomDatabase(){
    abstract fun getRoomDao(): RoomDao
}