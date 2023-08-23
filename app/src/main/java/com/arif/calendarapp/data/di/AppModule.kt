package com.arif.calendarapp.data.di

import com.arif.calendarapp.common.Constants
import com.arif.calendarapp.data.remote.CalendarTaskApi
import com.arif.calendarapp.data.repositories.CalendarTaskRepositoryImpl
import com.arif.calendarapp.domain.repositories.CalendarTaskRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppModule {
    private var API_INSTANCE: CalendarTaskApi? = null
    private var REPO_INSTANCE: CalendarTaskRepository? = null

    fun provideCalendarTaskApi(): CalendarTaskApi {
        if (API_INSTANCE == null) {
            API_INSTANCE = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CalendarTaskApi::class.java)
        }
        return API_INSTANCE!!
    }

    fun provideCalendarTaskRepository(): CalendarTaskRepository {
        if (REPO_INSTANCE == null) {
            REPO_INSTANCE = CalendarTaskRepositoryImpl(provideCalendarTaskApi())
        }
        return REPO_INSTANCE!!
    }
}

/**
 * Notes: Tried to provide object using Hilt but faced some android studio specific issue,
 * hence not able to implement Hilt and implemented it Singleton.
 */