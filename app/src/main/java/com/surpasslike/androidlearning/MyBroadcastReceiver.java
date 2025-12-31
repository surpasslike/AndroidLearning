package com.surpasslike.androidlearning;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("MyBroadcastReceiver", "系统启动完毕");
        // 延迟几秒再显示，等系统界面准备好
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Toast.makeText(context, "MyBroadcastReceiver 系统启动完成！", Toast.LENGTH_LONG).show();
        }, 5000);  // 延迟 5 秒
    }
}