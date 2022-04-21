package com.vv.life.compose.paging

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.vv.life.mvvmhabit.base.BaseApplication
import com.vv.life.mvvmhabit.base.SpaceModel
import com.vv.life.mvvmhabit.binding.viewadapter.refresh.RefreshStatus
import com.vv.life.mvvmhabit.widget.recyclerview.FooterStatus
import com.vv.life.mvvmhabit.widget.recyclerview.paging.LoadCallback
import com.vv.life.mvvmhabit.widget.recyclerview.viewmodel.BasePagingViewModel

/**
 * Desc: 分页，支持下拉刷新及上拉加载
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
fun <ITEM> RefreshColumn(
    viewModel: BasePagingViewModel<*, ITEM>,
    content: @Composable (item: ITEM) -> Unit
) {
    val refreshStatus by viewModel.refreshStatus.observeAsState()
    SwipeRefresh(
        state = rememberSwipeRefreshState(
            isRefreshing = refreshStatus == RefreshStatus.STATUS_REFRESHING
        ),
        swipeEnabled = refreshStatus != RefreshStatus.STATUS_FORBID,
        onRefresh = {
            viewModel.refreshStatus.value = RefreshStatus.STATUS_REFRESHING
            viewModel.onRefreshCommand.execute()
        },
    ) {
        PagingColumn(
            viewModel = viewModel,
            content = content,
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
fun RefreshColumnPreview() {
    val viewModel = object : BaseComposeListViewModel<SpaceModel, String>(BaseApplication()) {
        override fun loadData(pageIndex: Int, loadCallback: LoadCallback<String>) {

        }
    }
    viewModel.refreshStatus.value = RefreshStatus.STATUS_REFRESHING
    viewModel.loadStatus.value = FooterStatus.STATUS_NO_MORE
    repeat(10) {
        viewModel.items.add("item")
    }
    RefreshColumn(viewModel) {
        Text(text = "item")
    }
}