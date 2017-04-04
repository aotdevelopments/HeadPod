package com.siestasystemheadpod.headpodv10;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Michael on 22/01/2017.
 * Esta clase sera usada para recordar el usuario de sesión.
 * //http://www.nosinmiubuntu.com/como-guardar-datos-en-android/
 */

public class SharedPreferSession {

    private final String SHARED_PREFS_FILE = "HMPrefs";
    private final String KEY_EMAIL = "email";
    private final String KEY_PASSWORD ="password";
    private final String KEY_SESSION = "session";
    private Context mContext;

    public SharedPreferSession(Context context){
        mContext = context;
    }

    private SharedPreferences getSettings(){
        return mContext.getSharedPreferences(SHARED_PREFS_FILE, 0);
    }





    public String getUserEmail(){
        return getSettings().getString(KEY_EMAIL, null);
    }


    public String getUserPassword(){
        return getSettings().getString(KEY_PASSWORD, null);
    }

    public void setUserEmail(String email){

        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_EMAIL, email );

        if(editor.commit())
        {
            Log.d("Configuration","setUserEmail ok");
        }

    }

    public void setUserPassword(String password)
    {
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_PASSWORD, password );

        if(editor.commit())
        {
            Log.d("Configuration","setUserEmail ok");
        }
    }

    public void setSession(String session)
    {


        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_SESSION, session );

        if(editor.commit())
        {
            Log.d("Configuration","setSession ok");
        }

    }

    public String getSession(){
        return getSettings().getString(KEY_SESSION, null);
    }


    public void limpiarSession()
    {
        setUserPassword(null);
        setUserEmail(null);
        setSession(null);
    }


}
