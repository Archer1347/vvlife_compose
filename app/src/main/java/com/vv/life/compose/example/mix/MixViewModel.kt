package com.vv.life.compose.example.mix

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.vv.life.mvvmhabit.base.BaseModel
import com.vv.life.mvvmhabit.base.BaseViewModel
import com.vv.life.mvvmhabit.binding.command.BindingAction
import com.vv.life.mvvmhabit.binding.command.BindingCommand

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
class MixViewModel(application: Application) : BaseViewModel<BaseModel<Unit>>(application) {

    val inputText = MutableLiveData("http://www.baidu.com/")
    val url = MutableLiveData(inputText.value)

    val onLoadClickCommand = BindingCommand<Unit>(BindingAction {
        url.value = inputText.value
    })

    init {
        setTitleText("Compose嵌套原生View")
    }
}