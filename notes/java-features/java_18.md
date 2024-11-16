## Java 18 

### Pattern matching (second review)

#### Dominance checking: 
```java
return switch(object) {
    case String s -> "this is string";
    case CharSequence cs -> "this is charSeq";
    default -> "Unknown";
}
```
will produce the error: 
`java: this case label is dominated by a preceding case label`

#### Exhaustiveness 
The `switch` statement must handle all possible values / cases explicitly
```java
return switch(value) {
    case Integer i -> "int: " + i;
    case Double d -> "double: " + d;
    // no default --> compile error
}
```