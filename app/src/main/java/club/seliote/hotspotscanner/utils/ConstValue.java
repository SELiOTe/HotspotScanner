package club.seliote.hotspotscanner.utils;

public class ConstValue {

    // 热点连接设备存储的文件
    public final static String HOTSPOT_CONNECTED_DEVICES_FILE = "/proc/net/arp";

    // 日志文件的文件名
    public static final String LOG_FILE_NAME = "HotspotScanner.log";
    /**
     * 用户看到的出错的提示
     */
    // 打开日志文件出错
    public static final String ERROR_OPEN_LOG_FILE_1 = "[0x1]日志记录出错";
    // 关闭日志文件出错
    public static final String ERROR_OPEN_LOG_FILE_2 = "[0x2]日志记录出错";
    // 打开日志文件返回null
    public static final String ERROR_OPEN_LOG_FILE_3 = "[0x3]日志记录出错";
    // 向日志文件写入数据出错
    public static final String ERROR_OPEN_LOG_FILE_4 = "[0x4]日志记录出错";

}
