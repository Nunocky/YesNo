package com.example.yesno.di

import com.example.yesno.api.YesNoDataSource
import com.example.yesno.api.YesNoDataSourceImpl
import com.example.yesno.api.YesNoRepository
import com.example.yesno.api.YesNoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class YesNoModule {
    @Binds
    abstract fun bindYesNoRepository(repository: YesNoRepositoryImpl): YesNoRepository

    @Binds
    abstract fun bindYesNoDataSource(dataSource: YesNoDataSourceImpl): YesNoDataSource
}