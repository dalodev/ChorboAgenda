/*
 * Copyright 2021 dalodev
 */
package es.littledavity.database

import es.littledavity.database.migrations.MIGRATION_1_2
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ChorboagendaDatabaseMigrationsTest {

    @Test
    fun migration1to2() {
        val migration = MIGRATION_1_2
        assertThat(migration.startVersion).isEqualTo(1)
        assertThat(migration.endVersion).isEqualTo(2)
    }
}
