package de.fherfurt.jdbc.core;

import de.fherfurt.jdbc.core.errors.SqlException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.h2.jdbcx.JdbcDataSource;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
/**
 * <h2>H2Controller</h2>
 * <p>
 *
 * @author Michael Rhöse
 * @version 0.0.0.0 04/25/2021
 */
@Slf4j
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class H2Controller {

    private static final String DATABASE_NAME = "addressbook";

    private static H2Controller instance;

    private final JdbcDataSource dataSource;

    public static H2Controller getManager() {
        if (Objects.isNull(instance)) {
            instance = new H2Controller(initDatabase());
        }

        return instance;
    }

    private static JdbcDataSource initDatabase() {
        JdbcDataSource dataSource = new JdbcDataSource();
        // see http://www.h2database.com/html/features.html#in_memory_databases
        // because we want to persist our in-memory H2 database as long as our JVM is alive (or tests run)
        // we need to add ;DB_CLOSE_DELAY=-1 here. With that we also have the same behaviour like a 'real' DB
        // and can use multiple JDBC connections with dataSource.getConnection()
        // otherwise every connection would also get a new in-memory database, without the pre-created tables
        // from other connections
        dataSource.setURL("jdbc:h2:mem:" + DATABASE_NAME + ";DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'classpath:scripts/init.sql'");
        return dataSource;
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException ex) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("Some error occured");
            }

            throw new SqlException("Error while creating DB connection.", ex);
        }
    }
}
