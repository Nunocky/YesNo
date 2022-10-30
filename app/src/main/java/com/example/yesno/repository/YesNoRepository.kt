package com.example.yesno.repository

import com.example.yesno.data.YesNo

interface YesNoRepository {
    suspend fun fetch(force: String? = null): Result<YesNo>
}