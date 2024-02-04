## Sample JPA Project
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

## Relationships
**************

<details>
<summary>Owner of the relationship</summary>

> The side of relation which defines the `JoinColumn` - the side which has the foreign key
</details>

<details>
<summary>Which side of a relationship contins the <code>mappedBy</code> attribute?</summary>

The child - so the oposite to owner of relationship. `mappedBy` corresponds to field in the owner entity.
</details>

<details>
<summary>What is one of advantage of using bidirectional relationships instead of uni-?</summary>

> When allows to create JPQL (Java Persistence query language) queries from both sides of relations which can be helpful depending on circumstances
> 
</details>


### @OneToOne

#### @OneToOne - unidirectional relationship
```java
public class Person {
    
  @OneToOne
  @JoinColumn(name = "ID_CARD_ID") // <-- customize the join column name in database
  private IdCard idCard;

}
```

Adding the entry (similar with update):
```java
@Service
public class IdCardService {
    
    public Person addIdCardToPerson(Long personId, PostIdCardRequest postIdCardDtoRequest) {
        //...
        IdCard idCard = new IdCard();
        idCard.setSerialNumber(postIdCardDtoRequest.getSerialNumber());
        idCard.setValidUntil(postIdCardDtoRequest.getValidUntil());
        idCard.setPublishedBy(postIdCardDtoRequest.getPublishedBy());

        existingPerson.setIdCard(idCard);
        idCardRepository.save(idCard); // no cascading, so we need to persist this entity too...
        return personRepository.save(existingPerson);
    }
}
```

Delete logic: 
```java
public Person deleteIdCardFromPerson(Long personId) {
    Person existingPerson = getPersonByIdOrThrow(personId);
    if (existingPerson.getIdCard() != null) {
        existingPerson.setIdCard(null); // <-- !
        return personRepository.save(existingPerson);
    }
    throw new PersonNotFoundAppException("No person found for given personId: " + personId);
}
```

<details>
<summary>Will above example remove the <code>idCard</code> from database?</summary>

No - there default `orphanRemoval` is set to `false`, so when existing `idCard` in "unrelated" from person (setting `null`) it is still in database
`orphanRemoval` - `false` allows to *preserve* an entry in the database ***even if parent is removed***
It will be changed if we modify relation:

```java
@OneToOne(orphanRemoval = true)
@JoinColumn(name = "ID_CARD_ID")
private IdCard idCard;
```

Now, when idCard is set to `null` in person, the orphaned `idCard` is also removed from database as it's not referenced any more

</details>

<details>
<summary>Do we need to save/persist all entities each time?</summary>

No, we can use cascading in this case... Let's assume:
```java
@OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true)
@JoinColumn(name = "ID_CARD_ID")
private IdCard idCard;
```
It says: *"if you persist the `Person` persist also `IdCard`"* so we can remove the extra `idCardRepository.save(idCard)`':
```java
 public Person addIdCardToPerson(Long personId, PostIdCardRequest postIdCardDtoRequest) {
        // ...
        IdCard idCard = new IdCard();
        idCard.setSerialNumber(postIdCardDtoRequest.getSerialNumber());
        idCard.setValidUntil(postIdCardDtoRequest.getValidUntil());
        idCard.setPublishedBy(postIdCardDtoRequest.getPublishedBy());
        
        existingPerson.setIdCard(idCard);
        return personRepository.save(existingPerson); // <-- idCard will also be persisted
    }
```
without cascading we would see
> *org.hibernate.TransientPropertyValueException: object references an unsaved transient instance - save the transient instance before flushing*

as we want to persist a `Person` which has `idCard` not present in database

</details>

<details>
<summary>Difference between `orphanRemoval` and `Cascade.REMOVE`</summary>

From stackoverflow: https://stackoverflow.com/questions/4329577/how-does-jpa-orphanremoval-true-differ-from-the-on-delete-cascade-dml-clause

> `orphanRemoval` has nothing to do with `ON DELETE CASCADE` <br>
> `orphanRemoval` is an entirely **ORM-specific thing**. It marks "child" entity to be removed when it's no longer referenced from the "parent" entity, e.g. when you remove the child entity from the corresponding collection of the parent entity.<br>
> `ON DELETE CASCADE` is a **database-specific** thing, it deletes the "child" row in the database when the "parent" row is deleted.<br>

> In other words, it about disconnecting the relation between objects in JPA. So: 
> ```java
> person.setIdCard(null);
> person.save();
> ```
> will remove previously assigned `IdCard` only if `orphanRemoval` is `true`. Otherwise, event if `cascade=REMOVE` is set, it will not take place as setting a `null` idcard is not a removal of a `Person`.
</details>


#### @OneToOne - bidirectional relationship
Work in both directions

Add @OneToOne on the other side (in this case in IdCard)
```java
    @OneToOne(mappedBy = "idCard")
    @JsonBackReference
    private Person person;
```
When adding to context --> add both dependencies in both directions (probably will work but not guaranteed)
```java
    // ...
    IdCard idCard = existingPerson.getIdCard();
    idCard.setSerialNumber(putIdCardDtoRequest.getSerialNumber());
    idCard.setValidUntil(putIdCardDtoRequest.getValidUntil());
    idCard.setPublishedBy(putIdCardDtoRequest.getPublishedBy());
    
    idCard.setPerson(existingPerson); // \ 
    existingPerson.setIdCard(idCard); // /
    
    return personRepository.save(existingPerson); // PERSIST cascade will also persist the IdCard
```


<details>
<summary>Other ways to implement <code>@OneToOne</code> relations</summary>

> * Using Shared Primary Key using `@PrimaryKeyJoinColumn`
> * Using extra joining table using `@JoinTable` - helps to avoid `null` values in relation-related fields.
</details>

### @OneToMany
For example:
* one `EmploymentCertificate` belongs to a single `Person`, but...
* ... one `Person` may have many `EmploymentCertificates`

<details>
<summary>What three(3) options of implemention do we have?</summary>

* Two unidirectional - depending which side owns the relationship
* Bidirectional
</details>

#### First unidirectional approach - no collection:

<details>
<summary>How to do it and how hibernate creates it?</summary>

```java
import jakarta.persistence.JoinColumn;

public class EmploymentCertificate {

    // ...

    @ManyToOne
    @JoinColumn("person_id") // <-- not necessary, it would be default behaviour
    private Person person;
}
```
</details>

#### Second unidirectional approach - with collection:

<details>
<summary>How to do it and how hibernate creates it?</summary>

The owner can be only one (where the `@ManyToOne` is defined), but we can also use the `@OneToMany` which is always on oposite side of relation

```java
public class Person {

    @OneToMany
    private Set<EmploymentCertificate> certificates;
}
```

> ***NOTE***
> Do not do it :) In this case Hibernate will create an extra join table as it does not know where to put foreign key. So in practice we will have many to many. 

<details>
<summary>How to avoid extra table?</summary>

> We can use @JoinTable (only case where JoinTable is place in non-owner side of relation) to indicate a column with foreign key:
> 
> ```java
> import jakarta.persistence.JoinColumn;
> 
> public class Person {
> 
>     @OneToMany
>     @JoinTable(name = "person_id")
>     private Set<EmploymentCertificate> certificates;
> }
> ```
</details>
</details>

#### Bidirectional relationship
```java
@OneToMany(mappedBy = "person", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
private Set<EmploymentCertificate> certificates;
```
```java
@ManyToOne
@JoinColumn(name = "post")
private Person person;
```

* Important:
  * *mappedBy* - in bidirectional relations always use it (on non-owner side)
  * *setting bidirectional relation in context* - Always (if not cascaded) add both relation "directions". It could work without it, but it not guaranted by ORM.
  * *cascading* - we say 'if you persist person, persist also certificates'
    * Depending on the side of relation we may want to define different cascading rules

<details>
<summary>Fetching in <code>@OneToMany</code></summary>

* For single field - default is `EAGER`; LAZY not recommended as it creates extra query instead of join sql from beginnig
* For collections - default is `LAZY`; it makes sense as we usually do not need to load all data from collection
</details>

### @ManyToMany
Basic to approches are uni- and bidirectional relationships

#### @ManyToMany uni-directional

Basic implemetation of uni directional relation of ManyToMany is as follows: 

```java
public class Person {
    // ...
  
    @ManyToMany
    @JoinTable(
        name = "person_address",
        joinColumns = @JoinColumn(name = "person_id"),
        inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    private Set<AddressUniDirectional> addresses;
}
```

#### @ManyToMany bi-directional
Here we can just add the same `@ManyToMany` mapping on the other side of relation (in `Address`) remembering about `mappedBy` being specified in the 'oposite' side of relation (`JoinTable` always on *ownership* side)

```java
public class Person {
  // ...
  @ManyToMany
  @JoinTable(
          name = "person_address",
          joinColumns = @JoinColumn(name = "person_id"),
          inverseJoinColumns = @JoinColumn(name = "address_id")
  )
  private Set<Address> addresses;
}


public class Address {
    // ...
    @ManyToMany(mappedBy = "addresses")
    private Set<Person> persons;
}
```

When adding, of course, we need to link all relations to JPA entities. 

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
@OneToMany(mappedBy = "person", cascade = {CascadeType.PERSIST, CascadeType.MERGE} /*, orphanRemoval=true */)
private Set<EmploymentCertificate> certificates;
```
and
```java
@ManyToOne
private Person person;
```
When we delete a person by id: 
```java
public void removeCertificate(EmployeeCertificate cert) {
    certificatesInPerson.remove(cert);
    cert.setPerson(null);
    // commit...
}
```

### Using lombok with <code>@Entity</code> classes


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


