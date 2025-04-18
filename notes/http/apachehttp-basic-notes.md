### Basic notes about Apache HTTP Client

## Consuming a response
<details>
<summary>What does it mean to consume?</summary>

> The content (response body) of the response has to be fully read (all data). 
</details>

<details>
<summary>Why so important to consume</summary>

> It is about connections. Not consumer response makes it impossible for the connection the be resused by other requests.
> Moreover, in such state connection will be removed / destroyed by connection manager. 
</details>

## Other things: 
<details>
<summary>What is the <code>setValidateAfterInactivity</code> method for?</summary>

> * It sets how long a connection has to be in IDLE state to be revalidated after next requests.
> * Validation checks if connection is still ok, and not stale...
</details>

<details>
<summary>What is a stale connection?</summary>

> It is a connection which client believes its opened, but it is not - server already closed it. 
</details>

<details>
<summary>Whats a different between <code>setConnectionTimeout</code> and <code>setSocketTimeout</code>?</summary>

> * ConnectionTimeout - setting is used to prevent the client from waiting indefinitely to establish a connection with a server.
> * SocketTimeout: "read timeout," is the maximum time of inactivity between two consecutive data packets. This setting helps in ensuring that the client doesn’t wait indefinitely for a response from the server.
</details>

<details>
<summary>And what about <code>setTimeToLive</code>?</summary>

> It sets how long the connection (regardless of state) may be kept in the pool - it good to recreate connections from time to time 
> * security (attacker's connection would not exist for eternity)
> * resource managenment - proactive refresh helps maintain the overall health and efficiency of the connection pool
> * freshness - beneficial in environments where network conditions or server configurations may change frequently
</details>
