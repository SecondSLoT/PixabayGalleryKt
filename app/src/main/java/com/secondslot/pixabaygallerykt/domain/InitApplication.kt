package com.secondslot.pixabaygallerykt.domain

import android.app.Application
import com.secondslot.pixabaygallerykt.data.Repository

class InitApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Инициализация репозитория при запуске приложения
        Repository.init(this)
    }
}