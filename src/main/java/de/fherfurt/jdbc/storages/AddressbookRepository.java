/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package de.fherfurt.jdbc.storages;

import de.fherfurt.jdbc.core.H2Controller;
import de.fherfurt.jdbc.domains.Person;
import de.fherfurt.jdbc.storages.errors.StorageError;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import lombok.extern.slf4j.Slf4j;

/**
 * <h2>AdressbookRepository</h2>
 * <p>
 * {description}
 *
 * @author Michael Rhöse
 * @version 0.0.0.0, 04/25/2021
 */
@Slf4j
public class AddressbookRepository {

    public void savePerson(Person person) throws StorageError {
        String insertSql = "INSERT INTO PERSON (FIRST_NAME, LAST_NAME, EMAIL)\n"
                + "VALUES ('" + person.getFirstName() + "', '" + person.getLastName() + "', '" + person.getEMail() + "')";
        executeStatement(insertSql);
    }

    public Person readPerson() throws StorageError {

        try {

            final Statement statement = H2Controller.getManager().getConnection().createStatement();
            final ResultSet resultSet = statement.executeQuery("select * from PERSON");

            String firstName = "";
            String lastName = "";
            String eMail = "";
            while (resultSet.next()) {
                firstName = resultSet.getString("FIRST_NAME");
                lastName = resultSet.getString("LAST_NAME");
                eMail = resultSet.getString("EMAIL");
            }
            return new Person(firstName, lastName, eMail);

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new StorageError(e.getMessage());
        } finally {
            try {
                H2Controller.getManager().getConnection().close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                throw new StorageError(e.getMessage());
            }
        }
    }

    private void executeStatement(String sqlStatement) throws StorageError {

        try(final Statement statement = H2Controller.getManager().getConnection().createStatement()){
            statement.execute(sqlStatement);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new StorageError(e.getMessage());
        } finally {
            try {
                H2Controller.getManager().getConnection().close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                throw new StorageError(e.getMessage());
            }
        }
    }
}
