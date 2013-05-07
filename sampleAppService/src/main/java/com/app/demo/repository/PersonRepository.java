/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to use Celerio within your company? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Template pack-backend-jpa:src/main/java/project/repository/Repository.e.vm.java
 */
package com.app.demo.repository;

import static org.apache.commons.lang.StringUtils.isBlank;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import com.app.demo.dao.PersonDao;
import com.app.demo.domain.Person;
import com.app.demo.repository.support.GenericRepository;

/**
 * Note: you may use multiple DAO from this layer.
 */
@Named
@Singleton
public class PersonRepository extends GenericRepository<Person, String> {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(PersonRepository.class);

    // required by cglib to create a proxy around the object as we are using the @Transactional annotation
    protected PersonRepository() {
        super();
    }

    @Inject
    public PersonRepository(PersonDao personDao) {
        super(personDao);
    }

    @Override
    public Person getNew() {
        return new Person();
    }

    @Override
    public Person getNewWithDefaults() {
        Person result = getNew();
        result.initDefaultValues();
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Person get(Person model) {
        if (model == null) {
            return null;
        }

        if (model.isIdSet()) {
            return super.get(model);
        }
        if (isBlank(model.getUsername())) {
            Person result = getByUsername(model.getUsername());
            if (result != null) {
                return result;
            }
        }
        if (isBlank(model.getEmail())) {
            Person result = getByEmail(model.getEmail());
            if (result != null) {
                return result;
            }
        }

        return null;
    }

    /**
     * Return the persistent instance of {@link Person} with the given unique property value username,
     * or null if there is no such persistent instance.
     *
     * @param username the unique value
     * @return the corresponding {@link Person} persistent instance or null
     */
    @Transactional(readOnly = true)
    public Person getByUsername(String username) {
        return findUniqueOrNone(new Person().username(username));
    }

    /**
     * Delete a {@link Person} using the unique column username
     *
     * @param username the unique value
     */
    @Transactional
    public void deleteByUsername(String username) {
        delete(getByUsername(username));
    }

    /**
     * Return the persistent instance of {@link Person} with the given unique property value email,
     * or null if there is no such persistent instance.
     *
     * @param email the unique value
     * @return the corresponding {@link Person} persistent instance or null
     */
    @Transactional(readOnly = true)
    public Person getByEmail(String email) {
        return findUniqueOrNone(new Person().email(email));
    }

    /**
     * Delete a {@link Person} using the unique column email
     *
     * @param email the unique value
     */
    @Transactional
    public void deleteByEmail(String email) {
        delete(getByEmail(email));
    }
}