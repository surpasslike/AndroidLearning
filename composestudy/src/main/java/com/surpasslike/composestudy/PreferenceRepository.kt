package com.surpasslike.composestudy

import android.content.Context

class PreferenceRepository(context: Context) {

    private val DND_KEY = "dnd_key"

    private val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    // 读取数据
    fun getDoNotDisturb(): Boolean = prefs.getBoolean(DND_KEY, false) // 如果不存在则返回默认值false

    // 保存数据
    fun saveDoNotDisturb(value: Boolean) {
        prefs.edit().putBoolean("dnd_key", value).apply() // apply是异步,commit是同步
    }

}