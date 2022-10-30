package com.example.yesno.di

import com.example.yesno.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class YesNoRepositoryModule {
    @Singleton
    @Binds
    abstract fun bindYesNoRepository(repository: DefaultYesNoRepository): YesNoRepository
}