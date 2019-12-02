package com.android.mltcode.watchlib.config;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConfigUtil {
    private static final String PATH = "/Android/";
    static ExecutorService executor;
    private static boolean isWrite = false;
    /* access modifiers changed from: private */
    public static String mRelPath;

    public static void init(Context context, boolean write) {
        isWrite = write;
        if (Environment.getExternalStorageState().equals("mounted")) {
            StringBuffer bf = new StringBuffer();
            bf.append(Environment.getExternalStorageDirectory().getPath());
            bf.append(PATH);
            bf.append(context.getPackageName());
            bf.append("/log/");
            mRelPath = bf.toString();
        }
    }

    public static void LogWirte(String fileName, String cont) {
        LogWirte(fileName, cont, "yyyy-MM-dd");
    }

    public static void LogWriteByHour(String fileName, String cont) {
        LogWirte(fileName, cont, "yyyy-MM-dd-HH");
    }

    public static void LogWirte(final String fileName, final String cont, final String dataFormat) {
        if (isWrite) {
            if (executor == null) {
                executor = Executors.newSingleThreadExecutor();
            }
            executor.execute(new Runnable() {
                public void run() {
                    FileOutputStream stream = null;
                    try {
                        if (!TextUtils.isEmpty(cont)) {
                            SimpleDateFormat sdf0 = new SimpleDateFormat(dataFormat);
                            Date now = new Date();
                            String time0 = sdf0.format(now);
                            File path = new File(ConfigUtil.mRelPath);
                            File file = new File(ConfigUtil.mRelPath + fileName + time0 + ".txt");
                            if (!path.exists()) {
                                path.mkdirs();
                            }
                            if (!file.exists()) {
                                file.createNewFile();
                            }
                            StringBuffer bf = new StringBuffer(new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(now));
                            bf.append("  |  ");
                            bf.append(cont);
                            bf.append("\n");
                            FileOutputStream stream2 = new FileOutputStream(file, true);
                            stream2.write(bf.toString().getBytes());
                            try {
                                stream2.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (stream != null) {
                            try {
                                stream.close();
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                        if (stream != null) {
                            try {
                                stream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Throwable th) {
                        if (stream != null) {
                            try {
                                stream.close();
                            } catch (Exception e4) {
                                e4.printStackTrace();
                            }
                        }
                        throw th;
                    }
                }
            });
        }
    }
}
