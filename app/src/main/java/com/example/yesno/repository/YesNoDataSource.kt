package com.example.yesno.repository

import com.example.yesno.data.YesNo

interface YesNoDataSource {
    suspend fun fetch(force: String? = null): YesNo
}