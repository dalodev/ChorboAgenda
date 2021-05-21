/*
 * Copyright 2020 littledavity
 */
package es.littledavity.database

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import es.littledavity.database.chorbo.tables.ChorboDao
import es.littledavity.testUtils.roboelectric.TestRobolectric
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
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
        MockitoAnnotations.openMocks(this)
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
