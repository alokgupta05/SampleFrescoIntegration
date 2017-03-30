package com.example.fresco.myapplication.network;

import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashSet;


/**
 * Created by  on 10/17/2016.
 */
public class RetrieveCookieInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();

            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
                Log.d("CookiesInterceptor : ","header:  "+ header);
            }
            /*
            Store in preferences
             */
           // PreferenceHelper.getDefaultPreferences().edit().putStringSet(PreferenceHelper.PREF_BOX_COOKIES, cookies).apply();

        }

        return originalResponse;
    }
}