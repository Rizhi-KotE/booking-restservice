package rk.repository

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DriverManagerDataSource

import javax.sql.DataSource

@Configuration
class MeetingRepositoryTestConfig {
    @Bean
    DataSource dataSource() {
        def source = new DriverManagerDataSource()
        source.driverClassName = "org.hsqldb.jdbcDriver"
        source.url = "jdbc:hsqldb:mem:paging"
        source.username = "sa"
        source.password = ""
        source
    }

}
