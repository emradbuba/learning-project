## Sample JPA Project
This nice [YouTube](https://www.youtube.com/playlist?list=PLEocw3gLFc8UYNv0uRG399GSggi8icTL6) playlist goes through most of material in this small project.

### TODO
> Pid generation -> select FOR UPDATE - set lock on the table (perimistic lock)"

### Basic notes

<details>
<summary>Is @Id required in each @Entity?</summary>

> Yes. Unlike real database where table might (but does not have to) contain a primary, JPA requires for each `@Entity` an `@Id`. It does not matter if a database table has the PK or not.
</details>

<details>
<summary>What is the EntityManager</summary>

> * Basically it represents a **context**. 
> * Using it, we can **manage transactions** (commits, rollbacks) 
> * Created by means of `EntityManagerFactory`
</details>

<details>
<summary>Does JPA defines the update method EntityManager</summary>

> No. What Hibernate/ORM/JPA implementation retrieves an `entity` from a database (for example using `em.find()`),
> the entity lands in the context. After transaction is finished, the content of the context is **mirrored** in the database.
> This is why, for example, calling the `save()` method in SpringBoot not required, as the _mirroring_ action will take place at the end of transaction.
</details>

<details>
<summary>Will the below name be stored in database?</summary>

> Yes. Not in the same line where the comment, but when transaction is finished Hiberate/JPA will mirror the state
> of the context to the database, so new name will be stored in database as well.
</details>

```java
try {
    em.getTransaction().begin();
    Person p = em.find(Person.class, 37488); // <-- Select to db goes here...
    p.setName("New Name"); // <--- will this name be stored in database? 
    
    em.getTransaction().commit();
} finally {
    em.close();
}
```

#### Entity lifecycle
There are some basic lifecycle action of an entity: 
<details>
<summary><code>em.persist</code></summary>

> Adds an entity to the context
</details>
<details>
<summary><code>em.find</code></summary>

> Find the entity in database and adds it to the context if not already exist
</details>
<details>
<summary><code>em.remove</code></summary>

> Marks entity for removal
</details>
<details>
<summary><code>em.merge</code></summary>

> Merges entity from outside the context to the context, knowing that the entity exists in database. 
</details>
<details>
<summary><code>em.refresh</code></summary>

> Kind of a context object "reset". Loads (sends query) the entity from database and replace context state. 
</details>
<details>
<summary><code>em.detach</code></summary>

> Taking the entity out of context
</details>

<details>
<summary><code>em.find</code> vs. <code>em.getReference</code></summary>

> `em.getReference` is more "lazy" and creates only a reference to a database object. It sends query to database when it is really required.
> On the contrary, the `em.find` immediately sends a `SELECT` to DBMS and adds it to context.
```java
try {
    em.getTransaction().begin();
    Person p = em.getReference(Person.class, 37488); // <-- no  query yet...
    
    // this actually triggers a select, not getReference:
    p.setName("New Name");

    em.getTransaction().commit();
} finally {
    em.close();
}
```
</details>

<br>
<details>
<summary>Does the below code will create an entry in database?</summary>

> No. Entity is created, but outside the context. There is no action adding it to a context, so when
> transaction is committed, there is nothing to mirror. 
</details>

```java
try {
    em.getTransaction().begin();
    Person p = new Person();
    p.setId(1);
    p.setName("New Name");
    em.getTransaction().commit();
} finally {
    em.close();
}
```

<details>
<summary>What the <code>flush()</code> action actually does?</summary>

> It **saves**/**sends**/**synchonize** the state of context to the database, but it ***will not commit*** the transaction. 
> It means that if, after `flush()` there is an exception thrown, the changes will be rolled back.
</details>

<details>
<summary>When the <code>flush()</code> takes place?</summary>

> The `flush()` is executed of we invoke it, but also it can depend on the FlushMode. If it is set to `AUTO`, then ORM can 
> decied by itsown when to transfer changes to database (for example in case of many changes in loop). 
</details>

<details>
<summary>Is <code>flush()</code> effect visible for other transactions?</summary>

> When the `flush()` takes place all changes made to this point are mirrored to database but ***are not committed***.
> When changes are in DBMS, all other transaction with isolation level `READ_UNCOMMITTED` should be able to see them.
> Nevertheless, in case of exceptions changes will be rolled back.
> <br><br>
> See: <a href="https://4programmers.net/Forum/Java/196193-jpa">4programmers - JPA - metoda flush()</a> (PL)
</details>

### Related topics
<details>
<summary>
Hikari - what it is? Is it used in Spring? Can be configured/customized?
</summary>

> * Hikari is a JDBC DataSource implementation that ***provides a connection pooling*** mechanism.
> * Hikari is default connection pool in Spring 2 - it is added in `spring-boot-starter-data-jpa` 
>
> Hikari can be configure in the project:
>```
>spring.datasource.hikari.connectionTimeout=30000\
>spring.datasource.hikari.idleTimeout=600000\
>spring.datasource.hikari.maxLifetime=1800000\
>...
>```
</details>

#### PK generation strategy

> **NOTE:** _When using generation strategy always check how (and if) the GenerationType is supported by DBMS._

<details>
<summary><code>GenerationType.IDENTITY</code></summary>

> Allow DBMS to generate next id for me. For example MySql will use the `auto_increment` clause.
> Note: Oracle, for instance, does not support it and use `SEQUENCE` instead - so always check ;) 
</details>

<details>
<summary><code>GenerationType.SEQUENCE</code></summary>

> Very good, when DB supports sequences - uses sequences to generate next ids. 
> MySql, for instance, does not have sequences, so it will use a `TABLE` instead.
</details>

<details>
<summary><code>GenerationType.TABLE</code></summary>

> It is not recommended as it's not efficient, but unlike `SEQUENCE` or `AUTO_INCREMENT` it is supported by all DBMS.
> It creates a separate table in the database with last ids. When adding a new row, there is a lock set to this table
> and last id is taken and updated. 
</details>

<details>
<summary><code>GenerationType.UUID</code> - why may not be recommended</summary>

> Generates UUID as key - be cautions when to use (performance issues)
> <br><br>
> **Security tip:** 
> Good idea is to use both, internal and external (uuid) ids. UUID can be used in frontends, GET REST api etc to make keys difficult to anticipate.
</details>

<details>
<summary><code>GenerationType.AUTO</code></summary>

> Uses the default of the JPA implementation, so that's why it is not recommended. 
</details>

<details>
<summary><code>Custom strategies</code></summary>

> Out of scope here, but we can create custom generators for OK ids.
</details>
