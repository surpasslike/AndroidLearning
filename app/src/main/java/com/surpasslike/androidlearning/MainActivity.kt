package com.surpasslike.androidlearning

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
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