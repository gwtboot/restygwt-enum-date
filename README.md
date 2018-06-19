# RestyGWT: restygwt-enum-date
RestyGWT Example for Date and Enum.

This example uses GWT Boot: https://github.com/gwtboot/gwt-boot-samples

This example shows how to structure your Maven module for a nice development.

# Modules

There are three modules available:
1. API: restygwt-enum-date-api
2. Client / Web browser with GWT: restygwt-enum-date-client
3. Server with Spring Boot: restygwt-enum-date-server

# Development Time

On the development time you should start two processes. 

## API
For the API you just need to run mvn:clean install to deploy your API to the local
Maven repository. The API modul is your interface between the Client and the Server part.

## Client
For the Client part you start the standard process for GWT just like the example in the GWT Boot: 
https://github.com/gwtboot/gwt-boot-samples

## Server
For the Server part you just start the Spring Boot app. In this example: RestygwtDateEnumServerApplication.

That's it. You will have two clean separate processes which are independent of each other. All the 
Maven libs are also independent, so it won't mix and your Client module is ready for GWT 3, because
it does not use the Maven libs from the Server, in this case Spring Boot part.

# Deployment / Runtime

On the deployment time you only need the Server module, since the Server module has a dependency
to the Client module but only for the JavaScript part. In the Client module the Assembly Plugin will
create a special package like restygwt-enum-date-client-1.0.0-SNAPSHOT-javascript which
only contains the transpiled JavaScript files. Here is how the dependency to the JavaScript
created:

```xml
           <plugin>
				<artifactId>maven-dependency-plugin</artifactId>
				<executions>
					<execution>
						<id>unpack</id>
						<phase>prepare-package</phase>
						<goals>
							<goal>unpack</goal>
						</goals>
						<configuration>
							<artifactItems>
								<artifactItem>
									<groupId>com.example</groupId>
									<artifactId>restygwt-enum-date-client</artifactId>
									<version>${restygwt-enum-date-client.version}</version>
									<classifier>javascript</classifier>
								</artifactItem>
							</artifactItems>
							<excludes>**/*index.html</excludes>
							<outputDirectory>${project.build.directory}/classes/static</outputDirectory>
							<overWriteReleases>false</overWriteReleases>
							<overWriteSnapshots>true</overWriteSnapshots>
						</configuration>
					</execution>
				</executions>
			</plugin>
```

# Epilog

Advantages of this structure:
- Clean separation of the modules and each modules are independent of each other.
- The GWT module is clean and is ready for GWT 3.
- The Spring Boot module is completely pure Spring Boot, no other Maven libs.

