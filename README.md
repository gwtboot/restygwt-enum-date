# RestyGWT: restygwt-enum-date
RestyGWT Example for Date and Enum.

[![Build Status](https://travis-ci.com/gwtboot/restygwt-enum-date.svg?branch=master)](https://travis-ci.com/gwtboot/restygwt-enum-date)

This example uses GWT Boot: https://github.com/gwtboot/gwt-boot-samples

This example shows how to structure your Maven modules for a development toward GWT 3.

# Modules

There are three modules available:
1. _API_: restygwt-enum-date-api
2. _Client / Web browser with GWT_: restygwt-enum-date-client
3. _Server with Spring Boot_: restygwt-enum-date-server

# Development Time

On the development time you should start two processes: Client and Server. But this is
not a must. If you just need to build your UI you only need to start the Client part
and you can mock all the call to the REST APIs from Server part with simple interfaces.

## API
For the APIs you just need to run mvn:clean install to deploy your API to the local
Maven repository. The API modul is your interface between the Client and the Server part.

## Client
For the Client part you can start the standard process for GWT just like it is shown in the example from GWT Boot: 

```
https://github.com/gwtboot/gwt-boot-samples
```

## Server
For the Server part you just start the Spring Boot app. In this example the class: 

```
RestygwtDateEnumServerApplication.java
```

That's it. You will have two clean separate processes which are independent of each other. All the 
Maven libs are also independent, so it won't mix between the Client and the Server part. 
Your Client module is therefore ready for GWT 3, because it does not use the Maven libs from the Server, 
in this case, which come from the Spring Boot framework.

# Deployment / Runtime

On the deployment time you only need the Server module, since the Server module has a dependency
to the Client module but only for the JavaScript part. In the Client module the Maven Assembly Plugin will
create a special package with classifier _javascript_. In our example 

```
restygwt-enum-date-client-1.0.0-SNAPSHOT-javascript 
```

which only contains the transpiled JavaScript files from GWT. Here is how the dependency to the JavaScript
distribution created, see this [pom.xml](https://github.com/lofidewanto/restygwt-enum-date/blob/master/restygwt-enum-date-server/pom.xml):

```
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
Here is how to create the _javascript_ distribution from the Client module, see this [pom.xml](https://github.com/lofidewanto/restygwt-enum-date/blob/master/restygwt-enum-date-client/pom.xml) and this Assembly file [distribution.xml](https://github.com/lofidewanto/restygwt-enum-date/blob/master/restygwt-enum-date-client/src/assembly/distribution.xml):

```
	<plugin>
		<artifactId>maven-assembly-plugin</artifactId>
		<configuration>
			<descriptors>
				<descriptor>src/assembly/distribution.xml</descriptor>
			</descriptors>
		</configuration>
		<executions>
			<execution>
				<id>make-assembly</id>
				<phase>package</phase>
				<goals>
					<goal>single</goal>
				</goals>
			</execution>
		</executions>
	</plugin>
```

```
<assembly xmlns="http://maven.apache.org/ASSEMBLY/2.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/ASSEMBLY/2.0.0 http://maven.apache.org/xsd/assembly-2.0.0.xsd">
	<id>javascript</id>
	<formats>
		<format>jar</format>
	</formats>
	
	<includeBaseDirectory>false</includeBaseDirectory>

	<fileSets>
		<fileSet>
			<directory>${project.build.directory}/${project.artifactId}-${project.version}</directory>
			<outputDirectory>.</outputDirectory>
			<includes>
				<include>**/*</include>
			</includes>
			<excludes>
				<exclude>**/*index.html</exclude>
			</excludes>
		</fileSet>
	</fileSets>
</assembly>
```

To run in the deployment time you should build from the top Maven module _restygwt-enum-date_:

```
mvn:clean install
```

To run the Spring Boot standalone:

```
java -jar restygwt-enum-date-server-1.0.0-SNAPSHOT.jar
```

# Epilog

Advantages of this structure in comparison with the structure in [Spring Boot with GWT](https://github.com/gwtboot/gwt-boot-samples/tree/master/gwt-boot-sample-basic-with-spring-boot):
- Clean separation of the modules and each modules are independent of each other.
- The GWT module is clean and pure GWT Maven libs and is ready for GWT 3.
- The Spring Boot module is completely pure Spring Boot, no other Maven libs is included.
- In the deployment you just need to start the Server module with Spring Boot as you include the transpiled JavaScript automatically from the dependency from the Client module.


