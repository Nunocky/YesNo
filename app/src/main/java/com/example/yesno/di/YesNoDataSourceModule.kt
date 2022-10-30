package com.example.yesno.di

import com.example.yesno.repository.DefaultYesNoDataSource
import com.example.yesno.repository.YesNoDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class YesNoDataSourceModule {
    @Singleton
    @Binds
    abstract fun bindYesNoDataSource(dataSource: DefaultYesNoDataSource): YesNoDataSource
}