/**
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.zipcloud.spring.social.yahoo.api.impl;

import org.springframework.web.client.RestTemplate;

import edu.zipcloud.spring.social.yahoo.api.ContactsOperations;
import edu.zipcloud.spring.social.yahoo.filter.ContactsFilter;
import edu.zipcloud.spring.social.yahoo.module.CategoriesWrapper;
import edu.zipcloud.spring.social.yahoo.module.Category;
import edu.zipcloud.spring.social.yahoo.module.Contact;
import edu.zipcloud.spring.social.yahoo.module.ContactWrapper;
import edu.zipcloud.spring.social.yahoo.module.Contacts;
import edu.zipcloud.spring.social.yahoo.module.ContactsWrapper;

import java.util.List;

/**
 * Base class for all operations performed against the Yahoo Social API.
 *
 * @author Ruiu Gabriel Mihai (gabriel.ruiu@mail.com)
 */
public class ContactsTemplate extends AbstractYahooOperations implements ContactsOperations {

    private RestTemplate restTemplate;

    public ContactsTemplate(RestTemplate restTemplate, boolean isAuthorized, String guid) {
        super(isAuthorized, guid);
        this.restTemplate = restTemplate;
    }

    public Contacts getContacts() {
        requiresAuthorization();
        return restTemplate.getForObject(buildUri("/contacts"), ContactsWrapper.class).getContacts();
    }

    public Contacts getContacts(ContactsFilter filter) {
        requiresAuthorization();
        return restTemplate.getForObject(buildUri(String.format("/contacts;%s", filter.build())), ContactsWrapper.class).getContacts();
    }

    public Contacts getContactsByCategory(String categoryName) {
        requiresAuthorization();
        return restTemplate.getForObject(buildUri(String.format("/category/%s/contacts", categoryName)), ContactsWrapper.class).getContacts();
    }

    public Contact getContact(int contactCid) {
        requiresAuthorization();
        return restTemplate.getForObject(buildUri(String.format("/contact/%d", contactCid)), ContactWrapper.class).getContact();
    }

    public List<Category> getCategories() {
        requiresAuthorization();
        return restTemplate.getForObject(buildUri("/categories"), CategoriesWrapper.class).getCategories().getCategory();
    }

    public List<Category> getCategoriesByContactCid(int contactCid) {
        requiresAuthorization();
        return restTemplate.getForObject(buildUri(String.format("/contact/%d/categories", contactCid)), CategoriesWrapper.class).getCategories().getCategory();
    }

}