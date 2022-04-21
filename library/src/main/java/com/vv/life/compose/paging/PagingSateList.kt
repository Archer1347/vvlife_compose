package com.vv.life.compose.paging

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.vv.life.mvvmhabit.binding.collections.PagingList

/**
 * Desc: 实现[PagingList]接口，使其可接入分页组件
 * 代理[SnapshotStateList]，使其具备数据监听功能
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
class PagingSateList<T>(private val stateList: MutableList<T> = mutableStateListOf()) :
    PagingList<T>,
    MutableList<T> by stateList {

    override fun submit(newData: List<T>, append: Boolean) {
        if (!append) {
            stateList.clear()
        }
        stateList.addAll(newData)
    }
}