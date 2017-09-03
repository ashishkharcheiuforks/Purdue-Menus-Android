package com.moufee.purduemenus;

import android.app.Application;

import com.moufee.purduemenus.di.AppComponent;
import com.moufee.purduemenus.di.AppModule;
import com.moufee.purduemenus.di.DaggerAppComponent;

/**
 * Created by Ben on 25/08/2017.
 */

public class MenusApp extends Application {

    private AppComponent mAppComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(getApplicationContext())).build();
    }

    public AppComponent getAppComponent(){
        return mAppComponent;
    }
}
