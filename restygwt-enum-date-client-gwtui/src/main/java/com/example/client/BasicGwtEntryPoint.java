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
import java.util.logging.Logger;

import org.fusesource.restygwt.client.Defaults;

import com.example.api.PersonDto;
import com.example.api.PersonType;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

public class BasicGwtEntryPoint implements EntryPoint {

	private static Logger logger = Logger.getLogger(BasicGwtEntryPoint.class.getName());

	@Override
	public void onModuleLoad() {
		Defaults.setDateFormat("\"yyyy-MM-dd@HH:mm:ss.SSSZ\"");
		
		PersonDto person = new PersonDto();
		person.setDate(new Date());
		person.setName("Lofi");
		person.setPersonType(PersonType.COOL);
		
		Button button = new Button("Click me: " + person.getPersonType().name());
		button.addClickHandler(clickEvent -> {
			Window.alert("Hello World!");
			logger.info("Hello World!");
		});

		RootPanel.get("helloButton").add(button);
	}
}