package com.surpasslike.androidlearning;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class VehicleService extends Service {
    private final String TAG = "VehicleService";
    private IVehicleCallback mCallback;
    private boolean isRunning = true;
    private int mCurrentSpeed = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(() -> {
            while (isRunning) {
                mCurrentSpeed = (int) (Math.random() * 120); // 模拟生成一个随机速度值
                if (mCallback != null) {
                    try {
                        mCallback.onSpeedChanged(mCurrentSpeed);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }

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
            mCallback = callback;
        }

        @Override
        public void unregisterVehicleCallback(IVehicleCallback callback) {
            Log.d(TAG, "unregisterVehicleCallback");
            mCallback = null;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}