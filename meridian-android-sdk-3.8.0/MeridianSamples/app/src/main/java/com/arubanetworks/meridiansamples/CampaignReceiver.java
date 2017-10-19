package com.arubanetworks.meridiansamples;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.arubanetworks.meridian.campaigns.CampaignBroadcastReceiver;

import java.util.HashMap;
import java.util.Map;

public class CampaignReceiver extends CampaignBroadcastReceiver {

    @Override
    protected void onReceive(Context context, Intent intent, String title, String message) {

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(title);
        builder.setContentText(message);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setContentIntent(contentIntent);
        builder.setAutoCancel(true);
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify("com.arubanetworks.meridiansamples.CampaignReceiver".hashCode(), builder.build());
    }

    @Override
    protected Map<String, String> getUserInfoForCampaign(Context context, String campaignIdentifier) {
        HashMap<String, String> map = new HashMap<>();
        map.put("UserKey1", "userData1");
        map.put("UserKey2", "userData2");
        map.put("UserKey3", "userData3");
        return map;
    }

    //@Override
    //protected Map<String, String> getPushRegistrationUserInfo(Context context) {
    //    HashMap<String, String> hm = new HashMap<String, String>();
    //    hm.put("TestKey", "TestVal");
    //    return hm;
    //}
}
