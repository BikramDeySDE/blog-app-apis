# blog-app-apis
 Blog Application (Backend) using Spring Boot
 
# Important Points
1. In Entities and its respective DTOs, the variable names (i.e. field names) should be same (example : if in Post entity, there is a field named as "User", in the PostDto as well, the field name should be "User" only, not "UserDto")
2. Always assign a normal role to the newly registered user generally

# Form based authentication
1. add dependency '' in pom.xml
2. run the app and you'll get the form based authentication ready
3. username : 'user' | password : (will be shown on the console such as below : )

```
Using generated security password: 81da61b3-c24d-437d-ab23-e288c185aa18

This generated password is for development use only. Your security configuration must be updated before running your application in production.
```


#JWT Authentication
STEP-1) add dependency : (io.jsonwebtoken)

```
<!-- https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.9.1</version>
</dependency>
```
STEP-2) create class JwtAuthenticationENtryPoint implements AuthenticationEntryPoint in Security package, mark the class as @Component for autowiring 
-> 'commence()' (method under this class) will run, whenever some unauthorized person tries to access the APIs

STEP-3) create class JwtTokenHelper for the token related operations and mark the class as @Component for autowiring : in this class, the entire code is copied from source and pasted here

STEP-4) create class jJwtAuthenticationFilter extends OncePerRequestFilter, mark the class as @Component for autowiring 
-> 'doFilterChain()' method will run, whenever a reuest is hit

```
-> Steps :
	i.	autowire 'UserDetailsService' and 'JwtTokenHelper' 
	ii.	get JWT token from request
	iii.retrieve username from JWT token
	iv. once we get the token, Validate the token
	v. fetching userDetails by 'loadUserByUsername()' method of 'UserDetailsService'
	vi. once we Validate the token and check Authentication is null in Security Context, Set authentication in Security Context
	vii. filter the request using 'filterChain.doFIlter()' method
```	
```
STEP-5) create JwtAuthResponse (Here we will keep the token only as a field and annotate the class with @Data)

STEP-6) Configure JWT in spring security Config (in SecurityConfig class)
STEP-7) create a login API to return token

```
	i. create class JwtAuthRequest (annotate the class with @Data)
	ii. create class JwtAuthResponse (annotate the class with @Data)
	iii. create Log In API for creating token (create a method 'createToken()' in AuthController)
```

STEP-8) make the login url public (available for all) (in SecurityConfig class)

```
		http
		.authorizeHttpRequests()
		.requestMatchers("/auth/login").permitAll()
```

STEP-9) test the application

```
	i. log in :
		a. go to the GET request : "/auth/login" with the request body
		{
			"username" : "<userEmail>",
			"password" : "<userPassword>"
		}
	
	ii. in case of correct username and password, you will get a token generated for the particular user
	
	iii.now go to any other API, in the 'Header' section, provide the token as : 'Bearer <token>' and hit the request, and you'll be able to access the API
```
`
<br>
<br>
#Role Specific APIs

STEP-1) Configuration of Roles

CASE-I: In case roles will be created manually in DB
Check the DB -> 
		i. user table -> check if for all the users, passwords are encoded, and know the decoded passwords
		ii. role table -> check if roles 'ROLE_ADMIN' and 'ROLE_NORMAL' are already created, if not created, then create the roles
		iii. user_role table -> check if specific roles are assigned to specific users or not, if not then assign

CASE-II: In case roles will be created through code as defined in the class BlogApplication.java (main class) 
		-> it is already implemented in the main class
		-> also need to create RoleRepository
		-> also need to create RoleDto for Register API
		
STEP-2) annotate the class 'SecurityConfig' with @EnableGlobalMethodSecurity(prePostEnabled=true) -> now we can apply security in each method

STEP-3) go to the specific HTTP method controller for which you want to apply this, annotate the method @Preauthorize("hasRole('<ROLE>')")

STEP-4) if you want to secure a bunch of APIs with specific URL pattern, then secure them in the SecurityConfig ->
		code : antMatchers/requestMathers("<URL-pattern>").hasRole('<ROLE>')
		example: requestMatchers("/users/delete").hasRole('ADMIN')

STEP-5) if you want to un-secure (publicly accessible) any specific type of Requests, then un-secure them in the SecurityConfig ->
		code : antMatchers/requestMatchers(HttpMethod.<HTTP-METHOD>).permitAll()
		example : requestMatchers(HttpMethod.GET).permitAll()
		


# Swagger Configuration (Old method : using springfox)
STEP-1) add dependency : springfoc-boot-starter

```
<!-- https://mvnrepository.com/artifact/io.springfox/springfox-boot-starter -->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>
```

STEP-2) annotate the security configuration class (SecurityConfig.java) with @EnableWebMVC

STEP-3) make the URL ("/v3/api-docs") publicly accessible in security configuration class (SecurityConfig.java) [note : v3 -> as we are using swagger version 3.0.0) as mentioned in the dependency]

STEP-4) make the below mentioned URLs publicly accessible :
	
	i. "/v2/api-docs"
	ii. "/swagger-resources/**"
	iii. "/swagger-ui/**"
	iv. "/webjars/**"
	
	NOTE : You can put all these URls, which you need to make publicly accessible, in an array
	
STEP-5) Do the necessary Swagger Configurations in SwaggerConfid.java	
	
STEP-6) now you can run the application and if you hit the URLs mentioned below you can the respective results :

	i. Postman : http://localhost:8080/v3/api-docs : complete API details in the Back-end Form
	ii. Browser : http://localhost:8080/swagger-ui/index.html : complete API documentation in UI Form 
	
	
# Migration from SpringFox to SpringDoc : 

```
Reference Documentation : https://springdoc.org/migrating-from-springfox.html
Reference Video Link : https://www.youtube.com/watch?v=UvIWQSKz8kE
```
	
# Swagger Configuration (New method : using springdoc)

STEP-1) add dependency : springdoc-openapi-starter-webmvc-ui

```
<!-- https://mvnrepository.com/artifact/io.springfox/springfox-boot-starter -->
   <dependency>
      <groupId>org.springdoc</groupId>
      <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
      <version>2.6.0</version>
   </dependency>
```

STEP-2) annotate the security configuration class (SecurityConfig.java) with @EnableWebMVC

STEP-3) make the URL ("/v3/api-docs") publicly accessible in security configuration class (SecurityConfig.java) [note : v3 -> as we are using swagger version 3.0.0) as mentioned in the dependency]

STEP-4) make the below mentioned URLs publicly accessible :
	
	i. "/swagger-resources/**"
	ii. "/swagger-ui/**"
	iii. "/webjars/**"
	
	NOTE : You can put all these URls, which you need to make publicly accessible, in an array
	
STEP-5) Do the necessary Swagger Configurations in SwaggerConfig.java : create a method to return OpenAPI object (with info and externalDocs)

STEP-6) add swagger configurations in application.properties file :

```
springdoc.packages-to-scan=com.bikram.blog.controllers
springdoc.paths-to-match=/auth/**, /users/**, /categories/**, /posts/**, /comments/**, /user/**, /category/**, /post/**, /files/**, /comments/** 
```
	
STEP-7) now you can run the application and if you hit the URLs mentioned below you can the respective results :

	i. Postman : http://localhost:8080/v3/api-docs : complete API details in the Back-end Form
	ii. Browser : http://localhost:8080/swagger-ui/index.html : complete API documentation in UI Form 

	
# Implementation of Security in API Docs
There are 2 Processes to implement Swagger Configuration with Security in API Docs

Process-1) By declaration of Bean

in Swagger configuration File, in the method openAPI(), while returning OpenAPI object, add 'addSecurityItem' and 'components' part to the object

```
SwaggerConfig.java > openAPI() > add componenets() part :

new OpenAPI()
	.addSecurityItem(new SecurityRequirement().addList(schemeName))
	.components(new Component()
		.addSecuritySchemes(schemeName, new SecurityScheme()
			.name("")
			.type("")
			.bearerFormat(SecurityScheme.Type.{TYPE})
			.scheme("")))
	.info(new Info().title("...")....)

```

```
NOTE : in Swagger-ui page, we don't need to put "Bearer" from out side while providing the token 
```


Process-2) Using Annotations

STEP-I) Do necessary configuration using Annotation in Swagger Configuration class(SwaggerConfig.java) : we can remove complete 'openAPI()' method (including info, externalDocs, securityItem and components with SecurityScheme)(steps mentioned in STEP-5 and Process-I) from SwaagerConfig.java and instead we can use Annotations

```
@Configuration
@SecurityScheme(
		name = "bearerScheme",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer"
		)
@OpenAPIDefinition(
		info = @Info(
				title = "Blogging Appilacation",
				description = "This project is developed by SDE Bikram Dey",
				version = "1.0",
				contact = @Contact(
						name = "Bikram Dey",
						email = "bikramdeyofficial@gmail.com",
						url = "https://www.linkedin.com/in/bikramdey/"
						),
				license = @License(
						name = "OPEN License"
						)
				),
		externalDocs = @ExternalDocumentation(
				description = "Software Development Engineer : Bikram Dey",
				url = "https://www.linkedin.com/in/bikramdey/"
				))
public class SwaggerConfig {

}
```

STEP-II) Annotate all the Controller Classes with the Annotation "@SecurityRequirement"

```
@SecurityRequirement(name = "bearerScheme")	// for implementation of security in swagger
```


# Adding Support for MediaType XML with JSON 

STEP-1) Add a dependency : jackson dataformat xml

```
<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.dataformat/jackson-dataformat-xml -->
<dependency>
    <groupId>com.fasterxml.jackson.dataformat</groupId>
    <artifactId>jackson-dataformat-xml</artifactId>
</dependency>
```
```
Note : 
i. after adding this dependency, the default MediaType will be set as XML
ii. if we want to get choose any MediaType in which we want to get the response while hitting the API, we need to do the configuration accordingly
```
STEP-2) Create a configuration class (ContentConfig.java), annotate the class with '@Configuration', implement the interface 'WebMvcConfigurer', and override a method 'configureContentNegotiation(ContentNegotiationConfigurer)'

```
@Configuration
public class ContentConfig implements WebMvcConfigurer {
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {		
		configurer.favorParameter(true)
					.parameterName("mediaType")
					.defaultContentType(MediaType.APPLICATION_JSON)
					.mediaType("json", MediaType.APPLICATION_JSON)
					.mediaType("xml", MediaType.APPLICATION_XML);
	}
}
```

# Deployment in AWS

- Required Services :

	i. Amazon EC2 : provide a virtual computer
	ii. AWS Elastic Beanstalk : We need to simply upload our code and Elastic Beanstalk will automatically handle the deployment (from capacity load balancing, auto-scaling to health monitoring)
	iii. Amazon RDS : provides Relational Database
	iv. Amazon Route 53 : routes end-users to Internet application by translating domain-name into IP address
	v. Amazon S3 : Object storage built to retrieve any amount of data from anywhere
	
- AWS Account Creation : 

```
Link : https://signin.aws.amazon.com/signup?request_type=register
```

- Steps for Deployment : 

PART-A) Managing Different Environments

	STEP-1) Configure Different Environments : configure application.peoperties file for different environments : 
	i. application.properties : the common properties for all environments will be in this file. also add a property : spring.profiles.active=<env-name; ex.- dev/prod>
	ii. application-dev.properties : the properties only for the development environment (other than the common properties) will be in this file
	iii. application-prod.properties : the properties only for the production environment (other than the common properties) will be in this file
	
	note : before creating jar file, the active profile must be set to 'prod'
		spring.profiles.active=prod
		
PART-B) Set up MySQL DB on AWS using RDS

	STEP-2) Create RDS : go to AWS console > search "RDS" > create Database > enter details [choose Engine type, Edition, Version, Template, Identifier (name of DB), Credentials (username & password), public access] > click on "Create Database"
	STEP-3) Once DB is created, you can see the DB status as "Available"
	STEP-4) DB details can be checked after clicking on it under Connectivity & Security section :
			Endpoint : host
			Port : port number
			- use these details : 
				- to connect the workbench
				- to configure application.peoperties file in the project and create the jar file
	STEP-5) Make the Port Publicly Accessible : Go to DB Details > Connectivity & Security > under 'Security' section, click on The VPC security group default link > click on security group id > edit Inbound Rules > add a rule & select [Type : MySQL/Aurora | Source : Anywhere IPv4] & save rule
		
PART-C) Connect the DB in Workbench
	
	STEP-6) Connect the Workbench using these details : Go to workbench in the local PC > click on the '+' button > Provide connection Details (Connection name : give as per your choice | Connection method : Standard(TCP/IP) | Hostname : 'Endpoint' as you can see in AWS RDS DB Details | port : port number as you can see in AWS RDS DB Details | username & password : as you have given while creating the RDS DB) > test connection > OK > Database connection completed.
	STEP-7) Create A Schema in the workbench under this connection : name : as we are using the link declared in application-prod.properties file > this Schema is now created in AWS.
	
PART-D) DB configuration in application-prod.properties
	
	STEP-8) Configure applicatio-prod.peoperties file :
			- spring.datasource.url=jdbc:mysql://<Hostname>:<port>/<Schema-name>
				Here :	Hostname - 'Endpoint' as you can see in AWS RDS DB Details in STEP-6
						port - port number as you can see in AWS RDS DB Details in STEP-6
						Schema name - as you've created under the AWS connection in STEP-7
			- spring.datasource.username=<username - as you have given while creating the DB>
			- spring.datasource.password=<password - as you have given while creating the DB>
	STEP-9) Configure applicatio.peoperties file : (activate the prod profile)
			- add property :
				spring.profiles.active=prod
	STEP-10) change the port no to 5000 - because Elastic Beanstalk in AWS expects out application to run at port no 5000 (in case you are deploying the application on AWS)
				
PART-E) Create jar file
				 
	STEP-11) create the jar file : click on the project > run as > Maven Build... > Goals : package > apply > run > it will run the build and after successful completion of build, jar file will be created under the 'target' folder
		- rename the jar file
		- run in console : launch the command prompt in the folder 'target' > run the command : java -jar <jar-file-name including .jar extension> and you can check on browser by hitting the swagger ui url 
		- stop the application using console : in console, press 'Ctrl + C', hit 'Enter'
	
- Deploy the jar file in AWS
	
	STEP-12) go to aws console > search "Elastic Beanstalk" > click on the button 'create application' > fill in the blanks (application name, platform) & select on upload your code > choose the jar file by clicking on 'choose file' > after the file is uploaded successfully, click on 'configure more options' > go to the 'Database' section & click on 'edit' > wait for some seconds and it will fetch some details > fill the 'username' and 'password' for the database (same username and password for the DB which you have just created in AWS using RDS) > click on 'save' > now click on the button 'create app' > it will create an environment in backend and our app will be deployed
	
	- after successful completion of the build, we can see an url just under the <appname>-env (here blogapp-env), you can use this URL to access your application (URL will be like this : "<appname>-env.....elasticbeanstalk.com") (after successful completion of build, you can see a green Tick mark under the 'Health' and it should be showing as 'OK')
	
	- you can also access the swagger API : <appname>-env.....elasticbeanstalk.com/swagger-ui/index.html
	
	- Note : When our application is deployed on AWS, an EC instance will be created. You can also check this : seach "EC2" > click on 'EC2' > click on 'instances (running)' > you can see the running instances here (names would be like '<Appname>-env')
	
	- if we want to update anything in code, we can update it in code, rebuild and create the package (jar file) and upload the code by clicking on 'upload and deploy' button under 'running version' > choose file > click on 'Deploy. This will also maintain the versions of our application (like <appname>-source-1, <appname>-source-2,....).
		
- For this project
	
	STEP-1) Configure Different Environments : we are configuring 2 Environments here : dev and prod (application-dev.properties & application-prod.peoperties)
	STEP-2) Create RDS : Here choose these options :
						engine type : MySQL
						Edition : MySQL Community
						Version : MySQL 8.0.28
						Templates : Free Tier
						Identifier : blog-db
						Public Access : yes
	STEP-5) 	DB Link for 'blog-db' created on AWS : https://us-east-1.console.aws.amazon.com/rds/home?region=us-east-1#databases:
	STEP-7) Schema name : blog-app-apis
	STEP-8) 	spring.datasource.url=jdbc:mysql://<Hostname>:<port>/<Schema-name>
			spring.datasource.username=bikram
			spring.datasource.password=bikram
			STEP			

	STEP-9) 	rename the jar file as : blog-api-app.jar
			command to run : java -jar blog-api-app.jar
			swagger url : localhost:8080/swagger-ui/index.html
			
	STEP-12)	application name : blogapp
			platform : java
			
	NOTE : Used AWS services : i. RDS | ii. Elastic Beanstalk | iii. EC2
	NOTE : If you want to delete your deployed application, you need to delete : 
			i. EC2 instances ii. databases created using RDS iii. Security Groups 
	Special Note : always check all the zones i.e. anything is running in any zones