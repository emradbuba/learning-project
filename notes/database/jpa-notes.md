## Spring JPA - basic notes
This nice [YouTube](https://www.youtube.com/playlist?list=PLEocw3gLFc8UYNv0uRG399GSggi8icTL6) playlist goes through most of material in this small project.

### TODO
> * Using LOMBOK with `@Entity` classes
> * Entity lifecycle: Create more details descriptions and use cases
> * Pid generation -> select FOR UPDATE - set lock on the table (perimistic lock)"
> * How to create JPA Entities from existing database
> * Involving some kind of liquibase scripts
> * How SpringJPA uses em.methods on crud repos

### Resources
* https://www.objectdb.com/java/jpa/getting/started
* https://itecnote.com/tecnote/java-jpa-onetomany-not-deleting-child/
* https://stackoverflow.com/questions/10394857/how-to-use-transactional-with-spring-data
* https://jpa-buddy.com/blog/lombok-and-jpa-what-may-go-wrong

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
<summary>Does JPA defines the update method in EntityManager</summary>

> No. When Hibernate/ORM/JPA implementation retrieves an `entity` from a database (for example using `em.find()`),
> this entity lands in the context. After transaction is finished, the content of the context is **mirrored** in the database.
> This is why, for example, calling the `save()` method in SpringBoot not required, as the _mirroring_ action will take place at the end of transaction.
</details>

<details>
<summary>Is an Entity corresonding to database row, always the same object in memory?</summary>

> If we ask same EntityManager for an entity then yes, but we can have many `EntityManager` each of which has its own persistance context 
> so we may have same database object represented by multiple in memory objects - each in different persistence context. 
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

## Entity lifecycle

### Lifecycle
There are four states in lifecycle of entity (see: [ObjectDB article](https://www.objectdb.com/java/jpa/persistence/managed))
<details>
<summary>New/Transient state</summary>

> Not yet in an `EntityManager`, not in database - just a created instance of an entity
</details>

<details>
<summary>Managed state</summary>

> * Entity becomes managed when it is *persisted*, so added to the context of EntityManager
> * It is also managed when retrieved from `EntityManager`
</details>

<details>
<summary>Removed state</summary>

> It's when **managed** entity is marked to delete by `em.remove(entity)` so it will be removed from DB when committing transaction. 
</details>

<details>
<summary>Detached state</summary>

> Represents entity objects that have been disconnected from the EntityManager. <br>
> For instance, all the managed objects of an EntityManager become detached when the EntityManager is closed or `em.clear`ed.<br> 
> Working with detached objects, including merging them back to an EntityManager.
</details>

### Entity lifecycle actions

There are some basic lifecycle action of an entity: 
<details>
<summary><code>em.persist</code></summary>

> Adds an entity to the context
</details>
<details>
<summary><code>em.find</code></summary>

> Find the entity in database and adds it to the context if not already exist. <br>
> If already exists in context, the object from context will be returned.
> Every entity object can be uniquely identified and retrieved by the combination of its class and its primary key.
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
<summary><code>em.clear</code></summary>

All managed entites are being detached. 
</details>

<details>
<summary><code>em.find</code> vs. <code>em.getReference</code></summary>

> `em.getReference` is more "lazy" and, when object is not in context, it creates only a reference to a database object with valid id (a "hollow" object). <br> 
> It sends query to database when it is really required (when object is accessed).
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

***See:*** [Nice resource](https://www.objectdb.com/java/jpa/persistence/retrieve) explaining Storing/Retrieving entities
<br>
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
<summary>When <code>flush</code> can be useful?</summary>

> Generally when you need the result of some side effects, like an autogenerated key, or a database trigger. <br>
> By flushing we just execute the SQL command which JPA provider has planned until now, but has not yet executed.
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

## Other topics
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

### PK generation strategy

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

<details>
<summary>Cascade.ALL not recomended?</summary>

> We create too much, potentially unexpected, behavior - cascade only what you need to cascade
</details>

<details>
<summary>Default fetchType for <code>@OneToOne</code></summary>

> @OneToOne(fetchType = "...")

For "single field" dependencies like `Person` and `IdCard`, in asked for a `Person`, `IdCard` data will be also selected (`FetchType.EAGER`)
If set to `FetchType.LAZY` - only when `IdCard` is first used
</details>

### About <code>orphanRemoval</code> in <code>@OneToMany</code> relation: 
Nice explanation: [Vladmihalcea blog post](https://vladmihalcea.com/orphanremoval-jpa-hibernate/)
Example: 
```java
@OneToMany(mappedBy = "personEntity", cascade = {CascadeType.PERSIST, CascadeType.MERGE} /*, orphanRemoval=true */)
private Set<EmploymentCertificate> certificates;
```
and
```java
@ManyToOne
private Person personEntity;
```
When we delete a personEntity by id: 
```java
public void removeCertificate(EmployeeCertificate cert) {
    certificatesInPerson.remove(cert);
    cert.setPerson(null);
    // commit...
}
```

### Lombok pitfalls with <code>@Entity</code> classes
<details>
<summary>Using <code>@Data</code>/<code>@EqualsAndHashCode</code> annotation with <code>@Entity</code> class</summary>

> These annotations generate equals/hashCode using entity fields which are, by design, mutable. It can destroy HashSets. 
</details>

<details>
<summary>Using <code>@ToString</code> with <code>Entity</code> classes</summary>

> `@ToString`, same as `@EqualsAndHashCode` reference fields in class, which may lead to broken lazy initialization - invoking the toString may result in loading all data from database which was supposed to be loaded later on demand. 
</details>

<details>
<summary>Missing non-arg constructor</summary>

> Non args is required by entites in JPA, but adding for example `@Builder` - it should always be added by `@NoArgsContructor`
</details>

For details see this [JPA Buddy article](https://jpa-buddy.com/blog/lombok-and-jpa-what-may-go-wrong/).

### Other topics

<details>
<summary>What the role of <code>orphanRemoval</code>?</summary>

> The `orphanRemoval` attribute is going to instruct the JPA provider to trigger a remove entity state transition when a Certificate entity is no longer referenced by its parent Person entity.<br>
> It means, certificate will be removed (so not only when Person is delete, but also when reference does not exist)
> <details>
> <summary>What with no orphanRemoval?</summary>
> 
>> NULL will be set in Certificate (so it will not be deteled, just the reference will be removed)
> </details>
</details>

<details>
<summary>When <code>save</code> / <code>persist</code> is not required?</summary>

> If you retrieve an entity, for example using the findOne method call within a transactional method <br> 
> it has become **managed** from that point by the persistence provider. <br>
> 
> Now if you make any changes to that entity (which is actually a proxy object), upon transaction commit, <br> 
> those changes will be persisted to the database, regardless of the fact of invoking the `save` or `update` methods.
</details>


