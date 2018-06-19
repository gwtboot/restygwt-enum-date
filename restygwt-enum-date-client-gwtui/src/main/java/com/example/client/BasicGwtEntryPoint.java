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
import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.RestServiceProxy;

import com.example.api.PersonDto;
import com.example.api.PersonEndpoint;
import com.example.api.PersonType;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

public class BasicGwtEntryPoint implements EntryPoint {

	private static Logger logger = Logger.getLogger(BasicGwtEntryPoint.class.getName());

	private static final String SERVER_CONTEXT_PATH = "http://localhost:9090/server";

	@Override
	public void onModuleLoad() {
		PersonDto person = new PersonDto();
		person.setDate(new Date());
		person.setName("Lofi");
		person.setPersonType(PersonType.COOL);

		Button button = new Button("Click me: " + person.getPersonType().name());
		button.addClickHandler(clickEvent -> {
			logger.info("Hello World!");

			Defaults.setDateFormat(PersonEndpoint.DATE_FORMAT);
			PersonClient personClient = GWT.create(RestPersonClient.class);
			Resource resource = new Resource(SERVER_CONTEXT_PATH);
			((RestServiceProxy) personClient).setResource(resource);

			personClient.getPersons(new MethodCallback<List<PersonDto>>() {
				@Override
				public void onSuccess(Method method, List<PersonDto> response) {
					response.forEach(person -> logger
							.info("Person: " + person.getName() + " - " + person.getPersonType().getType()));
				}

				@Override
				public void onFailure(Method method, Throwable exception) {
					logger.info("Error: " + exception);
					throw new RuntimeException(exception);
				}
			});
		});

		RootPanel.get("helloButton").add(button);
	}
}