package com.vv.life.compose.example

import android.app.Application
import android.util.Log
import android.view.View
import com.vv.life.compose.example.list.ListActivity
import com.vv.life.compose.example.pager.PagerActivity
import com.vv.life.compose.example.mix.MixActivity
import com.vv.life.mvvmhabit.base.BaseModel
import com.vv.life.mvvmhabit.base.BaseViewModel
import com.vv.life.mvvmhabit.base.state.EmptyState
import com.vv.life.mvvmhabit.base.state.ReloadType
import com.vv.life.mvvmhabit.binding.command.BindingAction
import com.vv.life.mvvmhabit.binding.command.BindingCommand
import com.vv.life.mvvmhabit.extensions.launch
import kotlinx.coroutines.delay

/**
 * Desc:
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
class MainViewModel(application: Application) : BaseViewModel<BaseModel<Unit>>(application) {

    /**
     * 列表
     */
    val onListClickCommand = BindingCommand<Unit>(BindingAction {
        startActivity(ListActivity::class.java)
    })

    /**
     * ViewPager
     */
    val onPagerClickCommand = BindingCommand<Unit>(BindingAction {
        startActivity(PagerActivity::class.java)
    })

    /**
     * Compose嵌套原生View
     */
    val onMixClickCommand = BindingCommand<Unit>(BindingAction {
        startActivity(MixActivity::class.java)
    })

    override fun onCreate() {
        super.onCreate()
        mStateModel.setReloadType(ReloadType.NET_ERROR)
        mStateModel.emptyState = EmptyState.PROGRESS
        launch({
            setTitleText("Compose")
            setRightTextVisible(View.VISIBLE)
            setRightText("菜单")
            delay(500)
            mStateModel.emptyState = EmptyState.NORMAL
        })
    }

    override fun onReloadPressed() {
        super.onReloadPressed()
        Log.e("linjiaqiang", "重试")
        mStateModel.emptyState = EmptyState.PROGRESS
        launch({
            delay(2000)
            mStateModel.emptyState = EmptyState.EMPTY
        })
    }

    override fun onRightPressed() {
        super.onRightPressed()
        Log.e("linjiaqiang", "右边图标点击")
    }

    override fun onRightTextPressed() {
        super.onRightTextPressed()
        Log.e("linjiaqiang", "右边文字点击")
    }
}