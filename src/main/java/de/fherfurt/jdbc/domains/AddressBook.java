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

import de.fherfurt.jdbc.domains.errors.PersonNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * <h2>AdressBook</h2>
 * <p>
 *
 * @author Michael Rhöse
 * @version 0.0.0.0, 04/25/2021
 */
@Slf4j
public class AddressBook {

    private final List<Person> persons = new ArrayList<>();

    public void addContact(Person person) {
        LOGGER.debug("Adding Person to AddressBook: " + person.toString());
        this.persons.add(person);
        Collections.sort(persons);
    }

    public Person getContactByLastName(String lastName) throws PersonNotFoundException {
        for (Person person : persons) {
            if (person.getLastName().equals(lastName)) {
                return person;
            }
        }
        String errorMessage = "Person with the last name '" + lastName + "' could not be found!";
        LOGGER.error(errorMessage);
        throw new PersonNotFoundException(errorMessage);
    }

    public int getSize() {
        LOGGER.info("There are currently " + persons.size() + " persons in the address book.");
        return persons.size();
    }

    public Person getPersonByIndex(int index) {
        Person person = persons.get(index);
        LOGGER.info("Person " + person.toString() + " is currently stored at index number " + index);
        return person;
    }

    public long getFrequencyOfLastName(String lastName) {
        LOGGER.debug("Let's find out, how many persons with last name " + lastName + " we have in our address book right now!");
        return persons.stream().map(Person::getLastName).filter(lastName::equals).count();
    }
}
