/*
 * Copyright 2021 dev.id
 */
package es.littledavity.testUtils

import es.littledavity.core.providers.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher

class FakeDispatcherProvider : DispatcherProvider {

    private val testDispatcher = TestCoroutineDispatcher()


    override val main: CoroutineDispatcher = testDispatcher
    override val io: CoroutineDispatcher = testDispatcher
    override val computation: CoroutineDispatcher = testDispatcher


}
