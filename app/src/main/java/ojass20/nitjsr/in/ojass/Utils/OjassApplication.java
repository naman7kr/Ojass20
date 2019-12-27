package ojass20.nitjsr.in.ojass.Utils;

import android.app.Application;

import com.onesignal.OneSignal;

public class OjassApplication extends Application {
    private static OjassApplication mInstance;
    public OjassApplication(){
        mInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }
    public static synchronized OjassApplication getInstance(){
        return mInstance;
    }
}
