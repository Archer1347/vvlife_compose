package com.vv.life.compose.example.pager

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.vv.life.compose.BaseComposeActivity
import com.vv.life.compose.example.list.ListPage
import com.vv.life.compose.theme.VvLife_composeTheme
import kotlinx.coroutines.launch

/**
 * Desc: ViewPager示例，目前处于实验状态
 * <p>
 * Date: 2021/8/9
 * Copyright: Copyright (c) 2010-2020
 * Company: @微微科技有限公司
 * Updater:
 * Update Time:
 * Update Comments:
 *
 * @author: linjiaqiang
 */
class PagerActivity : BaseComposeActivity<PagerViewModel>() {

    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VvLife_composeTheme(viewModel) {
                PagerPage()
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
fun PagerPage(viewModel: PagerViewModel = viewModel()) {
    val items = remember {
        viewModel.pagerItems
    }
    val pagerState = rememberPagerState(pageCount = items.size)
    val coroutineScope = rememberCoroutineScope()
    Box {
        Column {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                        color = Color.Blue,
                    )
                },
                backgroundColor = Color.White,
                contentColor = Color.Red,
            ) {
                viewModel.indicators.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                    )
                }
            }
            HorizontalPager(state = pagerState) {
                VvLife_composeTheme(
                    viewModel = viewModel.pagerItems[it],
                    needToolbar = false,
                ) {
                    ListPage(viewModel.pagerItems[it])
                }
            }
        }
        Button(
            onClick = {
                viewModel.onAddTabClickCommand.execute()
            },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
        ) {
            Text(text = "添加一个Tab")
        }
    }
}
