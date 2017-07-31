package com.andfast.app.util;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by mby on 17-7-28.
 */

public class LogUtils {
    private final static int LOG_LENGTH = 3500;
    private static int MAX_DIR_SIZE = 30 * 1024 * 1024; // 30M
    private static int MAX_FILE_SIZE = 10 * 1024 * 1024;// 10M
    private static int MAX_SAVE_DAY = 7;// 7 Day
    private static boolean sDebug = true;// true output log, false not output log
    private static boolean sAutoSave = false;// true auto save log to file
    private static boolean sSaveRuntimeInfo = false;// true auto save process/thread Id to file
    private static final String PATH_DATA_LOGS = "/data/Logs/";
    private static final String PATH_DATA_LOGS_COMMON = PATH_DATA_LOGS + "common/";
    private static volatile String PATH_LOGS_DIR = null;
    private static volatile String ROOT_TAG = "LogUtils";
    private static final String FILE_NAME_SPLIT = ".";
    private static volatile ExecutorService sExecutorService = Executors.newSingleThreadExecutor();

    /**
     * set debug switch.
     *
     * @param debug true output log， false don't output log.
     **/
    public static void setDebug(boolean debug) {
        sDebug = debug;
    }

    /**
     * set autoSave switch.
     *
     * @param save true auto to save log msg.
     **/
    public static void setAutoSave(boolean save) {
        sAutoSave = save;
    }

    /**
     * set save process/thread ID switch.
     *
     * @param save true save process/thread ID to file.
     **/
    public static void setSaveRuntimeInfo(boolean save) {
        sSaveRuntimeInfo = save;
    }

    /**
     * set log root tag.
     *
     * @param rootTag
     **/
    public static void setRootTag(String rootTag) {
        ROOT_TAG = rootTag;
    }

    /**
     * set log path.
     *
     * @param path the absolutePath of log.
     **/
    public static void setLogPath(String path) {
        if (null == path) {
            is(ROOT_TAG, "setLogPath with path null");
            return;
        }

        PATH_LOGS_DIR = path;
        if (!PATH_LOGS_DIR.endsWith(File.separator)) {
            PATH_LOGS_DIR += File.separator;
        }

        chmod777(PATH_LOGS_DIR);
    }

    /**
     * set log path of /data/Logs/common/xxx.
     *
     * @param path the relative path of log.
     **/
    public static void setLogPath2Data(String path) {
        if (null == path) {
            is(ROOT_TAG, "setLogPath2Data with path null");
            return;
        }

        PATH_LOGS_DIR = PATH_DATA_LOGS_COMMON + path;
        if (!PATH_LOGS_DIR.endsWith(File.separator)) {
            PATH_LOGS_DIR += File.separator;
        }

        chmod777(PATH_LOGS_DIR);
    }

    public static String getLogPath() {
        return PATH_LOGS_DIR;
    }

    /**
     * set max size of log dir.
     *
     * @param logDirSize log dir size, unit Byte.
     **/
    public static void setLogDirSize(int logDirSize) {
        if (logDirSize > 0 && logDirSize < MAX_DIR_SIZE) {
            MAX_DIR_SIZE = logDirSize;
            is(ROOT_TAG, "set MAX_DIR_SIZE " + logDirSize);

            if (MAX_FILE_SIZE > logDirSize) {
                MAX_FILE_SIZE = logDirSize;
                is(ROOT_TAG, "set MAX_FILE_SIZE " + logDirSize);
            }
        }
    }

    /**
     * set max size of single log file.
     *
     * @param logFileSize log file size, unit Byte.
     **/
    public static void setLogFileSize(int logFileSize) {
        if (logFileSize > 0 && logFileSize < MAX_FILE_SIZE) {
            MAX_FILE_SIZE = logFileSize;
            is(ROOT_TAG, "set MAX_FILE_SIZE " + logFileSize);
        }
    }

    /**
     * set max day of save log.
     *
     * @param day max save day, unit day.
     **/
    public static void setLogSaveDay(int day) {
        if (day > 0 && day < 7) {
            MAX_SAVE_DAY = day;
            is(ROOT_TAG, "set MAX_SAVE_DAY " + day);
        }
    }


    /**
     * Send a VERBOSE log message.
     *
     * @param tag Used to identify the source of a log message.
     * @param msg The message you would like logged.
     */
    public static void v(String tag, String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }

        if (sDebug) {
            Log.v(ROOT_TAG, tag + ", " + msg);
        }

        if (sAutoSave) {
            log2File(tag, msg);
        }
    }

    /**
     * Send a VERBOSE log message and save to file.
     *
     * @param tag Used to identify the source of a log message.
     * @param msg The message you would like logged.
     */
    public static void vs(String tag, String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }

        if (sDebug) {
            Log.v(ROOT_TAG, tag + ", " + msg);
        }

        log2File(tag, msg);
    }

    /**
     * Send a VERBOSE log message and log the exception.
     *
     * @param tag model.
     * @param msg The message you would like logged.
     * @param tr An exception to log.
     */
    public static void v(String tag, String msg, Throwable tr) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }

        if (sDebug) {
            Log.v(ROOT_TAG, tag + ", " + msg, tr);
        }

        if (sAutoSave) {
            if (null != tr) {
                log2File(tag, msg + "\n" + tr.getMessage());
            } else {
                log2File(tag, msg);
            }
        }
    }

    /**
     * Send a VERBOSE log message and log the exception, save to file.
     *
     * @param tag model.
     * @param msg The message you would like logged.
     * @param tr An exception to log.
     */
    public static void vs(String tag, String msg, Throwable tr) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }

        if (sDebug) {
            Log.v(ROOT_TAG, tag + ", " + msg, tr);
        }

        if (null != tr) {
            log2File(tag, msg + "\n" + tr.getMessage());
        } else {
            log2File(tag, msg);
        }
    }

    /**
     * Send a DEBUG log message.
     *
     * @param tag Used to identify the source of a log message.
     * @param msg The message you would like logged.
     */
    public static void d(String tag, String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }

        if (sDebug) {
            Log.d(ROOT_TAG, tag + ", " + msg);
        }

        if (sAutoSave) {
            log2File(tag, msg);
        }
    }

    /**
     * Send a DEBUG log message and save to file.
     *
     * @param tag Used to identify the source of a log message.
     * @param msg The message you would like logged.
     */
    public static void ds(String tag, String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }

        if (sDebug) {
            Log.d(ROOT_TAG, tag + ", " + msg);
        }

        log2File(tag, msg);
    }

    /**
     * Send a DEBUG log message and log the exception.
     *
     * @param tag Used to identify the source of a log message.
     * @param msg The message you would like logged.
     * @param tr An exception to log.
     */
    public static void d(String tag, String msg, Throwable tr) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }

        if (sDebug) {
            Log.d(ROOT_TAG, tag + ", " + msg, tr);
        }

        if (sAutoSave) {
            if (null != tr) {
                log2File(tag, msg + "\n" + tr.getMessage());
            } else {
                log2File(tag, msg);
            }
        }
    }

    /**
     * Send a DEBUG log message and log the exception, save to file.
     *
     * @param tag Used to identify the source of a log message.
     * @param msg The message you would like logged.
     * @param tr An exception to log.
     */
    public static void ds(String tag, String msg, Throwable tr) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }

        if (sDebug) {
            Log.d(ROOT_TAG, tag + ", " + msg, tr);
        }

        if (null != tr) {
            log2File(tag, msg + "\n" + tr.getMessage());
        } else {
            log2File(tag, msg);
        }
    }

    /**
     * Send an INFO log message.
     *
     * @param tag Used to identify the source of a log message.
     * @param msg The message you would like logged.
     */
    public static void i(String tag, String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }

        if (sDebug) {
            Log.i(ROOT_TAG, tag + ", " + msg);
        }

        if (sAutoSave) {
            log2File(tag, msg);
        }
    }

    /**
     * Send an INFO log message and save to file.
     *
     * @param tag Used to identify the source of a log message.
     * @param msg The message you would like logged.
     */
    public static void is(String tag, String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }

        if (sDebug) {
            Log.i(ROOT_TAG, tag + ", " + msg);
        }

        log2File(tag, msg);
    }

    /**
     * Send an INFO log message and log the exception.
     *
     * @param tag Used to identify the source of a log message.
     * @param msg The message you would like logged.
     * @param tr An exception to log.
     */
    public static void i(String tag, String msg, Throwable tr) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }

        if (sDebug) {
            Log.i(ROOT_TAG, tag + ", " + msg, tr);
        }

        if (sAutoSave) {
            if (null != tr) {
                log2File(tag, msg + "\n" + tr.getMessage());
            } else {
                log2File(tag, msg);
            }
        }
    }

    /**
     * Send an INFO log message and log the exception, save to file.
     *
     * @param tag Used to identify the source of a log message.
     * @param msg The message you would like logged.
     * @param tr An exception to log.
     */
    public static void is(String tag, String msg, Throwable tr) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }

        if (sDebug) {
            Log.i(ROOT_TAG, tag + ", " + msg, tr);
        }

        if (null != tr) {
            log2File(tag, msg + "\n" + tr.getMessage());
        } else {
            log2File(tag, msg);
        }
    }

    /**
     * Send a WARN log message.
     *
     * @param tag Used to identify the source of a log message.
     * @param msg The message you would like logged.
     */
    public static void w(String tag, String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }

        if (sDebug) {
            Log.w(ROOT_TAG, tag + ", " + msg);
        }

        if (sAutoSave) {
            log2File(tag, msg);
        }
    }

    /**
     * Send a WARN log message and save to file.
     *
     * @param tag Used to identify the source of a log message.
     * @param msg The message you would like logged.
     */
    public static void ws(String tag, String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }

        if (sDebug) {
            Log.w(ROOT_TAG, tag + ", " + msg);
        }

        log2File(tag, msg);
    }

    /**
     * Send a WARN log message and log the exception.
     *
     * @param tag Used to identify the source of a log message.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static void w(String tag, String msg, Throwable tr) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }

        if (sDebug) {
            Log.w(ROOT_TAG, tag + ", " + msg, tr);
        }

        if (sAutoSave) {
            if (null != tr) {
                log2File(tag, msg + "\n" + tr.getMessage());
            } else {
                log2File(tag, msg);
            }
        }
    }

    /**
     * Send a WARN log message and log the exception, save to file.
     *
     * @param tag Used to identify the source of a log message.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static void ws(String tag, String msg, Throwable tr) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        if (sDebug) {
            Log.w(ROOT_TAG, tag + ", " + msg, tr);
        }

        if (null != tr) {
            log2File(tag, msg + "\n" + tr.getMessage());
        } else {
            log2File(tag, msg);
        }
    }

    /**
     * Send a WARN log message and log the exception.
     *
     * @param tag Used to identify the source of a log message.
     * @param tr An exception to log
     */
    public static void w(String tag, Throwable tr) {
        if (sDebug) {
            Log.w(ROOT_TAG, tag + ", " + tr);
        }

        if (sAutoSave) {
            if (null != tr) {
                log2File(tag, tr.getMessage());
            }
        }
    }

    /**
     * Send a WARN log message and log the exception, save to file.
     *
     * @param tag Used to identify the source of a log message.
     * @param tr An exception to log
     */
    public static void ws(String tag, Throwable tr) {
        if (sDebug) {
            Log.w(ROOT_TAG, tag + ", " + tr);
        }

        if (null != tr) {
            log2File(tag, tr.getMessage());
        }
    }

    /**
     * Send an ERROR log message.
     *
     * @param tag Used to identify the source of a log message.
     * @param msg The message you would like logged.
     */
    public static void e(String tag, String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }

        if (sDebug) {
            Log.e(ROOT_TAG, tag + ", " + msg);
        }

        if (sAutoSave) {
            log2File(tag, msg);
        }
    }

    /**
     * Send an ERROR log message and save to file.
     *
     * @param tag Used to identify the source of a log message.
     * @param msg The message you would like logged.
     */
    public static void es(String tag, String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }

        if (sDebug) {
            Log.e(ROOT_TAG, tag + ", " + msg);
        }

        log2File(tag, msg);
    }

    /**
     * Send a ERROR log message and log the exception.
     *
     * @param tag Used to identify the source of a log message.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static void e(String tag, String msg, Throwable tr) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }

        if (sDebug) {
            Log.e(ROOT_TAG, tag + ", " + msg, tr);
        }

        if (sAutoSave) {
            if (null != tr) {
                log2File(tag, msg + "\n" + tr.getMessage());
            } else {
                log2File(tag, msg);
            }
        }
    }

    /**
     * Send a ERROR log message and log the exception, save to file.
     *
     * @param tag Used to identify the source of a log message.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static void es(String tag, String msg, Throwable tr) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }

        if (sDebug) {
            Log.e(ROOT_TAG, tag + ", " + msg, tr);
        }

        if (null != tr) {
            log2File(tag, msg + "\n" + tr.getMessage());
        } else {
            log2File(tag, msg);
        }
    }

    public static void s(String tag, String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }

        log2File(tag, msg);
    }

    /**
     * save log to file.
     *
     * @param tag Used to identify the source of a log message.
     * @param msg the message to save.
     **/
    public synchronized static void log2File(final String tag, final String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }

        final StringBuilder stringBuilder = new StringBuilder();
        if (sSaveRuntimeInfo) {
            stringBuilder.append(getRuntimeInfo());
        }
        stringBuilder.append(tag);

        try {
            sExecutorService.execute(new Runnable() {
                @Override
                public void run() {
                    if (-1 == handleLog(tag)) {
                        return;
                    }
                    String logDir = getLogDir();
                    if (null == logDir) {
                        return;
                    }
                    File logDirFile = new File(logDir);
                    if (!logDirFile.exists() || !logDirFile.isDirectory()) {
                        return;
                    }

                    String file = getFilePath(logDir);
                    if (TextUtils.isEmpty(file)) {
                        return;
                    }

                    File f = new File(logDir + file);
                    save2File(stringBuilder.toString(), msg, f);
                }
            });
        } catch (Exception ex) {
            Log.i(ROOT_TAG, "log2File exception " + ex.getMessage());
        }
    }

    /**
     * get the file path to save log.
     *
     * @param dirPath the parent dir of log.
     **/
    private static String getFilePath(String dirPath) {
        if (null == PATH_LOGS_DIR) {
            return null;
        }

        Date d = new Date(System.currentTimeMillis());
        String filePath = formatTime(d.getTime(), "yyyyMMdd");

        File dirFile = new File(dirPath);
        File[] files = dirFile.listFiles();
        List<String> todayLogList = new ArrayList<String>();

        for (File file : files) {
            if (file.getName().contains(filePath)) {
                todayLogList.add(file.getName());
            }
        }

        if (todayLogList.size() == 0) {// not has log current day
            return filePath;
        } else {// has log
            if (todayLogList.size() == 1) {// has one log
                File file = new File(PATH_LOGS_DIR + todayLogList.get(0));
                File newFile = new File(PATH_LOGS_DIR + filePath);

                // if current day has one log but name not match as 20170524. create new file
                if (!file.getName().equals(filePath)) {
                    boolean isSuccess = copyFile(file, newFile);
                    Log.i(ROOT_TAG, "copy file " + file.getAbsolutePath() + " destFile " +
                            newFile.getAbsolutePath() + " return " + isSuccess);
                    if (isSuccess) {
                        file.delete();
                    } else {
                        newFile = file;
                    }
                }

                // if file large than MAX_FILE_SIZE, return a new file
                if (newFile.length() >= MAX_FILE_SIZE) {
                    Log.i(ROOT_TAG, "file size limit, create new file");
                    return filePath + FILE_NAME_SPLIT + "01";
                }
            } else {// has more than one log
                // sort log file from small to large
                Collections.sort(todayLogList, new Comparator<String>() {
                    @Override
                    public int compare(String lhs, String rhs) {
                        return lhs.compareTo(rhs);
                    }
                });

                // more than one log , reName as 20170524 2017052401 2017052402...
                for (int i = 0; i < todayLogList.size(); i++) {
                    String oldFilePath = todayLogList.get(i);
                    String newFilePath;
                    if (i == 0) {
                        newFilePath = filePath;
                    } else {
                        newFilePath = filePath + FILE_NAME_SPLIT + String.format("%02d", i);
                    }

                    File oldFile = new File(PATH_LOGS_DIR + oldFilePath);
                    File newFile = new File(PATH_LOGS_DIR + newFilePath);

                    if (!oldFilePath.equals(newFilePath)) {
                        boolean isSuccess = copyFile(oldFile, newFile);
                        Log.i(ROOT_TAG, "copy file " + oldFile.getAbsolutePath() + " destFile "
                                + newFile.getAbsolutePath() + " return " + isSuccess);
                        if (isSuccess) {
                            oldFile.delete();
                        } else {
                            newFile = oldFile;
                        }
                    }

                    // return file path for save
                    if (todayLogList.size() - 1 == i) {
                        if (newFile.length() >= MAX_FILE_SIZE) {
                            return filePath + FILE_NAME_SPLIT + String.format("%02d", i + 1);
                        } else {
                            return newFilePath;
                        }
                    }
                }
            }
        }

        return filePath;
    }

    /**
     * save message to file.
     *
     * @param tag Used to identify the source of a log message.
     * @param msg the message to save.
     * @param file the file to save.
     **/
    private static void save2File(String tag, String msg, File file) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }

        FileWriter writer = null;
        try {
            if (!file.exists()) {
                boolean isCreated = file.createNewFile();
                if (isCreated) {
                    chmod777(file.getPath());
                } else {
                    Log.i(ROOT_TAG, tag + ", " + "createNewFile() failed:" + file.getPath());
                    return;
                }
            }
            writer = new FileWriter(file, true);
            writer.write(formatTime(System.currentTimeMillis(), "yyyyMMdd HH:mm:ss.SSS") + "  " + tag
                    + ", " + msg + "\n");
            writer.flush();
        } catch (IOException e) {
            Log.e(ROOT_TAG, tag + ", " + "", e);
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                } catch (IOException e) {
                    Log.e(ROOT_TAG, tag + ", " + "", e);
                }
            }
        }
    }

    /**
     * pre process the log dir.
     *
     * @param tag Used to identify the source of a log message.
     **/
    private static int handleLog(String tag) {
        String logDir = getLogDir();
        if (null == logDir) {
            return 0;
        }
        File f = new File(logDir);
        if (!f.exists()) {
            return 0;
        }
        if (!f.isDirectory()) {
            f.delete();
            f.mkdirs();
        }
        if (!f.exists() || !f.isDirectory()) {
            return 0;
        }

        // 1.date format, if not match, delete it. correct example 20170524 20170524.01 20170524.99
        // 2.if file is old than current time Subtract the max save day, delete it
        File[] files = f.listFiles();
        for (File file : files) {
            String name = file.getName();
            if (null == name || !(8 == name.length() || 11 == name.length())) {
                file.delete();
                continue;
            }
            Date date = parseTime(tag, name.substring(0, 8), "yyyyMMdd");
            if (null != date) {
                long time = date.getTime();
                if (0 == time || System.currentTimeMillis() - time >= MAX_SAVE_DAY * 24 * 60 * 60 * 1000) {
                    file.delete();
                }
            } else {
                file.delete();
            }
        }
        // dir size limit, if log dir size > MAX_DIR_SIZE, try to delete old file
        long dirSize = getDirSize(f);
        while (dirSize > (MAX_DIR_SIZE / 1024 / 1024)) {
            files = f.listFiles();
            try {
                int index = 0;
                String tmpFileName = files[0].getName();
                for (int i = 0; i < files.length; i++) {
                    String tmpFileName1 = files[i].getName();
                    if (tmpFileName.compareTo(tmpFileName1) > 0) {
                        tmpFileName = tmpFileName1;
                        index = i;
                    }
                }
                if (files[index].exists()) {
                    Log.i(ROOT_TAG, tag + ", " + "log dir is too large and delete old files:"
                            + files[index].getAbsolutePath());
                    boolean success = files[index].delete();
                    Log.i(ROOT_TAG, tag + ", " + "delete " + success);
                    if (!success) {
                        return -1;
                    }
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            dirSize = getDirSize(f);
        }
        return 0;
    }

    /**
     * get file size.
     *
     * @param file file to get size.
     * @return size of file, unit MB.
     **/
    private static long getDirSize(File file) {
        if (file == null || !file.exists()) {
            return 0;
        }

        long size = 0;
        try {
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                for (File f : children)
                    size += getDirSize(f);
            } else {
                size = file.length() / 1024 / 1024;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.i(ROOT_TAG, "ArrayIndexOutOfBoundsException: " + e.getMessage());
        } catch (Exception e) {
            Log.i(ROOT_TAG, "Exception: " + e.getMessage());
        }

        return size;
    }

    /**
     * get log dir.
     *
     * @return path of log dir.
     **/
    private static String getLogDir() {
        if (null == PATH_LOGS_DIR) {
            return null;
        }

        String path = PATH_LOGS_DIR;
        File pathFile = new File(path);
        boolean isNewCreated = false;
        if (!pathFile.exists()) {
            pathFile.mkdirs();
            isNewCreated = true;
        }

        if (pathFile.exists() && !pathFile.isDirectory()) {
            pathFile.delete();
            pathFile.mkdirs();
            isNewCreated = true;
        }

        if (!pathFile.exists() || !pathFile.isDirectory()) {
            path = null;
        } else if (isNewCreated) {
            chmod777(PATH_DATA_LOGS);
            chmod777(PATH_DATA_LOGS_COMMON);
            chmod777(PATH_LOGS_DIR);
        }
        return path;
    }

    /**
     * format time.
     *
     * @param time time need to be format.
     * @param format time format.
     * @return time string after format.
     **/
    private static String formatTime(long time, String format) {
        return new SimpleDateFormat(format, Locale.getDefault()).format(new Date(time));
    }

    /**
     * get date from time string.
     *
     * @param tag Used to identify the source of a log message.
     * @param time time string.
     * @param template time string format.
     * @return date after format.
     **/
    private static Date parseTime(String tag, String time, String template) {
        SimpleDateFormat format = new SimpleDateFormat(template, Locale.getDefault());
        try {
            return format.parse(time);
        } catch (ParseException e) {
            Log.e(ROOT_TAG, tag + ", " + "ParseException: " + e.getMessage(), e);
        }
        return null;
    }

    /**
     * chmod of file.
     *
     * @param path the file path need to chmod.
     **/
    private static void chmod777(String path) {
        Log.d(ROOT_TAG, "chmod -R 777 " + path);
        Process proc = null;
        BufferedReader in = null;
        BufferedReader err = null;
        PrintWriter out = null;
        try {
            proc = Runtime.getRuntime().exec("sh");
            if (proc != null) {
                in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                err = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                        proc.getOutputStream())), true);
                out.println("chmod -R 777 " + path);
                out.println("exit");
            }
        } catch (IOException e) {
            Log.e(ROOT_TAG, "chmod() IOException: " + e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (err != null) {
                try {
                    err.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                out.close();
            }
            if (proc != null) {
                try {
                    int exitValue = proc.exitValue();
                    Log.d(ROOT_TAG, "chmod() exitValue :" + exitValue);
                } catch (IllegalThreadStateException e) {
                }
                proc = null;
            }
        }
    }

    /**
     * copy file.
     *
     * @param resFile source file.
     * @param desFile destFile.
     */
    private static boolean copyFile(File resFile, File desFile) {
        FileChannel inc = null;
        FileChannel out = null;
        RandomAccessFile fos = null;
        FileInputStream in = null;
        boolean isSuccess = false;
        try {
            if (!resFile.exists()) {
                Log.i(ROOT_TAG, "resFile not exist" + resFile.getAbsolutePath());
                return false;
            }
            if (desFile.exists()) {
                desFile.delete();
            }
            desFile.createNewFile();
            chmod777(desFile.getAbsolutePath());

            fos = new RandomAccessFile(desFile, "rw");
            in = new FileInputStream(resFile);
            inc = in.getChannel();
            out = fos.getChannel();

            ByteBuffer bb = ByteBuffer.allocate(1024 * 100);
            int read = 0;
            int cursum = 0;
            while ((read = inc.read(bb)) != -1) {
                bb.flip();
                out.write(bb);
                bb.clear();
                cursum += read;
                if (cursum >= 1024 * 128) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    cursum = 0;
                }
            }
            if (fos.getFD().valid()) {
                fos.getFD().sync();
            }
            isSuccess = true;
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            Log.i(ROOT_TAG, "FileNotFoundException" + fileNotFoundException.getMessage());
        } catch (IOException ioException) {
            ioException.printStackTrace();
            Log.i(ROOT_TAG, "IOException" + ioException.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
            if (inc != null) {
                try {
                    inc.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return isSuccess;
    }

    /**
     * get runtime Info.
     */
    private static String getRuntimeInfo() {
        return getProcessId() + "/" + getThreadId() + " ";
    }

    /**
     * get current process Id.
     *
     * @return processId.
     */
    private static int getProcessId() {
        return android.os.Process.myPid();
    }

    /**
     * get current thread Id.
     *
     * @return threadId.
     */
    private static long getThreadId() {
        return Thread.currentThread().getId();
    }
}
