package com.surpasslike.androidlearning

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var networkReceiver: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // 创建意图的过滤器,指定接收的广播类型
        // 告诉门铃系统："我只关心网络变化这件事，其他事不用通知我。"
        val filter = IntentFilter()
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE") // 只接收网络广播

        // 门铃响了之后你要做的事——去检查网络状态
        networkReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                checkNetworkStatus()
            }
        }

        // 注册广播接收器,把门铃系统安装并启用。只有注册了，才能收到广播。
        registerReceiver(networkReceiver, filter)

        // 调试按钮，点击触发DebugReceiver调试功能
        val btnDebug = findViewById<Button>(R.id.btn_debug)
        btnDebug.setOnClickListener {
            val theIntent = Intent(DebugReceiver.ACTION).apply {
                putExtra("TYPE", 0)
                putExtra("PA1", 1)
            }
            theIntent.setPackage(packageName)
            sendBroadcast(theIntent)
        }

        /*
        * 语法解释:
        * **apply 是什么？** 一个"作用域函数"，让你对对象连续操作
        * // 用 apply（简洁）
            val intent = Intent(DebugReceiver.ACTION).apply {
                putExtra("TYPE", 0)
                putExtra("PA1", 1)
            }

            // 不用 apply（繁琐）
            val intent = Intent(DebugReceiver.ACTION)
            intent.putExtra("TYPE", 0)
            intent.putExtra("PA1", 1)
        *
        * */
    }

    private fun checkNetworkStatus() {
        // 检查网络状态的逻辑
    }

    override fun onDestroy() {
        super.onDestroy()
        // 取消注册** Activity 销毁时，**拆掉门铃系统**
        unregisterReceiver(networkReceiver)
    }
}