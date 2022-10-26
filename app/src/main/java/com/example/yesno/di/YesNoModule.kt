package com.example.yesno.di

import com.example.yesno.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class YesNoModule {
    @Binds
    abstract fun bindYesNoRepository(repository: YesNoRepositoryImpl): YesNoRepository
    //abstract fun bindYesNoRepository(repository: YesNoRepositoryImplYes): YesNoRepository
    //abstract fun bindYesNoRepository(repository: YesNoRepositoryImplNo): YesNoRepository
    //abstract fun bindYesNoRepository(repository: YesNoRepositoryImplMaybe): YesNoRepository
    //abstract fun bindYesNoRepository(repository: YesNoRepositoryImplFail): YesNoRepository
}

/**
 * FOR TEST
 * 必ず yesを返すリポジトリ
 */
//class YesNoRepositoryImplYes @Inject constructor() : YesNoRepository {
//    override suspend fun fetch(force: String?): YesNo {
//        return YesNo(
//            answer = "yes",
//            forced = false,
//            image = "https://yesno.wtf/assets/yes/5-64c2804cc48057b94fd0b3eaf323d92c.gif"
//        )
//    }
//}

/**
 * FOR TEST
 * 必ず noを返すリポジトリ
 */
//class YesNoRepositoryImplNo @Inject constructor() : YesNoRepository {
//    override suspend fun fetch(force: String?): YesNo {
//        return YesNo(
//            answer = "no",
//            forced = false,
//            image = "https://yesno.wtf/assets/no/12-dafd576be23d3768641340f76658ddfe.gif"
//        )
//    }
//}

/**
 * FOR TEST
 * 必ず maybeを返すリポジトリ
 */
//class YesNoRepositoryImplMaybe @Inject constructor() : YesNoRepository {
//    override suspend fun fetch(force: String?): YesNo {
//        return YesNo(
//            answer = "maybe",
//            forced = false,
//            image = "https://yesno.wtf/assets/yes/5-64c2804cc48057b94fd0b3eaf323d92c.gif"
//        )
//    }
//}

/**
 * FOR TEST
 * 必ず例外を発生するリポジトリ
 */
//class YesNoRepositoryImplFail @Inject constructor() : YesNoRepository {
//    override suspend fun fetch(force: String?): YesNo {
//        throw Exception("TEST")
//    }
//}