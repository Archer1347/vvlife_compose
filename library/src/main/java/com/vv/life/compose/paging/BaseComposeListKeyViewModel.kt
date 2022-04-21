package com.vv.life.compose.paging

import android.app.Application
import androidx.recyclerview.widget.RecyclerView
import com.vv.life.mvvmhabit.base.BaseModel
import com.vv.life.mvvmhabit.widget.recyclerview.paging.PageKeyDataSource
import com.vv.life.mvvmhabit.widget.recyclerview.paging.PagingDataSource
import com.vv.life.mvvmhabit.widget.recyclerview.viewmodel.BasePagingViewModel
import com.vv.life.mvvmhabit.widget.recyclerview.viewmodel.PagingKeyLoader
import me.tatarka.bindingcollectionadapter2.ItemBinding

/**
 * Desc: Compose分页的ListKeyViewModel
 * 1. 使用[PagingSateList]进行列表的监听刷新
 * 2. 目前Compose暂不支持差异化更新，所以不需要使用[com.vv.life.mvvmhabit.binding.collections.DiffObservableArrayList]
 * 3. 去掉适配RecyclerView的相关的实现
 * <p>
 * Date: 2021/8/5
 * Copyright: Copyright (c) 2010-2020
 * Company: @微微科技有限公司
 * Updater:
 * Update Time:
 * Update Comments:
 *
 * @author: linjiaqiang
 */
abstract class BaseComposeListKeyViewModel<M : BaseModel<*>, ITEM, KEY>(application: Application) :
    BasePagingViewModel<M, ITEM>(application), PagingKeyLoader<ITEM, KEY> {

    override fun createDataSource(): PagingDataSource<ITEM> {
        return PageKeyDataSource(
            source = PagingSateList(),
            firstPageKey = getFirstKey(),
            sourceLoader = this
        ) {
            finishLoad(it)
        }
    }

    /**
     * Desc: 设置第一页的key
     * <p>
     * Author: linjiaqiang
     * Date: 2021/8/9
     */
    abstract fun getFirstKey(): KEY

    override fun getItemDecoration(): RecyclerView.ItemDecoration? {
        return null
    }

    override fun getItemBinding(): ItemBinding<ITEM> {
        return ItemBinding.of { _, _, _ -> }
    }

}