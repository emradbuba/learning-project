## Spring JPA - relations


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
  private IdCard idCardEntity;

}
```

Adding the entry (similar with update):
```java
@Service
public class IdCardService {
    
    public Person addIdCardToPerson(Long personId, PostIdCardRequest postIdCardDtoRequest) {
        //...
        IdCard idCardEntity = new IdCard();
        idCardEntity.setSerialNumber(postIdCardDtoRequest.getSerialNumber());
        idCardEntity.setValidUntil(postIdCardDtoRequest.getValidUntil());
        idCardEntity.setPublishedBy(postIdCardDtoRequest.getPublishedBy());

        existingPersonEntity.setIdCard(idCardEntity);
        idCardRepository.save(idCardEntity); // no cascading, so we need to persist this entity too...
        return personRepository.save(existingPersonEntity);
    }
}
```

Delete logic:
```java
public Person deleteIdCardFromPerson(Long personId) {
    Person existingPersonEntity = getPersonByIdOrThrow(personId);
    if (existingPersonEntity.getIdCard() != null) {
        existingPersonEntity.setIdCard(null); // <-- !
        return personRepository.save(existingPersonEntity);
    }
    throw new PersonNotFoundAppException("No personEntity found for given personId: " + personId);
}
```

<details>
<summary>Will above example remove the <code>idCardEntity</code> from database?</summary>

No - there default `orphanRemoval` is set to `false`, so when existing `idCardEntity` in "unrelated" from personEntity (setting `null`) it is still in database
`orphanRemoval` - `false` allows to *preserve* an entry in the database ***even if parent is removed***
It will be changed if we modify relation:

```java
@OneToOne(orphanRemoval = true)
@JoinColumn(name = "ID_CARD_ID")
private IdCard idCardEntity;
```

Now, when idCardEntity is set to `null` in personEntity, the orphaned `idCardEntity` is also removed from database as it's not referenced any more

</details>

<details>
<summary>Do we need to save/persist all entities each time?</summary>

No, we can use cascading in this case... Let's assume:
```java
@OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true)
@JoinColumn(name = "ID_CARD_ID")
private IdCard idCardEntity;
```
It says: *"if you persist the `Person` persist also `IdCard`"* so we can remove the extra `idCardRepository.save(idCardEntity)`':
```java
 public Person addIdCardToPerson(Long personId, PostIdCardRequest postIdCardDtoRequest) {
        // ...
        IdCard idCardEntity = new IdCard();
        idCardEntity.setSerialNumber(postIdCardDtoRequest.getSerialNumber());
        idCardEntity.setValidUntil(postIdCardDtoRequest.getValidUntil());
        idCardEntity.setPublishedBy(postIdCardDtoRequest.getPublishedBy());
        
        existingPersonEntity.setIdCard(idCardEntity);
        return personRepository.save(existingPersonEntity); // <-- idCardEntity will also be persisted
    }
```
without cascading we would see
> *org.hibernate.TransientPropertyValueException: object references an unsaved transient instance - save the transient instance before flushing*

as we want to persist a `Person` which has `idCardEntity` not present in database

</details>

<details>
<summary>Difference between `orphanRemoval` and `Cascade.REMOVE`</summary>

From stackoverflow: https://stackoverflow.com/questions/4329577/how-does-jpa-orphanremoval-true-differ-from-the-on-delete-cascade-dml-clause

> `orphanRemoval` has nothing to do with `ON DELETE CASCADE` <br>
> `orphanRemoval` is an entirely **ORM-specific thing**. It marks "child" entity to be removed when it's no longer referenced from the "parent" entity, e.g. when you remove the child entity from the corresponding collection of the parent entity.<br>
> `ON DELETE CASCADE` is a **database-specific** thing, it deletes the "child" row in the database when the "parent" row is deleted.<br>

> In other words, it about disconnecting the relation between objects in JPA. So:
> ```java
> personEntity.setIdCard(null);
> personEntity.save();
> ```
> will remove previously assigned `IdCard` only if `orphanRemoval` is `true`. Otherwise, event if `cascade=REMOVE` is set, it will not take place as setting a `null` idcard is not a removal of a `Person`.
</details>


#### @OneToOne - bidirectional relationship
Work in both directions

Add @OneToOne on the other side (in this case in IdCard)
```java
    @OneToOne(mappedBy = "idCardEntity")
    @JsonBackReference
    private Person personEntity;
```
When adding to context --> add both dependencies in both directions (probably will work but not guaranteed)
```java
    // ...
    IdCard idCardEntity = existingPersonEntity.getIdCard();
    idCardEntity.setSerialNumber(putIdCardDtoRequest.getSerialNumber());
    idCardEntity.setValidUntil(putIdCardDtoRequest.getValidUntil());
    idCardEntity.setPublishedBy(putIdCardDtoRequest.getPublishedBy());
    
    idCardEntity.setPerson(existingPersonEntity); // \ 
    existingPersonEntity.setIdCard(idCardEntity); // /
    
    return personRepository.save(existingPersonEntity); // PERSIST cascade will also persist the IdCard
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
    private Person personEntity;
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
@OneToMany(mappedBy = "personEntity", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
private Set<EmploymentCertificate> certificates;
```
```java
@ManyToOne
@JoinColumn(name = "post")
private Person personEntity;
```

* Important:
    * *mappedBy* - in bidirectional relations always use it (on non-owner side)
    * *setting bidirectional relation in context* - Always (if not cascaded) add both relation "directions". It could work without it, but it not guaranted by ORM.
    * *cascading* - we say 'if you persist personEntity, persist also certificates'
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