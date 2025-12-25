// IVehicleData.aidl
package com.surpasslike.androidlearning;
import com.surpasslike.androidlearning.IVehicleCallback;

interface IVehicleData {
    // 注册回调
    void registerVehicleCallback(IVehicleCallback callback);
     // 注销回调
    void unregisterVehicleCallback(IVehicleCallback callback);
}