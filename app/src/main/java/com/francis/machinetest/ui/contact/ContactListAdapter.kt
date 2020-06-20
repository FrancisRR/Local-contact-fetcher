package com.francis.machinetest.ui.contact

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.francis.machinetest.R
import com.francis.machinetest.data.response.contact.ContactModel
import com.francis.machinetest.databinding.ItemContactListBinding

class ContactListAdapter(val context: Context?) :
    RecyclerView.Adapter<ContactListAdapter.ContactListModel>() {

    private val TAG by lazy { ContactListAdapter::class.java.simpleName }
    private var contactList: MutableList<ContactModel> = mutableListOf()
    var clickItem: MutableLiveData<ContactModel> = MutableLiveData()

    internal fun setAdapterList(mInputList: MutableList<ContactModel>?) {
        contactList.clear()
        if (mInputList != null) {
            contactList.addAll(mInputList)
        }
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactListModel {
        /*val view = LayoutInflater.from(context).inflate(R.layout.item_contact_list, parent, false)
        return ContactListModel(view)*/
        val binding: ItemContactListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.item_contact_list,
            parent,
            false
        )
        return ContactListModel(binding)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ContactListModel, position: Int) {
        val contactModel = contactList.get(position)
        holder.bindItemToView(position, contactModel)
    }

    inner class ContactListModel(val binding: ItemContactListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.conParent.setOnClickListener { onClickItem() }
        }

        fun bindItemToView(position: Int, contactModel: ContactModel?) {
            binding.tvName.text = "${contactModel?.contactName}"
            binding.tvNumber.text = "${contactModel?.phoneNumber}"

        }


        public fun onClickItem() {
            val position = adapterPosition
            if (position < 0) {
                return
            }
            clickItem.value = contactList.get(adapterPosition)
        }


    }


}