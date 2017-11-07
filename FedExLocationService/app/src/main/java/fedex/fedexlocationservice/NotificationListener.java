package fedex.fedexlocationservice;

import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

public class NotificationListener extends NotificationListenerService {
    private String getNotificationText(StatusBarNotification sbn) {
        return sbn.toString();
    }
}
