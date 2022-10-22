package com.example.yesno.di

import com.example.yesno.repository.YesNoRepository
import com.example.yesno.repository.YesNoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class YesNoModule {
    @Binds
    abstract fun bindYesNoRepository(repository: YesNoRepositoryImpl): YesNoRepository
    //abstract fun bindYesNoRepository(repository: YesNoRepositoryFailImpl): YesNoRepository
}