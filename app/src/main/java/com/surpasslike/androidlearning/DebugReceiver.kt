package com.surpasslike.androidlearning

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class DebugReceiver : BroadcastReceiver() {

    companion object {
        const val TAG = "DebugReceiver"
        const val ACTION = "com.surpasslike.androidlearning.debug" // 代码内发送广播会用到. 这里是静态注册了因此没调用它
    }

    /*
    # 示例：清除缓存（TYPE=0, PA1=0）
    am Activity Manager, broadcast发送广播, -a指定 Action  , -n指定组件（包名/类名）, --eiExtra Integer
    adb shell am broadcast -a com.surpasslike.androidlearning.debug -n com.surpasslike.androidlearning/.DebugReceiver --ei TYPE 0 --ei PA1 0
    * */

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            val type = it.getIntExtra("TYPE", -1)
            val pa1 = it.getIntExtra("PA1", -1)
            val pa2 = it.getIntExtra("PA2", -1)

            Log.d(TAG, "收到调试广播 - TYPE: $type, PA1: $pa1, PA2: $pa2")

            when (type) {
                0 -> handleCache(context, pa1, pa2)      // 缓存相关
                1 -> handleNetwork(context, pa1, pa2)    // 网络相关
                2 -> handleUI(context, pa1, pa2)         // UI 相关
                3 -> handleUser(context, pa1, pa2)       // 用户相关
                else -> Log.w(TAG, "未知 TYPE: $type")
            }
        }
    }

    // TYPE = 0：缓存相关
    private fun handleCache(context: Context?, pa1: Int, pa2: Int) {
        when (pa1) {
            0 -> {
                // 清除缓存
                context?.cacheDir?.deleteRecursively()
                showToast(context, "缓存已清除")
            }

            1 -> {
                // 显示缓存大小
                val size = context?.cacheDir?.length() ?: 0
                showToast(context, "缓存大小: $size bytes")
            }
        }
    }

    // TYPE = 1：网络相关
    private fun handleNetwork(context: Context?, pa1: Int, pa2: Int) {
        when (pa1) {
            0 -> {
                // 切换服务器环境
                val env = when (pa2) {
                    0 -> "开发环境"
                    1 -> "测试环境"
                    2 -> "生产环境"
                    else -> "未知"
                }
                showToast(context, "切换到: $env")
            }

            1 -> {
                // 模拟网络延迟
                showToast(context, "设置网络延迟: ${pa2}ms")
            }
        }
    }

    // TYPE = 2：UI 相关
    private fun handleUI(context: Context?, pa1: Int, pa2: Int) {
        when (pa1) {
            0 -> showToast(context, "显示调试悬浮窗")
            1 -> showToast(context, "隐藏调试悬浮窗")
        }
    }

    // TYPE = 3：用户相关
    private fun handleUser(context: Context?, pa1: Int, pa2: Int) {
        when (pa1) {
            0 -> showToast(context, "模拟登录用户: $pa2")
            1 -> showToast(context, "退出登录")
        }
    }

    private fun showToast(context: Context?, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        Log.d(TAG, msg)
    }
}