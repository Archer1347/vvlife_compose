package com.vv.life.compose.example.list

import android.app.Application
import com.vv.life.compose.paging.BaseComposeListViewModel
import com.vv.life.mvvmhabit.base.BaseModel
import com.vv.life.mvvmhabit.base.MultiItemViewModel
import com.vv.life.mvvmhabit.extensions.launch
import com.vv.life.mvvmhabit.widget.recyclerview.paging.LoadCallback
import com.vv.life.mvvmhabit.widget.recyclerview.paging.PAGE_NUM_INIT
import kotlinx.coroutines.delay

/**
 * Desc:
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
open class ListViewModel(application: Application) :
    BaseComposeListViewModel<BaseModel<*>, MultiItemViewModel<ListViewModel>>(application) {

    override fun onCreate() {
        super.onCreate()
        setTitleText("分页列表")
    }

    override fun loadData(
        pageIndex: Int,
        loadCallback: LoadCallback<MultiItemViewModel<ListViewModel>>
    ) {
        launch({
            delay(1000)
            val viewModel = mutableListOf<MultiItemViewModel<ListViewModel>>()
            if (pageIndex == PAGE_NUM_INIT) {
                viewModel.add(ListHeadItemViewModel(this))
            }
            repeat(10) {
                viewModel.add(ListItemViewModel(this, "这是第${pageIndex}页，第${it}个Item"))
            }
            loadCallback.onSuccess(viewModel, pageIndex, 5)
        }, {
            loadCallback.onFailure()
        })
    }
}