package com.example.yesno

import com.example.yesno.data.YesNo
import com.example.yesno.repository.YesNoDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

private const val waitMs = 500L

/**
 *
 */
class FakeYesDataSource @Inject constructor() : YesNoDataSource {
    override suspend fun fetch(force: String?): YesNo {

        withContext(Dispatchers.Main) {
            delay(waitMs)
        }

        return YesNo(
            answer = "yes",
            forced = false,
            image = "https://yesno.wtf/assets/yes/5-64c2804cc48057b94fd0b3eaf323d92c.gif"
        )
    }
}

/**
 *
 */
class FakeNoDataSource @Inject constructor() : YesNoDataSource {
    override suspend fun fetch(force: String?): YesNo {

        withContext(Dispatchers.Main) {
            delay(waitMs)
        }

        return YesNo(
            answer = "no",
            forced = false,
            image = "https://yesno.wtf/assets/no/12-dafd576be23d3768641340f76658ddfe.gif"
        )
    }
}

/**
 *
 */
class FakeMaybeDataSource @Inject constructor() : YesNoDataSource {
    override suspend fun fetch(force: String?): YesNo {

        withContext(Dispatchers.Main) {
            delay(waitMs)
        }

        return YesNo(
            answer = "maybe",
            forced = false,
            image = "https://yesno.wtf/assets/yes/5-64c2804cc48057b94fd0b3eaf323d92c.gif"
        )
    }
}

/**
 *
 */
class FakeNetworkErrorDataSource @Inject constructor() : YesNoDataSource {
    override suspend fun fetch(force: String?): YesNo {

        withContext(Dispatchers.Main) {
            delay(waitMs)
        }

        throw IOException("TEST")
    }
}

