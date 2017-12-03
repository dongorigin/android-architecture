package cn.dong.architecture

import android.app.Application
import com.facebook.stetho.Stetho

/**
 * @author dong on 2017/12/03.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)
    }

}