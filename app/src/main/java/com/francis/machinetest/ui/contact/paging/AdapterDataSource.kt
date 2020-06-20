package com.francis.paging.ui.paging.recyclerview

import androidx.paging.PageKeyedDataSource
import com.francis.machinetest.base.AppController
import com.francis.machinetest.data.response.contact.ContactModel
import com.francis.machinetest.db.RoomDb
import com.francis.machinetest.utils.UiUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class AdapterDataSource : PageKeyedDataSource<Int, ContactModel>() {

    private val TAG by lazy { AdapterDataSource::class.java.simpleName }

    @set:Inject
    lateinit var roomDb: RoomDb

    private var END_PAGE = 10
    private val START_PAGE = 0

    init {
        AppController.dagger.inject(this)
    }


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ContactModel>
    ) {
        callback.onResult(dbList(START_PAGE, END_PAGE), null, 1)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ContactModel>) {
        val key = params.key
        callback.onResult(dbList(END_PAGE + 1, END_PAGE + 10), key)
        END_PAGE += 10
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ContactModel>) {

    }


    private fun dbList(startP: Int, endP: Int): MutableList<ContactModel> = runBlocking {
        UiUtils.appLog("Size", "start:$startP || end:${endP}")
        return@runBlocking GlobalScope.async {

            val res = roomDb.getRoomDao().getDataPosition(startP, endP)

            UiUtils.appLog(TAG, res.toString())
            return@async res
        }.await()
    }
}