package com.example.yesno

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    MainFragmentTestYes::class,
    MainFragmentTestNo::class,
    MainFragmentTestMaybe::class,
    MainFragmentTestNetworkError::class,
)
class MainFragmentAllTests
