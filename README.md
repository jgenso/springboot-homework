# springboot-homework

## Running from console
```bash
mvn spring-boot:run
```

## Accessing the app online
In Chrome or Firefox go to

http://localhost:8080/swagger-ui/index.html#

Username: spring
Password: spring

## Troubleshooting

If an error saying: 
```bash
[ERROR] Failed to execute goal org.springframework.boot:spring-boot-maven-plugin:3.1.5:run 
(default-cli) on project homework: Execution default-cli of goal org.springframework.boot:
spring-boot-maven-plugin:3.1.5:run failed: Unable to load the mojo 'run' in the plugin 
'org.springframework.boot:spring-boot-maven-plugin:3.1.5' due to an API incompatibility: 
org.codehaus.plexus.component.repository.exception.ComponentLookupException: 
org/springframework/boot/maven/RunMojo has been compiled by a more recent version of the Java
 Runtime (class file version 61.0), this version of the Java Runtime only recognizes class file versions up to 55.0
```

Please review if you are using jdk < 21, you need to use a jdk 21.