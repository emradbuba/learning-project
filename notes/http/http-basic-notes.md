# HTTP Basic Notes: 

#### Headers (request and response)
<details>
<summary><code>Connection</code></summary>

Decide if network connection should be closed or not after the request
</details>

<details>
<summary><code>Via</code></summary>

Header used in TRACE requests - header updated between hops
</details>

#### Headers (request)

<details>
<summary><code>Accept-</code></summary>

What media types are acceptable by the client
</details>

#### HTTP Connection

<details>
<summary>When it is established</summary>

Before any calls are possible. 
</details>

<details>
<summary>What protocol is used when establishing a connection? </summary>

>TCP. 
>
>> <details>
>> <summary>How TCP sends HTTP data? </summary>
>> 
>> In IP Packages
>> </details>
>
>> <details>
>> <summary>Who ensures data will be delivered in correct order? </summary>
>>
>> The TCP itself.
>> </details>
>
>> <details>
>> <summary>Correlation between TCP / IP / HTTP </summary>
>>
>> HTTP is a web protocol using TCP which in turn is using the IP
>> 
>> </details>

</details>

<details>
<summary>How an HTTP application is identified so we can call it?</summary>

> IP address + port
</details>

<details>
<summary>How the connection steps work?</summary>

> Map address to IP (DNS)
> establish connection with the service
> send request
> server processes the request and returns response
> Close connection to server
</details>

<details>
<summary>What is a persistent connection? </summary>

> Just a connection with `Connection: Keep-Alive` which is default
</details>

<details>
<summary>When persistent connection is closed, by whom and how (HTTP 1.1 / 2.0) ?</summary>

> When client or server decides so. 
> By sending a header `Connection: close`
> For HTTP 2.0 due to multiple stream inside a connection, it by sending `GOAWAY` frames which informs the currect streams can do its work, but new stream will not be created
> After all HTTP 2.0 streams are closed, then entire HTTP connection is closed,
</details>

### HTTP 1.1 / HTTP 2.0

<details>
<summary>Main differences? </summary>

> Stream are multimplexed in a scope of a TCP single connection.
</details>

https://developer.mozilla.org/en-US/docs/Web/HTTP/Overview
https://developer.mozilla.org/en-US/docs/Web/HTTP/Messages#http2_messages
https://developer.mozilla.org/en-US/docs/Web/HTTP/Authentication
https://developer.mozilla.org/en-US/docs/Web/HTTP/Connection_management_in_HTTP_1.x
https://developer.mozilla.org/en-US/docs/Web/HTTP/Redirections
https://developer.mozilla.org/en-US/docs/Web/HTTP/MIME_types
https://developer.mozilla.org/en-US/docs/Web/HTTP/Content_negotiation
https://developer.mozilla.org/en-US/docs/Web/HTTP/Proxy_servers_and_tunneling
<br><br>
https://developer.mozilla.org/en-US/docs/Web/Security/Practical_implementation_guides
