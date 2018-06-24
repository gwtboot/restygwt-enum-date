/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.example.server;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.PersonDto;
import com.example.api.PersonEndpoint;
import com.example.api.PersonType;

@CrossOrigin
@RestController
public class PersonController {

	@RequestMapping(method = RequestMethod.GET, value = PersonEndpoint.PERSON_LIST)
	public List<PersonDto> getPersons() {
		List<PersonDto> persons = new ArrayList<>();

		PersonDto person1 = new PersonDto();
		person1.setDate(new Date());
		person1.setName("Lofi");
		person1.setPersonType(PersonType.COOL);

		PersonDto person2 = new PersonDto();
		person2.setDate(new Date());
		person2.setName("Kulaki");
		person2.setPersonType(PersonType.BORING);

		persons.add(person2);
		persons.add(person1);

		return persons;
	}

	@RequestMapping(method = RequestMethod.GET, value = PersonEndpoint.PERSON_WITH_ERROR_LIST)
	public List<PersonDto> getPersonsWithError() throws AccessDeniedException {
		throw new AccessDeniedException("Cannot access the file");
	}

}
