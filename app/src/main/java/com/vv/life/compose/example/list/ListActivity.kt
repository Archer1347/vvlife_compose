package com.vv.life.compose.example.list

import android.app.Application
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vv.life.compose.BaseComposeActivity
import com.vv.life.compose.paging.RefreshColumn
import com.vv.life.compose.theme.VvLife_composeTheme
import com.vv.life.mvvmhabit.widget.recyclerview.FooterStatus

/**
 * Desc: 列表Demo
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
class ListActivity : BaseComposeActivity<ListViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VvLife_composeTheme(viewModel) {
                ListPage()
            }
        }
    }
}

@Composable
fun ListPage(viewModel: ListViewModel = viewModel()) {
    RefreshColumn(viewModel = viewModel) {
        if (it is ListHeadItemViewModel) {
            ListHeadItem(viewModel = it)
        } else if (it is ListItemViewModel) {
            ListItem(viewModel = it)
        }
    }
    LaunchedEffect(key1 = viewModel) {
        viewModel.loadInit()
    }
}

@Composable
fun ListHeadItem(viewModel: ListHeadItemViewModel) {
    Text(
        text = viewModel.head,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .wrapContentSize()
            .background(color = Color.Gray)
            .clickable {
                viewModel.onClickCommand.execute()
            },
    )
}

@Composable
fun ListItem(viewModel: ListItemViewModel) {
    val count by viewModel.clickCount.observeAsState()
    Text(
        text = "${viewModel.str}, 点击了${count}次",
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable {
                viewModel.onItemClickCommand.execute()
            }
            .wrapContentSize()
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
fun ComposeListPagePreview() {
    val viewModel = ListViewModel(Application())
    viewModel.items.add(ListHeadItemViewModel(viewModel))
    repeat(5) {
        viewModel.items.add(ListItemViewModel(viewModel, "item${it}"))
    }
    viewModel.loadStatus.value = FooterStatus.STATUS_FAILED
    ListPage(viewModel)
}

