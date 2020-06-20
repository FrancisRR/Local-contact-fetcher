package com.francis.machinetest.dagger

import com.francis.machinetest.base.BaseFragment
import com.francis.machinetest.base.BaseViewModel
import com.francis.machinetest.base.BaseWorker
import com.francis.machinetest.base.Repo
import com.francis.machinetest.job.ContactListFetcher
import com.francis.paging.ui.paging.recyclerview.AdapterDataSource
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(repo: Repo)
    fun inject(job: BaseWorker)
    fun inject(baseFragment: BaseFragment)
    fun inject(viewModel: BaseViewModel)
    fun inject(dataSource: AdapterDataSource)
}