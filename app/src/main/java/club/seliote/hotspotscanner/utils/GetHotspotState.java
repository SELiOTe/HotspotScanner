package club.seliote.hotspotscanner.utils;

import android.content.Context;
import android.net.wifi.WifiManager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import club.seliote.hotspotscanner.exception.GetApplicationContextException;

/**
 * 用于获取热点状态
 */
public class GetHotspotState {

    /**
     * 获取热点状态
     * @return HOTSPOT_STATE的枚举
     * @throws Exception, 可能是GlobalApplicationContext或从反射中抛出
     */
    public static HOTSPOT_STATE getState() throws Exception {
        // 需要使用getApplicationContext(), 否则将报错
        // Error: The WIFI_SERVICE must be looked up on the Application context or memory will leak on devices < Android N.
        // Try changing  to .getApplicationContext()  [WifiManagerLeak]
        WifiManager wifiManager = (WifiManager) GlobalApplicationContext.getContext()
                .getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);

        // 不允许直接调用, 需要使用反射
        Method method = wifiManager.getClass().getMethod("getWifiApState");
        int stateCode = (Integer) method.invoke(wifiManager);

        // 部分API版本可能会有问题, 在这里处理一下
        if (stateCode > 10) {
            stateCode -= 10;
        }

        return HOTSPOT_STATE.class.getEnumConstants()[stateCode];
    }

    /**
     * 获取热点开启状态
     * @return 已开启返回true, 否则返回false
     * @throws Exception, 任何步骤出错将会抛出Exception
     */
    public static boolean isHotsoptOpen() throws Exception {
        return GetHotspotState.getState() == HOTSPOT_STATE.WIFI_AP_STATE_ENABLED;
    }

    /**
     * 以List<List<String>>形式返回已连接的设备
     * @return 已连接的设备的List<List<String>>形式, 外层的是所有设备列表, 内层的是设备对应的信息列表(分别表示IP address, HW type, Flags, HW address, Mask, Device)
     */
    public static List<List<String>> getConnectedDevices() throws GetApplicationContextException, IOException {
        List<List<String>> connectedDevices = new ArrayList<List<String>>();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(ConstValue.HOTSPOT_CONNECTED_DEVICES_FILE)))) {
            String line = bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                // 以空白符分割
                String[] devicesInfo = line.split("\\s+");
                connectedDevices.add(Arrays.asList(devicesInfo));
            }
        } catch (IOException exp) {
            Logger.e("获取热点连接设备时出错");
            throw exp;
        }
        return  connectedDevices;
    }

}
