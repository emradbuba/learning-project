## Java 10 (2o18)

### Local variable type inference (<code>var</code>)
> * `var myName = "Rejdi";` -- compiler infers the type of `myName` by the right side of an expressions so: 
> * `assertTrue(myName instanceof String)` passes 


> <span style='color:#0af'>**INFO**</span> <br>
> * For backwards compatibility `var` is not a keyword so we can say `var var = "MyVariable"`
> * Use wisely - `var` can make code less readable

### Unmodifiable collections

Java 10 introduced a `copyOf` on collections (List, Map, Set) which enables to say: 
```java
List<String> copiedReadOnlyList = List.copyOf(originalList);
copiedReadOnlyList.add("new string"); // <-- error
```
 as well as a collector in streams to collect to unmodifiable collections: 
 ```java
Set<Item> readOnlySet = list.stream()
    .filter(ItemValidator::isValid)
    .collect(Collectors.toUnmodifiableSet());
 ```

### Docker container awerness
// to read, to add...

### Changes in release approach
<details>
<summary>How often a new release of Java?</summary>

> Each 6 months.
</details>

<details>
<summary>How long supported?</summary>

> 6 month - so until next release
</details>

<details>
<summary>Long term support (LTS)</summary>

> Extra marked release to be supported for 3 years.
</details>
