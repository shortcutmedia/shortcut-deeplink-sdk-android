package sc.shortcut.sdk;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

class SCUtils {

    static Uri uriStripQueryParameter(Uri uri, String paramKey) {
        String queryParam = uri.getQueryParameter(paramKey);
        if (queryParam == null) {
            // nothing to strip
            return uri;
        } else {
            String uriString = uri.toString();
            String paramString = paramKey + "=" + queryParam;

            if (uri.getQuery().length() == paramString.length()) {
                paramString = "?" + paramString;
            } else if (uriString.length() - paramString.length() == uriString.indexOf(paramString)) {
                paramString = "&" + paramString;
            } else {
                paramString = paramString + "&";
            }
            return Uri.parse(uriString.replace(paramString, ""));
        }
    }

    /**
     * Generates a pseudo-unique ID according to http://www.pocketmagic.net/android-unique-device-id/
     */
    static String generateDeviceId() {
        return "35" + //we make this look like a valid IMEI
                Build.BOARD.length()%10+ Build.BRAND.length()%10 +
                Build.CPU_ABI.length()%10 + Build.DEVICE.length()%10 +
                Build.DISPLAY.length()%10 + Build.HOST.length()%10 +
                Build.ID.length()%10 + Build.MANUFACTURER.length()%10 +
                Build.MODEL.length()%10 + Build.PRODUCT.length()%10 +
                Build.TAGS.length()%10 + Build.TYPE.length()%10 +
                Build.USER.length()%10 ; //13 digits
    }

    /**
     * Returns true if network is available
     */
    static boolean isNetworkAvailable(Context context) {
        // Copied from
        // https://udacity-github-sync-content.s3.amazonaws.com/_imgs/752/1436612063/Screen_Shot_2015-07-11_at_12.53.52.png
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    static boolean hasPermission(Context context, String permission) {
        return (context.getPackageManager().checkPermission(permission, context.getPackageName())
                == PackageManager.PERMISSION_GRANTED);
    }

    static void appendJson(JSONObject json, JSONObject jsonToAppend) {
        if (jsonToAppend == null) return;

        try {
            Iterator<String> it = jsonToAppend.keys();
            while (it.hasNext()) {
                String key = it.next();
                json.put(key, jsonToAppend.get(key));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
