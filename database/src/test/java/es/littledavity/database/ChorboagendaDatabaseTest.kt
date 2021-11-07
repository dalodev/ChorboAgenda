/*
 * Copyright 2021 dalodev
 */
package es.littledavity.database

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import es.littledavity.database.chorbo.tables.ContactDao
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

internal class ChorboagendaDatabaseTest {

    @Mock
    internal lateinit var chorboagendaDatabase: ChorboagendaDatabase

    @Mock
    internal lateinit var contactDao: ContactDao

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun obtainChorboDao() {
        doReturn(contactDao).whenever(chorboagendaDatabase).contactDao

        assertThat(
            chorboagendaDatabase.contactDao,
            instanceOf(contactDao::class.java)
        )
    }
}
