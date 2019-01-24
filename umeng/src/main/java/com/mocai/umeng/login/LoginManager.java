package com.mocai.umeng.login;

import com.mocai.umeng.share.ShareManager;

/**
 * @author Li Xiaopeng
 * @date 2018/12/21
 */
public class LoginManager {

    private static LoginManager INSTANCE;

    public static LoginManager getInstance(){
        if (INSTANCE==null){
            synchronized (LoginManager.class){
                if (INSTANCE==null){
                    INSTANCE = new LoginManager();
                }
            }
        }
        return INSTANCE;
    }

}
