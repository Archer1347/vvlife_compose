package com.vv.life.compose.example.pager

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import com.vv.life.compose.example.list.ListViewModel
import com.vv.life.mvvmhabit.base.BaseModel
import com.vv.life.mvvmhabit.base.BaseViewModel
import com.vv.life.mvvmhabit.binding.command.BindingAction
import com.vv.life.mvvmhabit.binding.command.BindingCommand
import com.vv.life.mvvmhabit.extensions.launch
import kotlinx.coroutines.delay

/**
 * Desc:
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
class PagerViewModel(application: Application) : BaseViewModel<BaseModel<Unit>>(application) {

    val indicators = mutableStateListOf<String>()
    val pagerItems = mutableStateListOf<PagerItemViewModel>()

    val onAddTabClickCommand = BindingCommand<Unit>(BindingAction {
        indicators.add("tab${pagerItems.size + 1}")
        pagerItems.add(PagerItemViewModel(getApplication()))
    })

    init {
        setTitleText("ViewPager示例")
        indicators.add("tab1")
        indicators.add("tab2")
        indicators.add("tab3")
        pagerItems.add(PagerItemViewModel(getApplication()))
        pagerItems.add(PagerItemViewModel(getApplication()))
        pagerItems.add(PagerItemViewModel(getApplication()))
    }

}

class PagerItemViewModel(application: Application) : ListViewModel(application)