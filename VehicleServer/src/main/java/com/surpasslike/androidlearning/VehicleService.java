package com.surpasslike.androidlearning;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

public class VehicleService extends Service {
    private final String TAG = "VehicleService";
    private final RemoteCallbackList<IVehicleCallback> mCallbackList = new RemoteCallbackList<>();
    private boolean isRunning = true;
    private int mCurrentSpeed = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(() -> {
            while (isRunning) {
                mCurrentSpeed = (int) (Math.random() * 120); // 模拟生成一个随机速度值
                // 遍历所有注册过的客户端发送数据
                int count = mCallbackList.beginBroadcast(); // 给当前的列表拍个“快照”，并锁定列表
                /*
                 * 注意: beginBroadcast必须和finishBroadcast成对出现,一个是准备发送数据, 一个是结束发送
                 * */
                for (int i = 0; i < count; i++) {
                    try {
                        mCallbackList.getBroadcastItem(i).onSpeedChanged(mCurrentSpeed); // getBroadcastItem从快照中获取回调对象
                    } catch (RemoteException e) {
                        Log.e(TAG, "回调发送失败: " + e.getMessage());
                    }
                }

                mCallbackList.finishBroadcast(); // 广播结束，解锁列表，处理期间产生的注册/注销请求

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private final IVehicleData.Stub mBinder = new IVehicleData.Stub() {
        @Override
        public void registerVehicleCallback(IVehicleCallback callback) {
            Log.d(TAG, "registerVehicleCallback");
            if (callback != null) {
                mCallbackList.register(callback);
            }
        }

        @Override
        public void unregisterVehicleCallback(IVehicleCallback callback) {
            Log.d(TAG, "unregisterVehicleCallback");
            if (callback != null) {
                mCallbackList.unregister(callback);
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false; // 停止线程
        mCallbackList.kill(); // 释放所有资源
    }
}