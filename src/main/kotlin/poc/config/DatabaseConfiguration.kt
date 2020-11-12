package poc.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.r2dbc.core.DatabaseClient

@Configuration
class DatabaseConfiguration {
    @Autowired
    private lateinit var databaseClient: DatabaseClient

    /**
     * The ideal handling should use DB migration tool (e.g. Liquibase)
     * However, the integration of Liquibase and R2DBC is not yet done
     *
     * reference: https://liquibase.jira.com/browse/CORE-3419
     */
    @EventListener
    fun onApplicationStartup(event: ContextRefreshedEvent) {
        val statements = listOf(
            "DROP TABLE IF EXISTS FOOD;",
            "CREATE TABLE FOOD (ID bigint auto_increment, NAME VARCHAR(100) NOT NULL);",
            "INSERT INTO FOOD (NAME) VALUES ('Apple')"
        )
        statements.forEach { it: String? ->
            databaseClient.sql(it!!)
                .fetch()
                .rowsUpdated()
                .block()
        }
    }
}