// IVehicleCallback.aidl
package com.surpasslike.androidlearning;

interface IVehicleCallback {
    // 规定服务端如何通知客户端
    void onSpeedChanged(int speed);
}