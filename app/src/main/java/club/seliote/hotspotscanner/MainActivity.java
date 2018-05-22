package club.seliote.hotspotscanner;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import club.seliote.hotspotscanner.utils.GetHotspotState;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private DeviceInfoAdapter mDeviceInfoAdapter;

    private List<List<String>> mDevicesInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        mSwipeRefreshLayout = (SwipeRefreshLayout) this.findViewById(R.id.swipe_refresh_layout);
        mRecyclerView = (RecyclerView) this.findViewById(R.id.recycler_view);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    if (!GetHotspotState.isHotsoptOpen()) {
                        Snackbar.make(mRecyclerView, "热点未开启", Snackbar.LENGTH_LONG).show();
                        return;
                    }
                    mDevicesInfo = GetHotspotState.getConnectedDevices();
                    // 因为此处实际上是改变了this.mDevicesInfo的引用, Adapter中仍然引用之前的List, 需要手动更新
                    mDeviceInfoAdapter.changeDataSource(mDevicesInfo);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mDeviceInfoAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (Exception exp) {
                    Snackbar.make(mRecyclerView, "刷新热点状态失败", Snackbar.LENGTH_LONG).show();
                } finally {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        try {
            if (!GetHotspotState.isHotsoptOpen()) {
                Snackbar.make(mRecyclerView, "热点未开启", Snackbar.LENGTH_LONG).show();
            } else {
                mDevicesInfo = GetHotspotState.getConnectedDevices();
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                mRecyclerView.setLayoutManager(linearLayoutManager);
                mDeviceInfoAdapter = new DeviceInfoAdapter(mDevicesInfo);
                mRecyclerView.setAdapter(mDeviceInfoAdapter);
            }
        } catch (Exception exp) {
            Snackbar.make(mRecyclerView, "获取热点状态失败", Snackbar.LENGTH_LONG).show();
        }
    }

    private class DeviceInfoViewHolder extends RecyclerView.ViewHolder {

        private TextView mDeviceConnectedStateTextView;
        private TextView mDeviceIpTextView;
        private TextView mDeviceMacTextView;

        public DeviceInfoViewHolder(View itemView) {
            super(itemView);
            mDeviceConnectedStateTextView = (TextView) itemView.findViewById(R.id.device_connected_status);
            mDeviceIpTextView = (TextView) itemView.findViewById(R.id.device_ip);
            mDeviceMacTextView = (TextView) itemView.findViewById(R.id.device_mac);
        }

        public void setInfo(List<String> deviceInfo) {
            mDeviceConnectedStateTextView.setText(deviceInfo.get(2).equalsIgnoreCase("0x2") ? "在线" : "离线");
            mDeviceIpTextView.setText(deviceInfo.get(0).trim());
            mDeviceMacTextView.setText(deviceInfo.get(3).trim().toUpperCase());
        }
    }

    private class DeviceInfoAdapter extends RecyclerView.Adapter<DeviceInfoViewHolder> {

        private List<List<String>> mDevicesInfo = null;

        public DeviceInfoAdapter(List<List<String>> devicesInfo) {
            // 这个并不会和实参引用同一个对象
            mDevicesInfo = devicesInfo;
        }

        @NonNull
        @Override
        public DeviceInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_info_item, parent, false);
            DeviceInfoViewHolder deviceInfoViewHolder = new DeviceInfoViewHolder(view);
            return deviceInfoViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull DeviceInfoViewHolder holder, int position) {
            holder.setInfo(mDevicesInfo.get(position));
        }

        @Override
        public int getItemCount() {
            return mDevicesInfo.size();
        }

        /**
         * 需要注意List是引用类型, 外部更改引用后内部是没有变的, 需要手动更新才行
         * @param devicesInfo, 新的List<List<String>>引用
         */
        public void changeDataSource(List<List<String>> devicesInfo) {
            mDevicesInfo = devicesInfo;
        }
    }
}
