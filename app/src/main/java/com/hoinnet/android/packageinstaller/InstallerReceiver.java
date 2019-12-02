package com.hoinnet.android.packageinstaller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class InstallerReceiver extends BroadcastReceiver {
    private final String TAG = "InstallerReceiver";

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(INSTALL_DEFINE.ACTION_SLIENCEINSTALL_REQUEST)) {
            InstallerManager.getInstance(context).install(intent.getStringExtra(INSTALL_DEFINE.KEY_FILENAME));
        } else if (action.equals(INSTALL_DEFINE.ACTION_SLIENCEDELETE_REQUEST)) {
            InstallerManager.getInstance(context).uninstall(intent.getStringExtra(INSTALL_DEFINE.KEY_PACKAGENAME));
        }
    }
}
