package com.vv.life.compose.example.list

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
class ListHeadItemViewModel(viewModel: ListViewModel) :
    MultiItemViewModel<ListViewModel>(viewModel) {

    val head = "这是head，点击添加一个head"

    val onClickCommand = BindingCommand<Unit>(BindingAction {
        viewModel.items.add(0, ListHeadItemViewModel(viewModel))
    })
}