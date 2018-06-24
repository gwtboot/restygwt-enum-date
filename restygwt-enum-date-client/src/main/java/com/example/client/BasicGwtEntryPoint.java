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
package com.example.client;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.REST;
import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.RestServiceProxy;

import com.example.api.PersonDto;
import com.example.api.PersonEndpoint;
import com.example.api.PersonType;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class BasicGwtEntryPoint implements EntryPoint {

	private static Logger logger = Logger.getLogger(BasicGwtEntryPoint.class.getName());

	private static final String SERVER_CONTEXT_PATH = "http://localhost:9090/server";

	@Override
	public void onModuleLoad() {
		PersonDto coolPerson = new PersonDto();
		coolPerson.setDate(new Date());
		coolPerson.setName("Lofi");
		coolPerson.setPersonType(PersonType.COOL);

		PersonDto boringPerson = new PersonDto();
		boringPerson.setDate(new Date());
		boringPerson.setName("Test");
		boringPerson.setPersonType(PersonType.BORING);

		Defaults.setDateFormat(PersonEndpoint.DATE_FORMAT);

		FlowPanel flowPanel = new FlowPanel();

		Button personListButton = executePersonList(coolPerson);
		Button personWithErrorListButton = executePersonWithErrorList(boringPerson);

		flowPanel.add(personListButton);
		flowPanel.add(personWithErrorListButton);

		RootPanel.get("flowPanel").add(flowPanel);
	}

	private Button executePersonList(PersonDto person) {
		Button personListButton = new Button("Click me: " + person.getPersonType().name());

		personListButton.addClickHandler(clickEvent -> {
			logger.info("Hello World: executePersonList");

			// We can use general client without the extension
			// to RestyGwt RestService
			PersonClient personClient = GWT.create(RestPersonClient.class);
			Resource resource = new Resource(SERVER_CONTEXT_PATH);
			((RestServiceProxy) personClient).setResource(resource);

			personClient.getPersons(new MethodCallback<List<PersonDto>>() {
				@Override
				public void onSuccess(Method method, List<PersonDto> response) {
					response.forEach(person -> logger.info("Person: " + person.getName() + " - Date: "
							+ person.getDate() + " - Type: " + person.getPersonType()));
				}

				@Override
				public void onFailure(Method method, Throwable exception) {
					logger.info("Error: " + exception);
					throw new RuntimeException(exception);
				}
			});
		});

		return personListButton;
	}

	private Button executePersonWithErrorList(PersonDto person) {
		Button personWithErrorListButton = new Button("Click me: " + person.getPersonType().name());

		personWithErrorListButton.addClickHandler(clickEvent -> {
			logger.info("Hello World: executePersonWithErrorList");

			DirectRestPersonWithErrorClient personClient = GWT.create(DirectRestPersonWithErrorClient.class);
			Resource resource = new Resource(SERVER_CONTEXT_PATH);
			((RestServiceProxy) personClient).setResource(resource);

			// We need to use specific client for RestyGwt
			// DirectRestService
			REST.withCallback(new MethodCallback<List<PersonDto>>() {
				@Override
				public void onFailure(Method method, Throwable exception) {
					logger.info("Error: " + exception + "\nMessages: " + method.getResponse().getText());
				}

				@Override
				public void onSuccess(Method method, List<PersonDto> response) {
					response.forEach(person -> logger.info("Person: " + person.getName() + " - Date: "
							+ person.getDate() + " - Type: " + person.getPersonType()));
				}

			}).call(personClient).getPersonsWithError();
		});

		return personWithErrorListButton;
	}

}