package com.francis.machinetest.base

import com.francis.machinetest.data.response.contact.ContactModel
import com.francis.machinetest.db.RoomDb
import com.francis.machinetest.utils.RxJavaUtils
import com.francis.machinetest.utils.UiUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class Repo {

    private val TAG by lazy { Repo::class.java.simpleName }

    /*dagger object injected*/
    @set:Inject
    internal lateinit var roomDb: RoomDb


    init {
        AppController.dagger.inject(this)
    }


  /*  internal fun getDbCountaa(): Int {
        *//*   Observable.defer { roomDb.getRoomDao().getCount() }
               .compose(RxJavaUtils.applyNewObservableScheduler())
               .map { it }
               .blockingSingle()
           *//*

        val dd = Observable.defer { roomDb.getRoomDao().getCount() }
            .compose(RxJavaUtils.applyNewObservableScheduler())
            .map { it }
            .blockingSingle()


        UiUtils.appErrorLog(TAG, "" + dd)
        return 10;
    }
*/
    internal fun getDbCount(): Int = runBlocking {
        return@runBlocking GlobalScope.async {
            val res = roomDb.getRoomDao().getCount()
            return@async res
        }.await()
    }

    internal fun getAllContact(): Observable<MutableList<ContactModel>> {
        return Observable.defer { roomDb.getRoomDao().getAllData() }
            .compose(RxJavaUtils.applyNewObservableScheduler())
            .map { it }
    }

    internal fun getFilterContact(key:String):Observable<MutableList<ContactModel>>{
        return Observable.defer { roomDb.getRoomDao().getDataWithSearch(key) }
            .compose(RxJavaUtils.applyObserverbleScheduler())
            .map { it }
    }

}