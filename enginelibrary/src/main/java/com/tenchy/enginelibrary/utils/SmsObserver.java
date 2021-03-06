package com.tenchy.enginelibrary.utils;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zcc on 2015/12/8.
 */
public class SmsObserver extends ContentObserver {

    public static final int MSG_RECEIVED_CODE = 1;

    private Context mContext;
    private Handler mHandler;

    public SmsObserver(Context context, Handler handler) {
        super(handler);
        mContext = context;
        mHandler = handler;
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);

        Log.e("DEBUG", "SMS has changed!");
        Log.e("DEBUG", uri.toString());

        String code = "";

        if (uri.toString().equals("content://sms/raw")) {
            return;
        }

        Uri inboxUri = Uri.parse("content://sms/inbox");
        Cursor c = mContext.getContentResolver().query(inboxUri, null, null, null, "date desc");
        if (c != null) {
            if (c.moveToFirst()) {
                String address = c.getString(c.getColumnIndex("address"));
                String body = c.getString(c.getColumnIndex("body"));

                if (!address.equals("15555215556")) {
                    return;
                }

                Log.e("DEBUG", "发件人为：" + address + " " + "短信内容为：" + body);

                Pattern pattern = Pattern.compile("(\\d{6})");
                Matcher matcher = pattern.matcher(body);

                if (matcher.find()) {
                    code = matcher.group(0);
                    Log.e("DEBUG", "code is " + code);

                    mHandler.obtainMessage(MSG_RECEIVED_CODE, code).sendToTarget();
                }

            }
            c.close();
        }

    }
}
