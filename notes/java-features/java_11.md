## Java 11 (2o18) LTS
 
### No free LTS
Starting from Java 11, there is no free of charge Java for commercial use. Fortunatley there is OpenJDK provided by Oracle.

### New methods
#### --- String
`indent()`, `strip()`, `repeat()`, `lines()`

#### --- Files 
`Files.readString(path)`, `Files.writeString(path)`
#### --- Collection.toArray()
```java
List names = Arrays.asList("Radek", "Andrew"); 
String[] namesArray = names.toArray(String[]::new);
```  
#### --- not Predicate
`.filter(not(String::isEmpty))`

#### --- HTTPClient
New `HTTPClient` from Java 9 becomes a standard