package com.example.yesno.api

import com.example.yesno.data.YesNo
import javax.inject.Inject

// {
//  "answer": "yes",
//  "forced": false,
//  "image": "https://yesno.wtf/assets/yes/2.gif"
//}

interface YesNoRepository {
    suspend fun fetch(): Result<YesNo>
}

class YesNoRepositoryImpl @Inject constructor(
    private val dataSource: YesNoDataSource
) : YesNoRepository {
    override suspend fun fetch(): Result<YesNo> {
        return dataSource.fetch()
    }
}
