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