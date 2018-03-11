package cn.dong.architecture.data

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList

/**
 * @author dong on 2018/03/11.
 */
data class Listing<T>(
        val pagedList: LiveData<PagedList<T>>,

        val networkState: LiveData<NetworkState>,

        val refreshState: LiveData<NetworkState>,

        val refresh: () -> Unit,

        val retry: () -> Unit)