package com.vv.life.compose

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vv.life.mvvmhabit.base.BaseViewModel
import com.vv.life.mvvmhabit.base.IBaseActivity
import com.vv.life.mvvmhabit.base.loading.LoadingDialogProvider
import com.vv.life.mvvmhabit.utils.StringUtils
import com.vv.life.mvvmhabit.utils.language.applyContextAndApplication
import com.vv.life.mvvmhabit.utils.language.wrapContext
import java.lang.reflect.ParameterizedType
import com.vv.life.mvvmhabit.R
import com.vv.life.mvvmhabit.utils.StatusBarUtils

/**
 * Desc: ViewPager示例，目前处于实验状态
 * <p>
 * Date: 2021/8/9
 * Copyright: Copyright (c) 2010-2020
 * Company: @微微科技有限公司
 * Updater:
 * Update Time:
 * Update Comments:
 *BaseComposeActivity
 * @author: linjiaqiang
 */
abstract class BaseComposeActivity<VM : BaseViewModel<*>> : AppCompatActivity(), IBaseActivity {

    protected lateinit var viewModel: VM
    private var dialog: Dialog? = null

    override fun attachBaseContext(newBase: Context?) {
        //替换对应语言环境的 context
        super.attachBaseContext(wrapContext(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //国际化语言环境替换
        applyContextAndApplication(this)
        //页面接受的参数方法
        initParam()
        initViewModel()
        //私有的ViewModel与View的契约事件回调逻辑
        registerUiChangeLiveDataCallBack()
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        super.setContentView(view, params)
        StatusBarUtils.setStatusBarColor(this, StringUtils.getColorResource(R.color.white), 0, true)
    }

    private fun initViewModel() {
        val modelClass: Class<*>
        val type = javaClass.genericSuperclass
        modelClass = if (type is ParameterizedType) {
            type.actualTypeArguments[0] as Class<*>
        } else {
            //如果没有指定泛型参数，则默认使用BaseViewModel
            BaseViewModel::class.java
        }
        viewModel = createViewModel(this, modelClass as Class<VM>)
        lifecycle.addObserver(viewModel)
    }

    override fun initViewObservable() {

    }

    override fun initData() {

    }

    override fun initParam() {

    }

    /**
     * Desc:注册ViewModel与View的契约UI回调事件
     *
     *
     * Author: lianyagang
     * Date: 2019-03-13
     */
    protected fun registerUiChangeLiveDataCallBack() {
        /*
         *加载loading Dialog
         */
        viewModel.uc.showDialogEvent.observe(this, { this.showDialog(it) })
        /*
         *加载可取消loading Dialog */
        viewModel.uc.showCancelableDialogEvent.observe(this,
            { this.showDialog(it.title, it.isCancelable, it.isCancelOutsid, it.onCancelListener) })
        /*
         * 加载对话框消失
         */

        viewModel.uc.dismissDialogEvent.observe(this, { dismissDialog() })
        /*
         * 跳入新页面
         */
        viewModel.uc.startActivityEvent.observe(this, { params ->
            val clz = params[BaseViewModel.ParameterField.CLASS] as Class<*>
            val bundle = params[BaseViewModel.ParameterField.BUNDLE] as? Bundle
            startActivity(clz, bundle)
        })
        /*
         * 关闭界面
         */
        viewModel.uc.finishEvent.observe(this, { finish() })
        /*
         *关闭上一层
         */
        viewModel.uc.onBackPressedEvent.observe(this, { onBackPressed() })

        /*
         *右边按钮点击
         */
        viewModel.uc.onRightPressedEvent.observe(this, { onRightPressed() })
        /*
         *右边文字按钮点击
         */
        viewModel.uc.onRightTextPressedEvent.observe(this, { onRightTextOnClick() })
        /*
         * 左边文字点击按钮
         */
        viewModel.uc.onLeftTextPressedEvent.observe(this, { onLeftTextOnClick() })
        /*
         *左边按钮点击
         */
        viewModel.uc.onLeftPressedEvent.observe(this, Observer { onLeftPressed() })
        /*
         *重试按钮点击
         */
        viewModel.uc.onReloadPressedEvent.observe(this, { onReloadPressed() })
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    protected open fun startActivity(clz: Class<*>, bundle: Bundle?) {
        val intent = Intent(this, clz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    /**
     * Desc:显示loading Dialog
     *
     *
     * Author: lianyagang
     * Date: 2019-03-13
     *
     * @param title 显示文案
     */
    protected open fun showDialog(title: String = StringUtils.getStringResource(R.string.common_wait_loading)) {
        if (dialog == null) {
            dialog = LoadingDialogProvider.createLoadingDialog(this, title)
        }
        dialog!!.setCancelable(false)
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.show()
    }

    /**
     * Desc:显示可取消loading Didlog
     *
     *
     * Author: zhushuangmiao
     * Date: 2020-02-14
     *
     * @param title 显示文案
     * @param isCancelable 是否阻塞页面
     * @param isCancelOutsid 是否可以点击外部销毁
     * @param onCancelListener dialog 取消监听
     */
    protected open fun showDialog(
        title: String = StringUtils.getStringResource(R.string.common_wait_loading),
        isCancelable: Boolean,
        isCancelOutsid: Boolean,
        onCancelListener: DialogInterface.OnCancelListener?
    ) {
        if (dialog == null) {
            dialog = LoadingDialogProvider.createLoadingDialog(this, title)
        }
        dialog!!.setCancelable(isCancelable)
        dialog!!.setCanceledOnTouchOutside(isCancelOutsid)
        dialog!!.setOnCancelListener(onCancelListener)
        dialog!!.show()
    }

    protected open fun dismissDialog() {
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
            dialog = null
        }
    }

    /**
     * 创建ViewModel
     *
     * @param cls 类名
     * @param <T> 泛型
     * @return 创建的VM
    </T> */
    protected open fun createViewModel(
        activity: FragmentActivity,
        cls: Class<VM>
    ): VM {
        return ViewModelProvider(
            activity,
            ViewModelProvider.AndroidViewModelFactory(application)
        ).get(cls)
    }

    /**
     * Desc:左边按钮点击
     *
     *
     * Author: lianyagang
     * Date: 2019-04-23
     */
    protected open fun onLeftPressed() {
        finish()
    }

    /**
     * Desc:右边按钮点击
     *
     *
     * Author: lianyagang
     * Date: 2019-04-23
     */
    protected open fun onRightPressed() {
        //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Desc右边文字点击
     *
     *
     * Author: lianyagang
     * Date: 2019-04-23
     */
    protected open fun onRightTextOnClick() {
        //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Desc左边文字点击
     *
     *
     * Author: lianyagang
     * Date: 2019-04-23
     */
    protected open fun onLeftTextOnClick() {
        //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Desc:重新加载
     *
     *
     * Author: lianyagang
     * Date: 2019-04-23
     */
    protected open fun onReloadPressed() {
        //To change body of created functions use File | Settings | File Templates.
    }
}