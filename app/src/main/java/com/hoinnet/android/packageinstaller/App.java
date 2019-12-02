package com.hoinnet.android.packageinstaller;

import android.app.Application;
import android.content.Intent;
import com.android.mltcode.watchlib.WatchUtils;

public class App extends Application {
    public void onCreate() {
        super.onCreate();
        WatchUtils.init(this);
        startService(new Intent(this, InstallerService.class));
    }
}
