package club.seliote.hotspotscanner.utils;

/**
 * 热点状态的枚举
 */
public enum HOTSPOT_STATE {

    // 正在关闭
    WIFI_AP_STATE_DISABLING,

    // 已关闭
    WIFI_AP_STATE_DISABLED,

    // 正在开启
    WIFI_AP_STATE_ENABLING,

    // 已开启
    WIFI_AP_STATE_ENABLED,

    //错误状态
    WIFI_AP_STATE_FAILED

}
