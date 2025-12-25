package com.surpasslike.dashboard;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.surpasslike.androidlearning.IVehicleCallback;
import com.surpasslike.androidlearning.IVehicleData;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "客户端 MainActivity";
    private TextView tvCurrentSpeed;
    private IVehicleData mVehicleDataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvCurrentSpeed = findViewById(R.id.tv_current_speed);

        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.surpasslike.androidlearning", "com.surpasslike.androidlearning.VehicleService"));
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private IVehicleCallback mCallback = new IVehicleCallback.Stub() {
        @Override
        public void onSpeedChanged(int speed) throws RemoteException {
            runOnUiThread(() -> tvCurrentSpeed.setText("当前车速：" + speed + " km/h"));
        }
    };

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "连接成功");
            mVehicleDataService = IVehicleData.Stub.asInterface(service);// 这里的service就是VehicleService里面onBind里面的mBinder
            try {
                mVehicleDataService.registerVehicleCallback(mCallback);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "连接断开");
            mVehicleDataService = null;
        }
    };
}