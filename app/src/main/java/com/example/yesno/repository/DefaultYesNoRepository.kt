package com.example.yesno.repository

import com.example.yesno.data.YesNo
import javax.inject.Inject

class DefaultYesNoRepository @Inject constructor(
    private val dataSource: YesNoDataSource
) : YesNoRepository {
    override suspend fun fetch(force: String?): Result<YesNo> = runCatching {
        dataSource.fetch(force)
    }
        .onSuccess {
            Result.success(it)
        }
        .onFailure {
            Result.failure<Throwable>(it)
        }
}