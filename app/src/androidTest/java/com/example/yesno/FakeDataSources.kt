package com.example.yesno

import com.example.yesno.data.YesNo
import com.example.yesno.repository.YesNoDataSource
import kotlinx.coroutines.runBlocking
import java.io.IOException
import javax.inject.Inject

/**
 *
 */
class FakeYesDataSource @Inject constructor() : YesNoDataSource {
    override suspend fun fetch(force: String?): YesNo {

        runBlocking {
            Thread.sleep(300) // MEMO 300より小さいとうまくいかない。なぜ?
        }

        // こちらでもいいけど時間はこれ以上短くできない
//        withContext(Dispatchers.Main) {
//            delay(300)
//        }

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

        runBlocking {
            Thread.sleep(300) // MEMO 300より小さいとうまくいかない。なぜ?
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

        runBlocking {
            Thread.sleep(300) // MEMO 300より小さいとうまくいかない。なぜ?
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

        runBlocking {
            Thread.sleep(300) // MEMO 300より小さいとうまくいかない。なぜ?
        }

        throw IOException("TEST")
    }
}

