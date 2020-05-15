/*
 * Copyright 2020 littledavity
 */
package es.littledavity.core.database

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import es.littledavity.core.database.chorbo.ChorboDao
import es.littledavity.test_utils.roboelectric.TestRobolectric
import org.hamcrest.Matchers.instanceOf
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ChorboagendaDatabaseTest : TestRobolectric() {

    @Mock
    lateinit var chorboagendaDatabase: ChorboagendaDatabase
    @Mock
    lateinit var chorboDao: ChorboDao

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun obtainChorboDao() {
        doReturn(chorboDao).whenever(chorboagendaDatabase).chorboDao()

        assertThat(
            chorboagendaDatabase.chorboDao(),
            instanceOf(chorboDao::class.java)
        )
    }
}
