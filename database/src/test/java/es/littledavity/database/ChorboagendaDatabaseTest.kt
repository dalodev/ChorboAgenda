/*
 * Copyright 2021 dev.id
 */
package es.littledavity.database

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import es.littledavity.database.chorbo.tables.ContactDao
import es.littledavity.testUtils.roboelectric.TestRobolectric
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ChorboagendaDatabaseTest : TestRobolectric() {

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
