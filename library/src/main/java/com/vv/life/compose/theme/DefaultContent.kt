package com.vv.life.compose.theme

import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import com.scwang.smartrefresh.layout.util.SmartUtil
import com.vv.life.mvvmhabit.base.state.EmptyState
import com.vv.life.mvvmhabit.base.state.StateModel

/**
 * Desc: 缺省页
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
fun ComposeDefaultContent(stateModel: StateModel) {
    val emptyState by stateModel.emptyStateChange.observeAsState()
    val top by stateModel.marginTop.observeAsState()
    val icon by stateModel.emptyIconResId.observeAsState()
    val title by stateModel.currentStateLabel.observeAsState()
    val subTitle by stateModel.currentNotifyStateLabel.observeAsState()
    val titleVisibility by stateModel.titleVisibility.observeAsState()
    val showReload by stateModel.showReload.observeAsState()
    val reloadStr by stateModel.reloadStr.observeAsState()
    if (emptyState == EmptyState.PROGRESS) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(com.vv.life.mvvmhabit.R.raw.vv_page_loading))
        val progress by animateLottieCompositionAsState(
            composition,
            iterations = LottieConstants.IterateForever,
        )
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.Center)
                    .background(
                        color = Color(0x99000000),
                        shape = RoundedCornerShape(8.dp),
                    )
            ) {
                LottieAnimation(
                    composition = composition, progress = progress,
                    contentScale = ContentScale.None,
                )
            }
        }
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            if (icon != null) {
                Image(
                    painter = painterResource(id = icon!!),
                    contentDescription = "empty icon",
                    modifier = Modifier.padding(top = SmartUtil.px2dp(top ?: 0).dp)
                )
                if (titleVisibility == View.VISIBLE) {
                    Text(
                        text = title.orEmpty(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize()
                            .padding(start = 20.dp, end = 20.dp),
                    )
                }
                Text(
                    text = subTitle.orEmpty(),
                    fontSize = 14.sp,
                    color = Color(0xFF999999),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize()
                        .padding(start = 56.dp, end = 56.dp, top = 12.dp),
                )
                if (showReload == View.VISIBLE) {
                    Spacer(modifier = Modifier.height((34.dp)))
                    Text(
                        text = reloadStr.orEmpty(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFFFFF),
                        modifier = Modifier
                            .height(34.dp)
                            .background(
                                color = Color(0xFFFFA22D),
                                shape = RoundedCornerShape(50.dp)
                            )
                            .clickable {
                                stateModel.reloadOnClick.execute()
                            }
                            .padding(start = 20.dp, end = 20.dp)
                            .wrapContentSize(),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ComposeDefaultContentPreview() {
    val stateModel = StateModel()
    stateModel.emptyState = EmptyState.NET_ERROR
    ComposeDefaultContent(stateModel)
}
