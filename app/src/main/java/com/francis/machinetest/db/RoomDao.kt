package com.francis.machinetest.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.francis.machinetest.data.response.contact.ContactModel
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(model: ContactModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(model: MutableList<ContactModel>)

    @Query("SELECT * FROM ContactModel ORDER BY contactName ASC")
    fun getAll(): LiveData<MutableList<ContactModel>>


    @Query("SELECT * FROM ContactModel ORDER BY contactName ASC")
    fun getAllData(): Observable<MutableList<ContactModel>>

    @Query("SELECT COUNT(*) FROM ContactModel")
    fun getCount(): Int

    @Query("SELECT * FROM ContactModel WHERE id BETWEEN(:mFrom) AND(:mTo)")
    fun getDataPosition(mFrom: Int, mTo: Int): MutableList<ContactModel>

    @Query("SELECT * FROM ContactModel WHERE id BETWEEN(:mFrom) AND(:mTo) AND (contactName LIKE '%' || :key || '%' OR phoneNumber LIKE '%' || :key || '%')")
    fun getDataPositionWithSearch(mFrom: Int, mTo: Int, key: String): MutableList<ContactModel>

    @Query("SELECT * FROM ContactModel WHERE (contactName LIKE '%' ||:key || '%' OR phoneNumber LIKE '%' || :key || '%') ORDER BY contactName ASC ")
    fun getDataWithSearch(key: String): Observable<MutableList<ContactModel>>


}