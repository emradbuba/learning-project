## RestTemplate - encoding / charsets

### How does RestTemplate flow look like:
#### Request creation 
- specify request body (like a json string)
- specify headers, like Content-Type
Converters: 
- RestTemplate chooses a message converter using: 
  - info about request body
  - info about content-type
- Serialization: 
  - MessageConverter serializes the request body
    - for JSON objects: MappingJackson2HttpMsgConverter with UTF-8 by default
    - for String: StringHttpMsgConverter with UTF-8 by default
- Converter encoding vs Content-Type
  - these have to match, otherwise server can face a WTF and misinterpret data

#### Sending:
- Content Type is sent to server so it knows the encoding
- Transmission: 
  - Before transmitting, the serialized data is written to request stream
  - In case of non-ASCII characters, encoding ensures they are correctly represented (like with UFT-8)

#### Server
- Server check the ContentType. If missing or incorrect, it can lead to problems, misinterpretation, strange characters etc...

#### RestTemplate - response
- RestTemplate checks the ContentType and determines encoding/charset if specified
- Based on content type, RestTemplate pick a message converter
- Converter converts the response body using specified charset (Content-Type) or UTF-8 by default

#### Deserialization
- RestTemplate, via HttpMessageConverter, deserializes the decoded content info Java object. 
- On this step, in case of misinterpretation, special characters in Java object may be corrupted.

------
#### Where Charset Plays a Role

During Request Serialization:
> Encoding of the request body based on the specified or default charset.
> Sending the correct Content-Type header to inform the server about the charset.

During Response Parsing:
> Decoding the response body according to the charset specified in the Content-Type header of the response.

Error Scenarios:
> Mismatched charsets between client and server can result in:
> Garbled text in the request or response.
> Serialization/Deserialization failures.
> Unpredictable behavior, especially with non-ASCII characters.