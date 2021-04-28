package de.fherfurt.jdbc.storages;

import de.fherfurt.jdbc.domains.Person;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PersonRepositoryTest {

    PersonRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository = new PersonRepository();
    }

    @Test
    void save() {
        // GIVEN
        Person given = new Person("Hans", "Musterfrau", "test@gmx.com");

        // WHEN
        Long result = repository.save(given);

        // THEN
        Assertions.assertThat(result)
                .isNotNull()
                .isGreaterThan(0);
    }

    @Test
    void findAll() {
        // GIVEN
        Person given1 = new Person("Hans", "Musterfrau", "test@gmx.com");
        Person given2 = new Person("Frauke", "Mustermann", "test2@gmx.com");

        List<Long> idsOfPersisted = new ArrayList<>();
        idsOfPersisted.add(repository.save(given1));
        idsOfPersisted.add(repository.save(given2));

        // WHEN
        List<Person> result = repository.findAll();

        // WHEN
        Assertions.assertThat(result).isNotNull().isNotEmpty().allMatch(Objects::nonNull);
        Assertions.assertThat(idsOfPersisted).isNotNull().isNotEmpty().allMatch(Objects::nonNull);
    }

    @Test
    void findByName() {
        // GIVEN
        Person given1 = new Person("Hans", "Musterfrau", "test@gmx.com");
        Person given2 = new Person("Frauke", "Mustermann", "test2@gmx.com");

        List<Long> idsOfPersisted = new ArrayList<>();
        idsOfPersisted.add(repository.save(given1));
        idsOfPersisted.add(repository.save(given2));

        // WHEN
        Optional<Person> result = repository.findBy("Mustermann");

        // WHEN
        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().getFirstName()).isEqualTo("Frauke");
        Assertions.assertThat(result.get().getLastName()).isEqualTo("Mustermann");
    }
}