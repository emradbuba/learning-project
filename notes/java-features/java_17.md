## Java 17 (2o21) LTS

### Pattern matching for Switch

```java
public record Car(int maxSpeed) { }
public record Person(String firstName) { }
public record Temperature(int celsius) { }
```
```java
return switch(object) {
       case Car c -> "Car: " + c.getMaxSpeed();
       case Person p -> "Person: " + p.getFirstName();
       case Temperature t -> "Temp: " + t.getCelsius();
       default -> "Unknown";
}
```
or
```java
return switch(object) {
       case Car c && (c.getMaxSpeed() > 210) -> "Fast car: " + c.getCarName();
       case Person p -> "Person: " + p.getFirstName();
       case Temperature t && (t.getCelsius() > 30) -> "Hot weather: " + t.getCelsius();
       default -> "Standard";
}
```