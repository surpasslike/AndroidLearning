package com.surpasslike.composestudy

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.surpasslike.composestudy.ui.theme.AndroidLearningTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 初始化仓库和ViewModel
        val repo = PreferenceRepository(this)
        val viewModel = SettingsViewModel(repo)


        // 界面的大门,界面的所有东西都在这里面
        setContent {

            // 试衣间,对应themes.xml里设置的主题,它定义了整个 App 的配色（深色/浅色）、字体样式。
            // 包裹在它里面的所有组件，都会自动继承这些样式。
            AndroidLearningTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var count by remember { mutableIntStateOf(0) }
                    Column(modifier = Modifier.padding(innerPadding)) {

                        // 在这里调用UI的Compose函数
                        SharedPrefsToggle(
                            text = "这里是开启免打扰模式", value = viewModel.isDndEnabled,

                            // 这里是把"对讲机"的频道调好,随时准备呼叫(也就是修改值)
                            onValueChanged = { newValue -> // 这个onValueChanged是接受电话, 收到SharedPrefsToggle里面的onValueChanged后执行.
                                // 2. 收到点击UI的通知(收到电话后),调用ViewModel方法去修改数据
                                viewModel.toggleDnd(newValue)
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun SharedPrefsToggle(
    text: String, // 该选项的名字
    value: Boolean, // 当前的状态:开还是关?
    onValueChanged: (Boolean) -> Unit // 回调函数
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(text, Modifier.weight(1f))

        // 用户点击
        Checkbox(
            checked = value, // 先说好点击之前的开关状态
            onCheckedChange = { newValue -> // 接受者说话了(用户点击了UI按钮),说到底想打开还是关闭
                // 1. 用户点击了, 发出通知
                onValueChanged(newValue) // 真正开始通知开还是关, 在这里拨打电话, 让setContent里面的onValueChanged开始处理
                // 3. 执行完毕,回到这里
            })
    }

}


@Composable
fun ClickCounter(clicks: Int, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("我被点击了 $clicks 次")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
}

@Composable
fun Greeting22(name: String, text: String) {
    Text("hello $name")

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidLearningTheme {
        Greeting("Android")
    }
}