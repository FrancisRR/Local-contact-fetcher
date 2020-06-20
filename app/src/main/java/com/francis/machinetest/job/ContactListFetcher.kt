package com.francis.machinetest.job

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import androidx.work.*
import com.francis.machinetest.base.AppConstant
import com.francis.machinetest.base.AppController
import com.francis.machinetest.base.BaseWorker
import com.francis.machinetest.data.response.contact.ContactModel
import com.francis.machinetest.utils.PermissionHandler
import com.francis.machinetest.utils.RxJavaUtils
import com.francis.machinetest.utils.UiUtils
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

class ContactListFetcher(val context: Context, val param: WorkerParameters) :
    BaseWorker(context, param) {

    companion object {
        fun callContactFetcher(request: WorkRequest) {
            WorkManager.getInstance(AppController.instance).enqueue(request)
        }
    }


    private val TAG by lazy { ContactListFetcher::class.java.simpleName }
    private val contactList: MutableList<ContactModel> = mutableListOf()


    override fun doWork(): Result {
        if (PermissionHandler.checkPermissionGrandedOrNot(AppConstant.contactReadPermission) && repo.getDbCount() == 0) {
            val cursor: Cursor? = context.contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                null
            )

            val nameIndex: Int = cursor?.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)!!
            val numberIndex: Int =
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            var i = 0;
            if (cursor.moveToFirst()) {
                while (cursor.moveToNext()) {
                    val name =
                        cursor.getString(nameIndex)
                    var phoneNumber =
                        cursor.getString(numberIndex)

                    phoneNumber = phoneNumber.replace("[()\\s-]+", "");
                    i++;
                    ContactModel(i, name, phoneNumber).also {
                        contactList.add(it)
                    }

                }
            }

            Completable.fromCallable {
                roomDb.getRoomDao().insert(contactList)
            }.compose(RxJavaUtils.applyCompletableSchedulers())
                .subscribe({
                    UiUtils.appErrorLog(TAG, "Db update complete")
                }, { thr ->
                    UiUtils.appErrorLog(TAG, "Db update fail")
                })



            return Result.success()
        } else {
            return Result.failure()
        }


    }
}