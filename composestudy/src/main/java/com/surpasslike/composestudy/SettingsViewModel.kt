package com.surpasslike.composestudy

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsViewModel(private val repo: PreferenceRepository) : ViewModel() {

    // 使用mutableStateOf的目的是,这个值一旦变化,Compose就会自动重组相关的UI
    var isDndEnabled by mutableStateOf(false)
        private set //限制只能在ViewModel里面修改,保证安全!

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val saved = repo.getDoNotDisturb()
            withContext(Dispatchers.Main) {
                // 任何对 Compose State（mutableStateOf）的赋值操作，都必须发生在主线程，
                // 因为这会触发 Snapshot 系统的变更通知，
                // 而 Compose 的 UI 重组调度器只监听主线程的快照变化
                isDndEnabled = saved
            }
        }
    }

    fun toggleDnd(newValue: Boolean) {
        // 1. 在主线程启动（viewModelScope 默认是 Main）
        viewModelScope.launch {
            try {
                // 2. 切换到 IO 线程执行存盘，并“挂起”在这里等待结果
                withContext(Dispatchers.IO) {
                    repo.saveDoNotDisturb(newValue)
                }

                // 3. 只有上面那行跑完了（成功了），才会执行这一行
                // 此时协程会自动切回主线程，更新 UI 状态
                isDndEnabled = newValue

            } catch (e: Exception) {
                // 4. 如果存盘挂了，跳到这里，UI 状态保持不变
                // 你可以弹个 Toast 告诉用户“保存失败”
            }
        }
    }

}