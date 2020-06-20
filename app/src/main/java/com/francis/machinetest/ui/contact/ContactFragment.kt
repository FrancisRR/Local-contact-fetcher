package com.francis.machinetest.ui.contact

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkRequest
import butterknife.OnTextChanged
import com.francis.machinetest.R
import com.francis.machinetest.base.AppConstant
import com.francis.machinetest.base.BaseFragment
import com.francis.machinetest.data.response.contact.ContactModel
import com.francis.machinetest.databinding.FragmentContactBinding
import com.francis.machinetest.databinding.FragmentContactBindingImpl
import com.francis.machinetest.job.ContactListFetcher
import com.francis.machinetest.utils.PermissionHandler
import com.francis.machinetest.utils.UiUtils
import com.francis.paging.ui.paging.recyclerview.AdapterDataSourceFactory
import kotlinx.android.synthetic.main.fragment_contact.*


class ContactFragment : BaseFragment() {

    private val TAG by lazy { ContactFragment::class.java.simpleName }
    private var parentView: View? = null
    private var viewModel: ContactViewModel? = null
    private var adapter: ContactListAdapter? = null
    private var request: WorkRequest? = null
    private lateinit var appContext: Context
    private var phoneNumberGlobal: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentContactBindingImpl =
            DataBindingUtil.inflate(inflater, R.layout.fragment_contact, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUi()
    }

    private fun setUi() {
        setInstance()
        setObserver()
        setAction()
    }

    private fun setInstance() {
        appContext = requireContext()
        createAdapter()
        request = OneTimeWorkRequest.Builder(ContactListFetcher::class.java).build()
    }


    private fun createAdapter() {
        adapter = ContactListAdapter(appContext)
        recyclerContactList.layoutManager = LinearLayoutManager(appContext)
        recyclerContactList.adapter = adapter
    }


    private fun setObserver() {

        viewModel?.contactList?.observe(viewLifecycleOwner, Observer {
            adapter?.setAdapterList(it)
        })

        val obj1: LiveData<MutableList<ContactModel>>? = roomDb.getRoomDao().getAll()
        obj1?.observe(viewLifecycleOwner, Observer { data: MutableList<ContactModel> ->
            adapter?.setAdapterList(data)
        })

        adapter?.clickItem?.observe(viewLifecycleOwner, Observer {
            phoneNumberGlobal = it.phoneNumber!!
            if (PermissionHandler.checkPermissionGrandedOrNot(AppConstant.callPhonePermission)) {
                UiUtils.startPhoneCall(appContext, it.phoneNumber!!)
            } else {
                requestPermissions(
                    arrayOf(AppConstant.callPhonePermission),
                    AppConstant.callPhoneRequestCode
                )
            }

        })


        edSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isNotEmpty()!!) {
                    viewModel?.getFilterList(s.toString().trim())
                } else {
                    viewModel?.getAllContact()
                }
            }
        })

    }

    private fun setAction() {
        if (PermissionHandler.checkPermissionGrandedOrNot(AppConstant.contactReadPermission)) {
            if (viewModel?.getDbCount()!! > 0) {
                viewModel?.showList()
            } else {
                viewModel?.showNoContactText()
            }
        } else {
            requestPermissions(
                arrayOf(AppConstant.contactReadPermission),
                AppConstant.contactPermissionRequestCode
            )
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == AppConstant.contactPermissionRequestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callContactFetchJob()
            } else {
                viewModel?.showPermissionDenyText()
            }
        } else if (requestCode == AppConstant.callPhoneRequestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                UiUtils.startPhoneCall(appContext, phoneNumberGlobal)
            } else {
                UiUtils.showToast("Please enable the permission")
            }
        }
    }


    private fun callContactFetchJob() {
        ContactListFetcher.callContactFetcher(request!!)
    }


}