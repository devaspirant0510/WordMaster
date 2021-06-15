package com.example.wordmaster.Define;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedManger {
    // 셰어드에 저장장할 name 상수로 지정
    public static final String SHARED_MANGER_LOGIN = "LoginInformation";

    public static SharedPreferences mSharePre;

    public SharedManger(){
    }

    public static void init(Context context){
        // 셰어드 프리퍼런스가 널일때 셰어드프리퍼런스 가져옴
        if (mSharePre==null){
            mSharePre = context.getSharedPreferences(SHARED_MANGER_LOGIN,Context.MODE_PRIVATE);
        }
    }
    // int 형 변수 저장
    public static void saveData(String key,int value){
        SharedPreferences.Editor editor = mSharePre.edit();
        editor.putInt(key, value);
        editor.apply();
    }
    // String 형 변수 저장
    public static void savData(String key,String value){
        SharedPreferences.Editor editor = mSharePre.edit();
        editor.putString(key,value);
        editor.apply();
    }
    // boolean 형 변수 저장
    public static void saveData(String key,boolean value){
        SharedPreferences.Editor editor = mSharePre.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }
    // int 형 변수 불러오기
    public static int loadData(String key,int default_){
        return mSharePre.getInt(key,default_);
    }
    // String 형 변수 불러오기
    public static String loadData(String key,String default_){
        return mSharePre.getString(key,default_);
    }
    // boolean 형 변수 불러오기
    public static boolean loadData(String key,boolean default_){
        return mSharePre.getBoolean(key,default_);
    }

    /**
     * 유저이름 저장
     * @param key 키값
     * @param value 들어갈값
     */
    @Deprecated
    public static void saveUserName(String key,String value){
        SharedPreferences.Editor editor = mSharePre.edit();
        editor.putString(key, value);
        editor.apply();
    }
    /**
     * 키값으로 셰어드에 저장된값 가져옴
     * @return key 값으로 해당 값 리턴
     */
    @Deprecated
    public static String  getUserName(){
        return mSharePre.getString(Const.SHARED_USER_NAME,"");
    }
    @Deprecated
    public static void saveUserProfileUri(String key,String value){
        SharedPreferences.Editor editor = mSharePre.edit();
        editor.putString(key, value);
        editor.apply();
    }
    @Deprecated
    public static String  getUserProfileUri(){
        return mSharePre.getString(Const.SHARED_USER_PROFILE_URI,"");
    }
    @Deprecated
    public static void saveUserId(String key, String value){
        SharedPreferences.Editor editor = mSharePre.edit();
        editor.putString(key, value);
        editor.apply();
    }
    @Deprecated
    public static String getUserId(){
        return mSharePre.getString(Const.SHARED_USER_ID,"");
    }
    @Deprecated
    public static void saveUserEmail(String key,String value){
        SharedPreferences.Editor editor = mSharePre.edit();
        editor.putString(key, value);
        editor.apply();
    }
    @Deprecated
    public static String getUserEmail(){
        return mSharePre.getString(Const.SHARED_USER_EMAIL,"");
    }
}
