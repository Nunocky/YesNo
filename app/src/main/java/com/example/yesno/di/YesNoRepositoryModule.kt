package com.example.yesno.di

import com.example.yesno.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class YesNoRepositoryModule {
    @Binds
    abstract fun bindYesNoRepository(repository: DefaultYesNoRepository): YesNoRepository
}