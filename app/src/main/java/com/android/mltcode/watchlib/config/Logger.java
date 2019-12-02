package com.android.mltcode.watchlib.config;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

public final class Logger {
    private static final String CONFIG_FILE_NAME = ".cofig.txt";
    private static final String LOG = "log";
    private static final String PATH = "/Android/com.android.mltcode/";
    private static boolean isDebug = false;

    public static void init(Context context) {
        boolean isWriteLog = false;
        Config config = readConfig(context);
        if (config != null) {
            isDebug = config.isDebug;
            isWriteLog = config.isWriteLog;
        }
        ConfigUtil.init(context, isWriteLog);
    }

    public static void d(String tag, String content) {
        if (isDebug) {
            Log.d(tag, content);
        }
        ConfigUtil.LogWirte(LOG, "d  +" + tag + " :" + content);
    }

    public static void i(String tag, String content) {
        if (isDebug) {
            Log.d(tag, content);
        }
        ConfigUtil.LogWirte(LOG, "i  +" + tag + " :" + content);
    }

    public static void e(String tag, String content) {
        if (isDebug) {
            Log.d(tag, content);
        }
        ConfigUtil.LogWirte(LOG, "e  +" + tag + " :" + content);
    }

    public static void w(String tag, String content) {
        if (isDebug) {
            Log.d(tag, content);
        }
        ConfigUtil.LogWirte(LOG, "w  +" + tag + " :" + content);
    }

    @SuppressLint("WrongConstant")
    private static Config readConfig(Context context) {
        String str;
        if (context.getPackageManager().checkPermission("android.permission.WRITE_EXTERNAL_STORAGE", context.getPackageName()) != 0) {
            return null;
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(createFile()));
            StringBuffer sb = new StringBuffer();
            while (true) {
                String readLine = br.readLine();
                String readline = readLine;
                if (readLine == null) {
                    break;
                }
                sb.append(readline);
            }
            br.close();
            str = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            str = null;
        }
        if (TextUtils.isEmpty(str)) {
            return getDefaultConfig();
        }
        try {
            String str2 = new String(Base64.decode(str, 0));
            Config config = new Config();
            JSONObject jo = new JSONObject(str2);
            config.isDebug = jo.optBoolean("isDebug", false);
            config.isWriteLog = jo.optBoolean("isWriteLog", false);
            config.mode = jo.optInt("mode", 1);
            return config;
        } catch (JSONException e2) {
            e2.printStackTrace();
            return getDefaultConfig();
        }
    }

    private static Config getDefaultConfig() {
        Config config = new Config();
        config.isDebug = false;
        config.isWriteLog = false;
        config.mode = 1;
        return config;
    }

    private static File createFile() {
        StringBuffer bf = new StringBuffer();
        bf.append(Environment.getExternalStorageDirectory().getPath());
        bf.append(PATH);
        File fileDir = new File(bf.toString());
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        bf.append(CONFIG_FILE_NAME);
        File file = new File(bf.toString());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return file;
    }
}
