## SpringBoot related knowledge

<details>
<summary>What's the difference between <code>@Controller</code> and <code>@RestController</code>?</summary>

> Controller will be better for cases when response assumes that consumer should render the view (for example view name is returned) <br>
> @RestController is a composed annotation that is itself meta-annotated with @Controller and @ResponseBody to indicate a controller whose every method inherits the type-level @ResponseBody annotation and, therefore, writes directly to the response body versus view resolution and rendering with an HTML template.
</details>

<details>
<summary>Is <code>@PostMapping</code> related to <code>@RequestMapping</code>?</summary>

> `@PostMapping` and other are just aggregating annotations which lead to RequestMapping at the end... 
</details>

<details>
<summary>Difference between <code>@ResponseBody</code> and <code>@ResponseEntity</code></summary>

> ResponseEntity represents the response in the app so we can customize it before sending to client like here
> ```java
>  ResponseEntity<String> response = ResponseEntity
>         .status(HttpStatus.OK)
>         .header("Custom-Header", "Custom-Value")
>         .body("Response Body");
> ```

> The `@ResponseBody` annotation tells that the value returned from the annotated method 
> will constituate a body of the response, which will be transformed to JSON/XML according 
> to `produces` attribute. 
</details>

### TODO
* What does the `@Configuration` annotation mean? 