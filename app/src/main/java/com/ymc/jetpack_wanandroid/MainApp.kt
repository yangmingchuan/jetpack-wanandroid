package com.ymc.jetpack_wanandroid

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.kingja.loadsir.core.LoadSir
import com.tencent.mmkv.MMKV
import com.ymc.library_base.loadsir.EmptyCallback
import com.ymc.library_base.startup.WidgetManager
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

/**
 * 主App
 */

class MainApp : Application() {

    private val modules = arrayListOf(
        moduleHome, moduleLogin, modulePersonal, moduleProject
    )

    override fun onCreate() {
        super.onCreate()
        initARouter()
        initKoin()
        initMMKV()
    }

    /**
     * 初始化阿里路由框架
     */
    private fun initARouter() {
        if (WidgetManager.isDebug) {
            // 打印日志
            ARouter.openLog()
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug()
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(this)

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    /**
     * 初始化 koin
     */
    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@MainApp)
            modules(modules)
        }
    }

    /**
     * 初始化缓存
     */
    private fun initMMKV() {
        MMKV.initialize(this)
    }

    private fun initLoadSir() {
        LoadSir.beginBuilder()
            .addCallback(ErrorCallback())
            .addCallback(LoadingCallback())
            .addCallback(EmptyCallback())
            .setDefaultCallback(LoadingCallback::class.java)
            .commit()
    }

}