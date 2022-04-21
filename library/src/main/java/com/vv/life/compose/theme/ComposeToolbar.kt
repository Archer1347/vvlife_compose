package com.vv.life.compose.theme

import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vv.life.compose.R
import com.vv.life.mvvmhabit.base.BaseApplication
import com.vv.life.mvvmhabit.base.BaseViewModel
import com.vv.life.mvvmhabit.base.SpaceViewModel

/**
 * Desc: 标题栏
 * <p>
 * Date: 2021/8/3
 * Copyright: Copyright (c) 2010-2020
 * Company: @微微科技有限公司
 * Updater:
 * Update Time:
 * Update Comments:
 *
 * @author: linjiaqiang
 */
@Composable
fun ComposeToolbar(viewModel: BaseViewModel<*>) {
    val title by viewModel.titleText.observeAsState()
    val rightText by viewModel.rightText.observeAsState()
    val rightTextEnable by viewModel.rightTextEnable.observeAsState()
    val rightTextVisibility by viewModel.rightTextVisibleObservable.observeAsState()
    val rightIcon by viewModel.rightIconObservable.observeAsState()
    val rightIconVisibility by viewModel.rightIconVisibleObservable.observeAsState()
    Box(
        modifier = Modifier
            .height(44.dp)
            .fillMaxWidth(),
    ) {
        // 返回键
        Image(
            painter = painterResource(id = R.mipmap.compose_ic_back),
            contentDescription = "back",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = 6.dp)
                .size(44.dp)
                .clickable {
                    viewModel.backOnClick.execute()
                }
        )
        // 标题
        Text(
            text = title.orEmpty(),
            color = Color(0xFF212121),
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 56.dp)
                .wrapContentSize(Alignment.Center)
        )
        // 右边文本
        if (rightTextVisibility == View.VISIBLE) {
            Text(
                text = rightText.orEmpty(),
                color = Color(0xFFF38700),
                fontSize = 17.sp,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable(enabled = rightTextEnable ?: true) {
                        viewModel.onRightTextPressed()
                    }
                    .height(44.dp)
                    .wrapContentSize()
                    .padding(horizontal = 12.dp)
            )
        }
        // 右边图标
        if (rightIconVisibility == View.VISIBLE) {
            Image(
                painter = painterResource(id = rightIcon ?: 0),
                contentDescription = "menu",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(horizontal = 6.dp)
                    .size(44.dp)
                    .clickable {
                        viewModel.onRightPressed()
                    }
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
internal fun ToolbarPreview() {
    val viewModel = SpaceViewModel(BaseApplication())
    viewModel.titleText.value = "标题"
    viewModel.rightTextVisibleObservable.value = View.VISIBLE
    viewModel.rightText.value = "菜单"
    ComposeToolbar(viewModel)
}