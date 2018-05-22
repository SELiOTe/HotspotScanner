package club.seliote.hotspotscanner.utils;

/**
 * 作者: 李阳帝
 * 联系方式: 13227753101
 * 日期: 2018/04/29
 */

import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

import club.seliote.hotspotscanner.exception.GetApplicationContextException;
/**
 * 日志类, 文件存储位置在软件SD卡缓存目录下, 文件写入时加锁会有莫名其妙的问题, 不加锁测试时也并未造成重入
 */
public class Logger {

    private static SimpleDateFormat sSimpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");

    /**
     * 写入INFO级别日志
     * @param msg, 日志内容
     */
    public static void i(String msg) throws GetApplicationContextException {
        RandomAccessFile randomAccessFile = Logger.openLogFile();
        if (randomAccessFile == null) {
            Toast.makeText(GlobalApplicationContext.getContext(), ConstValue.ERROR_OPEN_LOG_FILE_3, Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            randomAccessFile.writeUTF(Logger.getTime() + " [ INFO]: " + msg + "\n");
        } catch (IOException exp) {
            Toast.makeText(GlobalApplicationContext.getContext(), ConstValue.ERROR_OPEN_LOG_FILE_4, Toast.LENGTH_SHORT).show();
        }
        Logger.closeLogFile(randomAccessFile);
    }

    /**
     * 写入WARNING级别日志
     * @param msg, 日志内容
     */
    public static void w(String msg) throws GetApplicationContextException {
        RandomAccessFile randomAccessFile = Logger.openLogFile();
        if (randomAccessFile == null) {
            Toast.makeText(GlobalApplicationContext.getContext(), ConstValue.ERROR_OPEN_LOG_FILE_3, Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            randomAccessFile.writeUTF(Logger.getTime() + " [ WARN]: " + msg + "\n");
        } catch (IOException exp) {
            Toast.makeText(GlobalApplicationContext.getContext(), ConstValue.ERROR_OPEN_LOG_FILE_4, Toast.LENGTH_SHORT).show();
        }
        Logger.closeLogFile(randomAccessFile);
    }

    /**
     * 写入ERROR级别日志
     * @param msg, 日志内容
     */
    public static void e(String msg) throws GetApplicationContextException {
        RandomAccessFile randomAccessFile = Logger.openLogFile();
        if (randomAccessFile == null) {
            Toast.makeText(GlobalApplicationContext.getContext(), ConstValue.ERROR_OPEN_LOG_FILE_3, Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            randomAccessFile.writeUTF(Logger.getTime() + " [ERROR]: " + msg + "\n");
        } catch (IOException exp) {
            Toast.makeText(GlobalApplicationContext.getContext(), ConstValue.ERROR_OPEN_LOG_FILE_4, Toast.LENGTH_SHORT).show();
        }
        Logger.closeLogFile(randomAccessFile);
    }

    /**
     * 打开日至文件
     * @return 成功返回RandomAccessFile对象, 失败返回null
     */
    private static RandomAccessFile openLogFile() throws GetApplicationContextException {
        RandomAccessFile randomAccessFile = null;
        File file = new File(GlobalApplicationContext.getContext().getExternalCacheDir(), ConstValue.LOG_FILE_NAME);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            // 模式必须是"rw", 只是"w"的话会抛出Exception
            randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(randomAccessFile.length());
        } catch (IOException exp) {
            Toast.makeText(GlobalApplicationContext.getContext(), ConstValue.ERROR_OPEN_LOG_FILE_1, Toast.LENGTH_SHORT).show();
            return null;
        }
        return randomAccessFile;
    }

    /**
     * 关闭日志文件
     * @param randomAccessFile, 需要关闭的RandomAccessFile对象
     */
    private static void closeLogFile(RandomAccessFile randomAccessFile) throws GetApplicationContextException {
        try {
            if (randomAccessFile != null) {
                randomAccessFile.close();
            }
        } catch (IOException exp) {
            Toast.makeText(GlobalApplicationContext.getContext(), ConstValue.ERROR_OPEN_LOG_FILE_2, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取时间的字符串表示
     * @return 时间的字符串表示
     */
    private static String getTime() {
        return sSimpleDateFormat.format(new Date());
    }

}
