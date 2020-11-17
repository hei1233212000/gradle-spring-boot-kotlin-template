package poc.config

import io.r2dbc.h2.H2ConnectionConfiguration
import io.r2dbc.h2.H2ConnectionFactory
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

/**
 * This is the workaround to run liquibase with R2DBC as the official support is not yet done
 *
 * reference: https://liquibase.jira.com/browse/CORE-3419
 */
@Configuration
class DatabaseConfiguration : AbstractR2dbcConfiguration() {
    private val dbUrl = "mem:testdb;DB_CLOSE_DELAY=-1;"
    private val dbUser = "sa"
    private val dbPassword = ""

    @Bean
    override fun connectionFactory(): ConnectionFactory {
        return H2ConnectionFactory(
            H2ConnectionConfiguration.builder()
                .url(dbUrl)
                .username(dbUser)
                .password(dbPassword)
                .build()
        )
    }

    @Bean
    fun dataSource(): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName("org.h2.Driver")
        dataSource.url = "jdbc:h2:$dbUrl"
        dataSource.username = dbUser
        dataSource.password = dbPassword
        return dataSource
    }
}