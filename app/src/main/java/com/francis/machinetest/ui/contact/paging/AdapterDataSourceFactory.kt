package com.francis.paging.ui.paging.recyclerview

import androidx.paging.DataSource
import com.francis.machinetest.data.response.contact.ContactModel

class AdapterDataSourceFactory : DataSource.Factory<Int, ContactModel>() {
    override fun create(): DataSource<Int, ContactModel> {
        val dataSource = AdapterDataSource()
        return dataSource
    }
}