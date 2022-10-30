package com.example.yesno.di

import com.example.yesno.repository.DefaultYesNoDataSource
import com.example.yesno.repository.YesNoDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class YesNoDataSourceModule {
    @Binds
    abstract fun bindYesNoDataSource(dataSource: DefaultYesNoDataSource): YesNoDataSource
}