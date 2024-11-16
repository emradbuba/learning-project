## Java 14 (2o2o)

### Records
```
public record Person(String name, String surname) {
}
```

<details>
<summary>What is a main goal of a record?</summary>

It is a **class** that act as transparent **carriers** for **immutable data**.
</details>
 

<details>
<summary>What do we get out of the box?</summary>

> * fields
> * public canonical constructor
> * getter methods
> * equals() and hashCode()
> * toString()
</details>


<details>
<summary>What the generated fields are?</summary>

`private` and `final`
</details>


<details>
<summary>What is a canonical constructor?</summary>

Generated constructor initializing all fields in the record.
</details>


<details>
<summary>Is it possible to add different constructors?</summary>

Yes. But canonical constructor has to be invoked. 
</details>
 

<details>
<summary>What about visibility level of an explicit canonical constructor?</summary>

It cannot be more restrictive than record itself (Java 14). 
</details>
 
#### Compact constructor

```
public record Person(String name, String surname) {
    Person {
        Objects.requireNotNull(name);
        Objects.requireNotNull(surname);
    }
}
```

<details>
<summary>What is a compact constructor?</summary>
 
> A 'placeholder' for code to be executed before the canonical constructor's initialization job.
> This compact logic is invoked even implicitly as canonical constructor is always invoked.
</details>

<details>
<summary>What about using <code>this</code> in canonical constructor?</summary>

> All canonical constructor parameters are visible, but we cannot use `this` as this.xxx are not yet initialized (and such warning we would see in this situation).
</details>
 

<details>
<summary>So when to use Compact constructor?</summary>

> * Validation of parameters
> * Normalization of parameters or modifying them in some way before they are used to initialize record's fields.
</details>

<details>
<summary>Extra usage for improving immutability?</summary>

> By default record is immutable but as shallow-mode. Compact constructor can be used to implement deep-copy approach.
</details>
 
### Switch Expressions

```
String value = switch(parameter) {
    case "value1": yield "ONE";  // yield --> 'return from switch'
    case "value2": yield "TWO";
    case "value3": yield "THREE";
    default: yield "UNDEFINED";   
}
```

```
String value = switch(parameter) {
    case "value1" -> "ONE";
    case "value2" -> "TWO";
    case "value3" -> "THREE";
    case "value5", "value6", "value7" -> "OVER_5" 
    default: -> "UNDEFINED";
}
```

### Pattern matching instanceof
```java
if (myObject instanceof String string) {
    doSthWithString(string);    
}
```

We can also use the `string` in the same if: 
```java
if (myObject instanceof String string && string.startsWith("test")) {
    handleTestString(string);
}
```
