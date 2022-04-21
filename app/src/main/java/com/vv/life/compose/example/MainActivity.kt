package com.vv.life.compose.example

import android.app.Application
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.*
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.unit.dp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.vv.life.compose.BaseComposeActivity
import com.vv.life.compose.theme.VvLife_composeTheme
import org.w3c.dom.Text
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        findViewById<View>(R.id.textTouchView).setOnClickListener {
            Log.e("linjiaqiang", "TextTouchView onClick -> ")
            startActivity(Intent(this, MainActivity::class.java))
        }
//        setContent {
//            VvLife_composeTheme(viewModel = viewModel) {
//                MainContent()
//            }
//        }
        while (true) {

        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

}

@Composable
fun MainContent(viewModel: MainViewModel = viewModel()) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            onClick = {
                viewModel.onListClickCommand.execute()
            })
        {
            Text(text = "分页列表")
        }
        Button(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            onClick = {
                viewModel.onPagerClickCommand.execute()
            })
        {
            Text(text = "ViewPager示例(实验性)")
        }
        Button(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            onClick = {
                viewModel.onMixClickCommand.execute()
            })
        {
            Text(text = "Compose嵌套原生View")
        }
    }
}

/**
 * Desc: 预览
 * <p>
 * Author: linjiaqiang
 * Date: 2021/8/6
 */
@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
fun MainContentPreview() {
    MainContent(MainViewModel(Application()))
}