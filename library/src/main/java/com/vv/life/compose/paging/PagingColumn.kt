package com.vv.life.compose.paging

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.vv.life.mvvmhabit.base.BaseApplication
import com.vv.life.mvvmhabit.base.SpaceModel
import com.vv.life.mvvmhabit.binding.viewadapter.refresh.RefreshStatus
import com.vv.life.mvvmhabit.widget.recyclerview.FooterStatus
import com.vv.life.mvvmhabit.widget.recyclerview.paging.LoadCallback
import com.vv.life.mvvmhabit.widget.recyclerview.viewmodel.BasePagingViewModel

/**
 * Desc: 分页的列表
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
@Composable
fun <ITEM> PagingColumn(
    viewModel: BasePagingViewModel<*, ITEM>,
    content: @Composable (item: ITEM) -> Unit
) {
    val loadStatus by viewModel.loadStatus.observeAsState()
    val items = remember { viewModel.items }
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(items) { index, item ->
            if (index >= items.size - viewModel.getPreLoadOffset()) {
                if (loadStatus == FooterStatus.STATUS_NONE) {
                    viewModel.callLoadMore()
                }
            }
            content(item)
        }
        if (loadStatus != FooterStatus.STATUS_NONE) {
            item {
                LoadFooter(viewModel)
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
fun PagingColumnPreview() {
    val viewModel = object : BaseComposeListViewModel<SpaceModel, String>(BaseApplication()) {
        override fun loadData(pageIndex: Int, loadCallback: LoadCallback<String>) {

        }
    }
    viewModel.refreshStatus.value = RefreshStatus.STATUS_REFRESHING
    viewModel.loadStatus.value = FooterStatus.STATUS_NO_MORE
    repeat(10) {
        viewModel.items.add("item")
    }
    PagingColumn(viewModel) {
        Text(text = "item")
    }
}