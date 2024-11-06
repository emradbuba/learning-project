## Spring JPA - real life use cases

### Adding DB connection via JPA to existing app

<details>
<summary>First app run after adding JPA - how to prevent database from changes? </summary>

> * `spring.jpa.hibernate.ddl-auto` set to `none` (it's a springboot feature but eventually lands in Hibernate (`hibernate.hbm2ddl.auto`))
>  <details>
>  <summary>What's the default here? </summary>
>  
>  > Depending on DB - SpringBoot checks if we have embaded database or not (like H2 or not (Oracle)) and takes `create-drop` or `none`. But I would say I should specify it always...
>  </details>
>
>  <details>
>  <summary>What other values of `ddl-auto`? </summary>
> 
>  > * `create` - remove and creates from scratch
>  > * `update` - tries to add column tables which are not yet in db; never deletes anything
>  > * `create-drop` - creates and at the end drops tables (unit testing suitable)
>  > * `validate` - validate if tables and columns exist or not (exception)
>  > * `none` - no actions (production desired state and default for non embedded (Derby, H2, ...) databases
>  </details>
>
> * `import.sql` - Hibernate feature runs on startup - nice for testing / demo / dev but not on prod ;) 
> * `schema.sql` and `data.sql` - JDBC Datasource initializer feature
</details>
