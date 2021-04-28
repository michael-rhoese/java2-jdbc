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
package de.fherfurt.jdbc.domains;

import lombok.Data;

/**
 * <h2>Person</h2>
 * <p>
 *
 * @author Michael Rhöse
 * @version 0.0.0.0, 04/25/2021
 */
@Data
public class Person implements Comparable<Person> {

    public static final String TABLE_NAME = "PERSON";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_LASTNAME = "LASTNAME";
    public static final String COLUMN_FIRSTNAME = "FIRSTNAME";
    public static final String COLUMN_MAIL = "MAIL";
    public static final String COLUMN_ADDRESS = "ADDRESS_ID";

    private Long id;
    private final String firstName;
    private final String lastName;
    private final String eMail;
    // private Address address;

    @Override
    public int compareTo(final Person person) {
        return this.lastName.compareTo(person.getLastName());
    }

    @Override
    public String toString() {
        return this.firstName + " " + this.lastName + " (" + this.eMail + ")";
    }
}
