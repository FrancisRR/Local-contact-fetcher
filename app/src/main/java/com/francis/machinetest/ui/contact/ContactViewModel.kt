package com.francis.machinetest.ui.contact

import android.content.Context
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.transition.Visibility
import com.francis.machinetest.R
import com.francis.machinetest.base.AppController
import com.francis.machinetest.base.BaseViewModel
import com.francis.machinetest.data.response.contact.ContactModel
import com.francis.machinetest.utils.UiUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_contact.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.selects.SelectClause1

internal class ContactViewModel : BaseViewModel() {

    private val TAG by lazy { ContactViewModel::class.java.simpleName }
    public var statusText = MutableLiveData<String>()
    public var editTextHint = MutableLiveData<String>()
    public var noListIndicatorVisibility = MutableLiveData<Int>()
    public var edSearchVisibility = MutableLiveData<Int>()
    public var listVisibility = MutableLiveData<Int>()

    internal var dbCount: MutableLiveData<ContactModel> = MutableLiveData()
    internal var contactList: MutableLiveData<MutableList<ContactModel>> = MutableLiveData()
    private lateinit var appContext: Context

    init {
        appContext = AppController.instance
        editTextHint.value = AppController.instance.resources.getString(R.string.enter_name_hint)
    }

    internal fun showPermissionDenyText() {
        listVisibility.value = View.GONE
        noListIndicatorVisibility.value = View.VISIBLE
        edSearchVisibility.value = View.GONE
        statusText.value = appContext.resources.getString(R.string.allow_permission)
    }

    internal fun showNoContactText() {
        listVisibility.value = View.GONE
        noListIndicatorVisibility.value = View.VISIBLE
        edSearchVisibility.value = View.GONE
        statusText.value = appContext.resources.getString(R.string.no_contact_found)
    }

    internal fun showList() {
        listVisibility.value = View.VISIBLE
        noListIndicatorVisibility.value = View.GONE
        getAllContact()
        edSearchVisibility.value = View.VISIBLE

    }

    internal fun getDbCount(): Int {
        return repo.getDbCount()
    }

    internal fun getAllContact() {
        repo.getAllContact()
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                { res -> contactList.value = res },
                { thr -> UiUtils.appErrorLog(TAG, thr.message) }
            )
    }

    internal fun getFilterList(key: String) {
        repo.getFilterContact(key)
            .subscribe({ res ->
                contactList.value = res
            }, { throwable ->
                UiUtils.appErrorLog(TAG, throwable.message)
            })
    }
}