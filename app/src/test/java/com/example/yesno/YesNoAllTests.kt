package com.example.yesno

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    YesNoDataSourceTest::class,
    YesNoRepositoryTest::class
)
class YesNoAllTests
