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

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.api.ErrorDto;

@RestControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDto handleAuthorizationException(AccessDeniedException accessDeniedException) {
		// build a response body out of ex, and return that
		ErrorDto errorDto = new ErrorDto();
		errorDto.setDetail(accessDeniedException.getMessage());
		errorDto.setErrorcode("ERROR_NUMBER_2018");
		errorDto.setMessage("Error Authorization");
		errorDto.setStatus(HttpStatus.BAD_REQUEST.toString());

		return errorDto;
	}

}
