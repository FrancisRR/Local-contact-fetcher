package com.francis.machinetest.db

import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.francis.machinetest.base.AppController

object RoomDbController {

   internal fun getRoomInstance(): RoomDb {
        val db: RoomDb =
            Room.databaseBuilder(AppController.instance, RoomDb::class.java, "room_db")
                // .addMigrations(migration1)
                .build()
        return db;
    }

    /*Sample migration method*/
   private val migration1 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            val MigString = "ALTER TABLE ContactModel ADD COLUMN isNew INTEGER NOT NULL DEFAULT 1"
            database.execSQL(MigString)
        }
    }

}