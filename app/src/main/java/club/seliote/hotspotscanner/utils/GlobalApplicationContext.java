package club.seliote.hotspotscanner.utils;

import android.app.Application;
import android.content.Context;

import club.seliote.hotspotscanner.exception.GetApplicationContextException;

public class GlobalApplicationContext extends Application {

    // 是的, static 类成员
    private static Context mContext = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
    }

    // 是的, static 类方法
    public static Context getContext() throws GetApplicationContextException {
        if (mContext == null) {
            throw new GetApplicationContextException();
        } else {
            return mContext;
        }
    }
}
