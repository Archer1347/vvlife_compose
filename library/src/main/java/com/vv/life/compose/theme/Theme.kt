package com.vv.life.compose.theme

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import com.vv.life.mvvmhabit.base.BaseViewModel
import com.vv.life.mvvmhabit.base.state.EmptyState

/**
 * Desc: 页面主题样式
 * <p>
 * Author: linjiaqiang
 * Date: 2021/8/4
 *
 * @param viewModel
 * @param needToolbar 是否需要标题栏
 * @param defaultOnTop 缺省页是否覆盖在内容上面
 */
@Composable
fun VvLife_composeTheme(
    viewModel: BaseViewModel<*>,
    needToolbar: Boolean = true,
    defaultOnTop: Boolean = true,
    content: @Composable () -> Unit
) {
    // 如果要启用水波纹效果，使用MaterialTheme
    val indication = remember {
        DefaultIndication
    }
    CompositionLocalProvider(
        LocalIndication provides indication,
    ) {
        Screen(
            viewModel = viewModel,
            needToolbar = needToolbar,
            defaultOnTop = defaultOnTop,
            content = content
        )
    }
}


@Composable
fun Screen(
    viewModel: BaseViewModel<*>,
    needToolbar: Boolean,
    defaultOnTop: Boolean,
    content: @Composable () -> Unit
) {
    val emptyState by viewModel.mStateModel.emptyStateChange.observeAsState()
    Column {
        if (needToolbar) {
            ComposeToolbar(viewModel)
        }
        Box {
            if (emptyState == EmptyState.NORMAL || !defaultOnTop) {
                content()
            }
            if (emptyState != EmptyState.NORMAL) {
                ComposeDefaultContent(viewModel.mStateModel)
            }
        }
    }
}

/**
 * Desc: 自定义Indication，去掉水波纹效果
 * <p>
 * Author: linjiaqiang
 * Date: 2021/8/4
 */
private object DefaultIndication : Indication {

    private class DefaultDebugIndicationInstance : IndicationInstance {
        override fun ContentDrawScope.drawIndication() {
            drawContent()
        }
    }

    @Composable
    override fun rememberUpdatedInstance(interactionSource: InteractionSource): IndicationInstance {
        return remember(interactionSource) {
            DefaultDebugIndicationInstance()
        }
    }
}