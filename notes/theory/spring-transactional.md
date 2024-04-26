## @Transaction annotation

> TODO
>> When to use READ_UNCOMMITED and SNAPSHOTs --> [StackOverflow](https://dba.stackexchange.com/questions/206485/best-situation-to-use-read-uncommitted-isolation-level)

### Some basic information

---

<details>
<summary>What parameters/properties can be specified for a transaction using the `@Transactional` annotation?</summary>

- Propagation mode
- Isolation level
- Timeout
- Rollback conditions
- Transaction manager
- Read-only

</details>

<details>
<summary>How is proxy pattern involved in Spring Transactions?</summary>

It is about wrapping code in the method with extra logic for creating, **commit**ing or **rollback**ing the transaction. 
This wrapper is a proxy. That's why `@Transactional` method has to be invoked from outer scope and not, for example, by other method in the same class - in this case proxy will not work

Example of transaction wrapping: 
```java
createTransactionIfNecessary();
try {
    callMethod();
    commitTransactionAfterReturning();
} catch (exception) {
    completeTransactionAfterThrowing();
    throw exception;
}

```
</details>

<details>

<summary>
`@Transactional` on class level? What about private methods?

</summary>

```
@Transactional
@Service
public class ServiceTransactional {
    
    public void saveValue() {
        // is this method transactional? 
    }
    
    private void saveValue() {
        // is this method transactional? 
    }
} 
```
Spring applies the class-level annotation to all public methods of this class that we did not annotate with @Transactional.
However, if we put the annotation on a private or protected method, Spring will ignore it without an error.

</details>


### Propagation

---

<details>
<summary>When `propagation` it is important?</summary>

It comes into play when Spring asks a TransactionManager to get or create a transaction accodring to propagation mode.
</details>

<details>
<summary>Are all propagation levels always supported?</summary>

Depending on the TransactionManager, some propagations may not be supported
</details>

#### Propagation levels

<details>
<summary>`Propagation.REQUIRED`</summary>

In other words: **'Join existing or create new'**
Spring knows that there must be a transaction, so it joins the current one if exists or creates a new one otherwise.
</details>

<details>
<summary>`Propagation.SUPPORTS`</summary>

In other words: **'Join existing or dont be transactional'**
Spring join the existing active transaction. It there is not, it **won't** create new
</details>

<details>
<summary>`Propagation.MANDATORY`</summary>

In other words: **'Join existing or throw if there is no'**
</details>

<details>
<summary>`Propagation.NEVER`</summary>

In other words: **'Throw if there is an active transaction'**
</details>

<details>
<summary>`Propagation.NESTED`</summary>

For `NESTED` propagation, Spring checks if a transaction exists, and if so, it marks a save point. 
This means that if our business logic execution throws an exception, then the transaction rollbacks to this save point. If there’s no active transaction, it works like REQUIRED.
</details>



<details>
<summary>What is the **default** propagation level?</summary>

**REQUIRED** is a default one, so `@Transactional(propagation = Propagation.REQUIRED)` and `@Transactional` are equivalents.
</details>

### Isolation

<details>
<summary>Basic role of isolation</summary>

Isolation levels are required to separate changes by concurrent transactions.
Isolation levels manage how changes are visible to other transactions.
</details>

<details>
<summary>Role of `TransactionManager::setValidateExistingTransaction`</summary>

In the normal flow, the isolation only applies when a new transaction is created. Thus, if for any reason we don’t want to allow a method to execute in different isolation, we have to set `TransactionManager::setValidateExistingTransaction` to `true`:

```java
if (isolationLevel != ISOLATION_DEFAULT) {
    if (currentTransactionIsolationLevel() != isolationLevel) {
        throw IllegalTransactionStateException
    }
}
```
</details>

<details>
<summary>Isolation levels</summary>

> <details>
> <summary>Isolation.DEFAULT </summary>
> It means "use the default isolation level of **RDBMS**". 
> </details>

> <details>
> <summary>Isolation.READ_UNCOMMITTED </summary>
> Basically we can read all uncommitted data made by other concurrent transactions.
> It means, we can read data which will be updated/removed soon. We could also get different results of group queries when re-reading data. 
> </details>

> <details>
> <summary>Isolation.READ_COMMITTED </summary>
>
> `READ_COMMITTED`, prevents dirty reads.
> The rest of the concurrency side effects could still happen. So uncommitted changes in concurrent transactions have no impact on us, but if a transaction commits its changes, our result could change by re-querying
> </details>

> <details>
> <summary>Isolation.REPEATABLE_READ </summary>
> 
> * No dirty reads
> * Row re-query will get same results
> * Prevents lost update - no way to simultaneous access to the row
>
> Still it is possible to get different results of re-executed range queries. 
> </details>

> <details>
> <summary>Isolation.SERIALIZABLE </summary>
> To describe...
> </details>

</details>

<details>
<summary>What is the default isolation level in Spring?</summary>

`Isolation.DEFAULT` - that's why we should be careful when changing the underlying database provider.
</details>

<details>
<summary>Where READ_UNCOMMITTED is not supported? </summary>

**Postgres** and **Oracle** do not support `Isolation.READ_UNCOMMITTED` level. If set, the `READ_COMMITTED` will be applied instead.
</details>



