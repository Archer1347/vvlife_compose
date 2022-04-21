package com.vv.life.compose.example.list

import androidx.lifecycle.MutableLiveData
import com.vv.life.mvvmhabit.base.MultiItemViewModel
import com.vv.life.mvvmhabit.binding.command.BindingAction
import com.vv.life.mvvmhabit.binding.command.BindingCommand

/**
 * Desc:
 * <p>
 * Date: 2021/8/6
 * Copyright: Copyright (c) 2010-2020
 * Company: @微微科技有限公司
 * Updater:
 * Update Time:
 * Update Comments:
 *
 * @author: linjiaqiang
 */
class ListItemViewModel(viewModel: ListViewModel, val str: String) :
    MultiItemViewModel<ListViewModel>(viewModel) {

    val clickCount = MutableLiveData(0)

    val onItemClickCommand = BindingCommand<Unit>(BindingAction {
        clickCount.value = (clickCount.value ?: 0) + 1
    })
}
