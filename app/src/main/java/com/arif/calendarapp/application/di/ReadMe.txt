package com.arif.calendarapp.application.di

/**
 * In this package, we can create a module which will provide the necessary objects related of
 * application layer. (Not able to do it for now due to some android studio issue)
 *
 * Example:

 @Module
 @InstallIn(SingletonComponent::class.java)
 object AppModule {

    @Provides
    @Singleton
    fun provideObject(): T {
        return T()
    }
 }
 */