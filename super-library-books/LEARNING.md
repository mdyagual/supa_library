# Learning Repport - BooksAPI

## 1. Dependencies used.
```
<dependencies>
<!--- Quarkus dependencies --->
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-resteasy-reactive-jackson</artifactId>
    </dependency>
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-resteasy-reactive</artifactId>
    </dependency>
    <!--- Hibernate ORM dependencies & PostgreSQL --->
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-hibernate-orm</artifactId>
    </dependency>
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-agroal</artifactId>
    </dependency>
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-jdbc-postgresql</artifactId>
    </dependency>
    <!--- MapStruct for DTO --->
    <dependency>
      <groupId>org.mapstruct</groupId>
      <artifactId>mapstruct</artifactId>
      <version>${org.mapstruct.version}</version>
    </dependency>
    <dependency>
      <groupId>org.mapstruct</groupId>
      <artifactId>mapstruct-processor</artifactId>
      <version>${org.mapstruct.version}</version>
      <scope>provided</scope>
    </dependency>
    <!--- For unit testing --->
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-junit5</artifactId>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>io.rest-assured</groupId>
      <artifactId>rest-assured</artifactId>
      <scope>test</scope>
    </dependency>
    <!--- Lombok --->
    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <version>1.18.26</version>
      <scope>provided</scope>
    </dependency>
</dependencies>
```

## 2. Lombok and MapStruct config to working together.
I found this change curious, because last year (2022) it wasn't necessary. 

From [MapStruct page](https://mapstruct.org/faq/#Can-I-use-MapStruct-together-with-Project-Lombok): 
_Essentially, MapStruct will wait until Lombok has done all its amendments before generating mapper classes for Lombok-enhanced beans.
If you are using Lombok 1.18.16 or newer you also need to add lombok-mapstruct-binding in order to make Lombok and MapStruct work together._

So, you need to add is this in the _pom.xml_ (additional to the dependencies) inside <configuration/> tags.
```
<configuration>
    ...
    <annotationProcessorPaths>
        <path>
          <groupId>org.projectlombok</groupId>
          <artifactId>lombok</artifactId>
          <version>${org.projectlombok.version}</version>
        </path>
        <path>
          <groupId>org.projectlombok</groupId>
          <artifactId>lombok-mapstruct-binding</artifactId>
          <version>0.2.0</version>
        </path>
        <path>
          <groupId>org.mapstruct</groupId>
          <artifactId>mapstruct-processor</artifactId>
          <version>${org.mapstruct.version}</version>
        </path>
   </annotationProcessorPaths>
</configuration>
```

## 2. Mapstruct issue.
This was pretty difficult to figure it out, but hopefully I get an [answer](https://github.com/quarkusio/quarkus/issues/32983.), _if you can consider it as one_.

The situation was when I'm trying to run the microservice I got this error message:

```
Couldn't find type javax.enterprise.context.ApplicationScoped. Are you missing a dependency on your classpath?
```

The solution? In the _BookMapper.java_, instead of "cdi",

```
@Mapper(componentModel = "cdi", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
```

Change it for "jakarta".

```
@Mapper(componentModel = "jakarta", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
```

In the _answer_ that I found it didn't give a clear explanation. I deduce it has to be with "javax" be renamed to "jakarta" and for some
_bug_ reason when you tried to launch the microservice it looks for the old name. Doing a lil research I found that 'cdi' component model 
on a @Mapper resulted in an @ApplicationScoped implementation class, which couldn't get properly resolved in my case, so that is the reason 
of the launched error. Plus, since I am using java 17 and Quarkus 3.0.x the re-naming cause turns more evident. 

In the [documentation of MapStruct](https://mapstruct.org/documentation/stable/reference/html/#configuration-options) you will find more about @Mapper and its componentModel param.
If I found a better explanation, I will share it.

## 3. Repository implementation.
Oh, this was my favorite part just for the anecdote. Because I could use directly Panache dependency, but I wanted to struggle for fun. 

So, a raw implementation and using a containerized PostgreSQL. Maybe I will do a change to a Cloud SQL. IDK.

In my case before start code, I need to create the database because it is not automated when I run the microservice. And attach the creation name in the
_application.properties_ file.

```
quarkus.datasource.jdbc.url = jdbc:postgresql://localhost:5433/databaseName
```

I built a _repository_ layer/folder with an interface and the implementation of this one in a class that will act as a _Repository Entity_ abstraction in
order to not interact in the service layer/folder with _EntityManager_. I consider it a clean practice.

Something that I notice is the absence of a main method for retrieve all the registers. I had to use _.createQuery()_ to wrote it. Plus, while coding this
an issue appears related to "QuerySyntaxException". Googling a bit, I discovered that "SELECT * FROM books;" was invalid for the '*' and maybe the ';'. 
Nothing related to SQL main syntax that you typical use, it was more for this approach inside java code. So, I have to replace it for "SELECT b from Book b" when 'Book' is the name
of the class using @Entity.

## 4. Custom Id Generator config.
When I have to set up the @Entity class, I remember why I don't like relational dbs: Primary key sequence configuration with @GeneratedValue.

I'm not a fan of my primary key being Long type, so I always look to customize it. But it is a good time to remember [the configurations available](https://www.baeldung.com/hibernate-identifiers)

For that I generate _IdGenerator.java_ in _config_ layer/folder. What was coded is the main configuration to generate a string-unique primary key.

After that, an issue started to come out:

```
@GeneratedValue(generator = "custom_id_generator")
@GenericGenerator(
name = "custom_id_generator",
strategy = "ec.com.books.library.supa.config.IdGenerator")
```

In @GenericGenerator the IDE told me that the 'strategy' option is depreciated: Doing a lil of research, I found that it was renamed to 'type'. So, 
I did the change but, I also have to configure something new called parameters in order to use my IdGenerator class. And the cherry cake of all this was to
search what to set in 'type'.

So, with all the changes, the result was:

```
@GeneratedValue(generator = "custom_id_generator")
@GenericGenerator(
    name = "custom_id_generator",
    type = org.hibernate.id.enhanced.SequenceStyleGenerator.class,
    parameters = {
         @Parameter(name = "uuid_gen_strategy_class", value = "ec.com.books.library.supa.config.IdGenerator")
    }
)
```

# Extras
## Maven compiler.
I'm not considering this as an issue because the microservice runs normally but, it is worth to mention what I am experimenting. 

The _pom.xml_ file is not recognizing some plugins and the maven.home. Here is a pic about it.

![Maven_issue](https://github.com/mdyagual/supa_library/blob/main/imgs/Maven_Issue.JPG)

I read a lot of possible solutions but until now (24/5/2023) I didn't make it work. I hope in the future solve it and
share the solution in this section.

# Closure
Re-learning quarkus is fun for a Springboot main backend amateur programmer as me. Any other things that I can add in this Learning file I will do it as
as time let me.