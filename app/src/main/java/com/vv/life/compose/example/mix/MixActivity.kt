package com.vv.life.compose.example.mix

import android.os.Bundle
import android.webkit.WebView
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.vv.life.compose.BaseComposeActivity
import com.vv.life.compose.theme.VvLife_composeTheme
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Desc: Compose嵌套原生View
 * <p>
 * Date: 2021/8/10
 * Copyright: Copyright (c) 2010-2020
 * Company: @微微科技有限公司
 * Updater:
 * Update Time:
 * Update Comments:
 *
 * @author: linjiaqiang
 */
class MixActivity : BaseComposeActivity<MixViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VvLife_composeTheme(viewModel = viewModel) {
                MixPage()
            }
        }
    }
}

@Composable
fun MixPage(viewModel: MixViewModel = viewModel()) {
    val inputText by viewModel.inputText.observeAsState()
    val url by viewModel.url.observeAsState()
    Column {
        Row {
            TextField(
                value = inputText.orEmpty(),
                onValueChange = {
                    viewModel.inputText.value = it
                },
            )
            Button(
                onClick = {
                    viewModel.onLoadClickCommand.execute()
                })
            {
                Text(text = "确定")
            }
        }
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                WebView(it)
            },
            update = {
                it.loadUrl(url.toString())
            }
        )
    }
}