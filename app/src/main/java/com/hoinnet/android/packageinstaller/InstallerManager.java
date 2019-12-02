package com.hoinnet.android.packageinstaller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import com.android.mltcode.watchlib.config.Logger;
import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class InstallerManager {
    private static InstallerManager mInstance;
    /* access modifiers changed from: private */
    public final String TAG = InstallerManager.class.getSimpleName();
    private Context mContext;
    /* access modifiers changed from: private */
    public Map<String, String> mPkgSource = new HashMap();
    private PackageManager mPm;

    private InstallerManager(Context context) {
        this.mContext = context;
        Context context2 = this.mContext;
        if (context2 != null) {
            this.mPm = context2.getPackageManager();
        }
    }

    public static InstallerManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new InstallerManager(context);
        }
        return mInstance;
    }

    public void install(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            String str = this.TAG;
            Logger.d(str, "install: " + filePath);
            response(INSTALL_DEFINE.ACTION_INSTALL_RESPONSE_FAIL, (String) null, (String) null, "File is null !");
            return;
        }
        String pkgName = getPacketNameFromApk(filePath);
        if (TextUtils.isEmpty(pkgName)) {
            response(INSTALL_DEFINE.ACTION_INSTALL_RESPONSE_FAIL, (String) null, filePath, "Apk parsing failed");
            return;
        }
        this.mPkgSource.put(pkgName, filePath);
        /*this.mPm.installPackage(Uri.fromFile(new File(filePath)), new PackageInstallObserver() {
            public void onPackageInstalled(String basePackageName, int returnCode, String msg, Bundle extras) {
                InstallerManager.super.onPackageInstalled(basePackageName, returnCode, msg, extras);
                String access$000 = InstallerManager.this.TAG;
                Logger.d(access$000, "onPackageInstalled: basePackageName = " + basePackageName + ", returnCode = " + returnCode + ", msg = " + msg);
                if (returnCode == 1) {
                    InstallerManager installerManager = InstallerManager.this;
                    installerManager.response(INSTALL_DEFINE.ACTION_INSTALL_RESPONSE_SUCC, basePackageName, (String) installerManager.mPkgSource.remove(basePackageName), (String) null);
                    return;
                }
                InstallerManager installerManager2 = InstallerManager.this;
                installerManager2.response(INSTALL_DEFINE.ACTION_INSTALL_RESPONSE_FAIL, basePackageName, (String) installerManager2.mPkgSource.remove(basePackageName), msg);
            }
        }, 2, (String) null);*/
    }

    public void uninstall(String pkgName) {
        if (TextUtils.isEmpty(pkgName)) {
            String str = this.TAG;
            Logger.d(str, "uninstall: " + pkgName);
            response(INSTALL_DEFINE.ACTION_DELETE_RESPONSE_FAIL, pkgName, (String) null, (String) null);
            return;
        }
        /*this.mPm.deletePackage(pkgName, new IPackageDeleteObserver.Stub() {
            public void packageDeleted(String packageName, int returnCode) throws RemoteException {
                String access$000 = InstallerManager.this.TAG;
                Logger.d(access$000, "packageDeleted: packageName = " + packageName + ", returnCode = " + returnCode);
                if (returnCode == 1) {
                    InstallerManager.this.response(INSTALL_DEFINE.ACTION_DELETE_RESPONSE_SUCC, packageName, (String) null, (String) null);
                } else {
                    InstallerManager.this.response(INSTALL_DEFINE.ACTION_DELETE_RESPONSE_FAIL, packageName, (String) null, (String) null);
                }
            }
        }, 2);*/
    }

    public String getPacketNameFromApk(String archiveFilePath) {
        @SuppressLint("WrongConstant") PackageInfo packInfo = this.mPm.getPackageArchiveInfo(archiveFilePath, 1);
        if (packInfo == null) {
            return null;
        }
        return packInfo.packageName;
    }

    /* access modifiers changed from: private */
    public void response(String action, String packageName, String filePath, String failinfo) {
        Intent intent = new Intent(action);
        if (!TextUtils.isEmpty(failinfo)) {
            intent.putExtra(INSTALL_DEFINE.KEY_FAIL_INFO, failinfo);
        }
        if (!TextUtils.isEmpty(packageName)) {
            intent.putExtra(INSTALL_DEFINE.KEY_PACKAGENAME, packageName);
        }
        if (!TextUtils.isEmpty(filePath)) {
            intent.putExtra(INSTALL_DEFINE.KEY_FILENAME, filePath);
        }
        this.mContext.sendBroadcast(intent);
    }
}
