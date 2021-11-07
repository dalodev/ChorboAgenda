/*
 * Copyright 2021 dalodev
 */
package es.littledavity.database

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.platform.app.InstrumentationRegistry
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Provider

@HiltAndroidTest
internal class MigrationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val migrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        ChorboagendaDatabase::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )

    @Inject
    lateinit var databaseProvider: Provider<ChorboagendaDatabase>

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun runAllMigrations() {
        migrationTestHelper.createDatabase(Constants.DATABASE_NAME, 1).close()

        databaseProvider.get().apply {
            openHelper.writableDatabase
            close()
        }
    }
}
