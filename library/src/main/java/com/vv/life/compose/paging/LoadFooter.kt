package com.vv.life.compose.paging

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vv.life.compose.ui.ProgressPainter
import com.vv.life.mvvmhabit.widget.recyclerview.FooterStatus
import com.vv.life.mvvmhabit.widget.recyclerview.viewmodel.BasePagingViewModel
import com.vv.life.mvvmhabit.R
import com.vv.life.mvvmhabit.base.BaseApplication
import com.vv.life.mvvmhabit.base.BaseModel
import com.vv.life.mvvmhabit.base.SpaceModel
import com.vv.life.mvvmhabit.widget.recyclerview.paging.LoadCallback

/**
 * Desc: 加载更多的样式
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
fun LoadFooter(viewModel: BasePagingViewModel<*, *>) {
    val footerStatus = viewModel.loadStatus.value
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
    ) {
        when (footerStatus) {
            // 加载中
            FooterStatus.STATUS_LOADING -> {
                ProgressPainter()
            }
            // 没有更多数据
            FooterStatus.STATUS_NO_MORE -> {
                Text(
                    text = stringResource(id = R.string.footer_end),
                    fontSize = 12.sp,
                    color = Color(0xFF999999)
                )
            }
            // 加载失败重试
            else -> {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            viewModel.callLoadMore()
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_load_retry),
                        contentDescription = "retry",
                        modifier = Modifier.padding(end = 3.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.footer_retry),
                        fontSize = 12.sp,
                        color = Color(0xFF999999),
                        modifier = Modifier.padding(start = 3.dp)
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
fun LoadFooterPreview() {
    val viewModel = object : BaseComposeListViewModel<SpaceModel, Unit>(BaseApplication()) {
        override fun loadData(pageIndex: Int, loadCallback: LoadCallback<Unit>) {

        }
    }
    viewModel.loadStatus.value = FooterStatus.STATUS_NO_MORE
    LoadFooter(viewModel)
}