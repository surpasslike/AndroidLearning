package com.surpasslike.androidlearning

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 从系统或 Intent 获取真实数据
        val realUserName = "李克"
        setContent {
            Column(Modifier.height(160.dp)) {
                MyFirstComposeUI(realUserName)

                Spacer(Modifier.height(20.dp))

                MyScreen()
            }
        }
    }
}

@Composable
fun MyFirstComposeUI(name: String) {
    Text(text = "你好, $name, 这是我第一个compose画面")
}

@Composable
fun MyScreen() {
    Column {
        Text("My title")
        Button(onClick = {}) {
            Text("Click me")
        }
    }
}

// 这是专门给 IDE 看的预览函数，必须是【无参数】的
@Preview(showBackground = true)
@Composable
fun MyFirstComposePreview() {
    Column {
        // 在这里写死一个名字，专门给预览窗口看
        MyFirstComposeUI("预览用户")
        Spacer(Modifier.height(20.dp))
        MyScreen()
    }
}