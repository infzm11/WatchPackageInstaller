package com.hoinnet.android.packageinstaller;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;

public class InstallerService extends Service {
    private InstallerReceiver mInstallReceiver = new InstallerReceiver();

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(INSTALL_DEFINE.ACTION_SLIENCEINSTALL_REQUEST);
            filter.addAction(INSTALL_DEFINE.ACTION_SLIENCEDELETE_REQUEST);
            registerReceiver(this.mInstallReceiver, filter);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.mInstallReceiver);
    }
}
