# Basic Terms 
## Context
<details>
<summary>What is the relation between <code>BeanFactory</code> and <code>ApplicationContext</code>?</summary>

* `BeanFactory` interface provides an advanced configuration mechanism capable of managing any type of object. We can say it provides some basic configuration framework and basic functionality whereas `ApplicationContext` adds more enterprise features.  
* `ApplicationContext` is a sub-interface of `BeanFactory` and adds sum even more advanced things.
</details>

<details>
<summary>What are IoC Container, <code>ApplicationContext</code>, <code>BeanFactory</code>, Configuration Metadata?</summary>

Spring IoC container is represented by `ApplicationContext` (which is an extension of `BeanFactory`).
Container manages, creates and stores Spring beans according to some configuration metadata (xml, annotations, java code)
</details>

<details>
<summary>Apart from XML what kind of configuration metadata do we have</summary>

* Java based configuration (using `@Bean` in `@Configuration` class, `@Import`, `@DependsOn`...)
* Annotation based configuration
</details>

## Spring Beans
<details>
<summary>What is a Spring bean? </summary>

As Spring documentation says: 
> In Spring, the objects that form the backbone of your application and that are <b>managed by the Spring IoC container</b> are called beans. A bean is an object that is instantiated, assembled, and managed by a Spring IoC container. Otherwise, a bean is simply one of many objects in your application. Beans, and the dependencies among them, are reflected in the configuration metadata used by a container.
</details>

<details>
<summary>What is a <code>BeanDefinition</code>?</summary>

* This is a recipe how bean should be created in context. We can say that: <br>
* **Configuration Metadata** --> `BeanDefinition` describing a bean creation rules. <br>
* The container looks at the recipe for a named bean when asked and uses the configuration metadata encapsulated by that bean definition to create (or acquire) an actual object.
</details>

### Beans scope

* **Singleton** bean - always same instance returned from context _(usage: stateless beans)_
* **Prototype** - each time a `getBean()` is called a new instance is returned _(usage: stateful beans)_
* **Session/Request**

<details>
<summary>How Session/Request bean behaves when injected into a singleton?</summary>

When such bean is injected Spring creates a proxy and injects it into a field. In that way, each invocation of injected bean refers to correct request/session as is created only if not exist in this scope. 
</details>

<details>
<summary>What about prototype cleanup?</summary>

In case of proto beans, context initializes, creates and returns bean, but does not care about it anymore after already returned. <br>
It means, user should remember to clean up after such prototype beans (for example expensive resources) cause Spring will not do any cleanup here... <br>
See: [bean post-processor](https://docs.spring.io/spring-framework/reference/core/beans/factory-extension.html#beans-factory-extension-bpp)
</details>

<details>
<summary>How to inject a prototype bean in a singleton?</summary>

See the method injection especially the `@Lookup` annotations which instructs that method should return an instance of a bean (return type) to the caller - if it is a prototype, each time a new instance will be returned.
```java
@Component
public class StudentServices {
    // ...
    @Lookup
    public SchoolNotification getNotification() {
        return null; // <-- body irrelevant as spring overrides it...
    }
}
```
</details>

## Autowiring
<details>
<summary>What is autowiring in Spring?</summary>

Let's say it is the ability of injecting dependencies between beans by Spring Container inspecting the context.
</details>

<details>
<summary>Pros and Cons</summary>

Described in more details in [official documentation](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-autowire.html#beans-autowired-exceptions)
</details>
