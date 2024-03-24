## Spring Actuator - basic notes
---------------------
### Basics & Demo
Actuator can give us many information about the application. It has many endpoint configured out of
the box we can just use, like `/actuator/info` endpoints. 

We, as developers, can use it, configure it, like 
by saying which info should be visible or not, or even add our own `InfoContributor` which adds 
extra information for default actuator's info response. 

We can also say "in health endpoint don't include information about enabled java":
> `management.info.java.enabled=false`

or "set url to info to 'appinfo'":
> `management.endpoints.web.path-mapping.info=/appinfo`

#### Example
An example of `/actuator/info` endpoint including extra `"personInfo"` section added by `InfoContributor`:
```json
{
"app": {
  "description": "Sample static info"
},
"build": {
  "version": "1.0.0-SNAPSHOT"
},
"custom": {
  "static": {
    "information": "This is the custom info from actuator"
  }
},
"java": {
  "version": "17.0.5",
  "vendor": {
    "name": "Eclipse Adoptium",
    "version": "Temurin-17.0.5+8"
  },
  "runtime": {
    "name": "OpenJDK Runtime Environment",
    "version": "17.0.5+8"
  },
  "jvm": {
    "name": "OpenJDK 64-Bit Server VM",
    "vendor": "Eclipse Adoptium",
    "version": "17.0.5+8"
  }
},
"personInfo": {
  "description": "This is the custom info from actuator",
  "version": "1.0.0-SNAPSHOT",
  "personCount": "0"
}
}
```



### More information

Possible endpoint config: 
* "enable" / "disable"
* "expose" or "do not expose"

<details>
<summary>When endpoint is autoconfigured? </summary>

When it is available - so it is **enabled** and **exposed**. 
</details>

<details>
<summary>What does it mean to <b>expose</b> an actuator's  endpoint?</summary>

Make them remotely accessible over HTTP or JMX
</details>

<details>
<summary>Default enabled/disabled?</summary>

All endpoint are enabled by default.
</details>

<details>
<summary>Changing default config? </summary>

Yes. We can use `management.endpoints.enabled-by-default`
</details>

<details>
<summary>Default exposure settings?</summary>

By default only health is exposed (via HTTP and JMX). 
</details>


<details>
<summary>How to expose?</summary>

> * We expose endpoints per technology and then...
> * ... we specify which endpoint are included or excluded from exposure
> * Example: 
>   * `management.endpoints.jmx.exposure.include=health,info` exposes info and health via JMX 
>   * `management.endpoints.web.exposure.include=*` exposes all endpoint via HTTP
</details>

### Custom configurations...
We can configure different endpoints of actuator. For example, if info endpoint is enabled and exposed, we can say: 
* add some static info: `info.app.description=Sample static info do return from /info endpoint`
* we can enable default `InfoContributor` implementations like `JavaInfoContributor` : `info.app.java.enable`
* Define custom info by implementing `InfoContributor`

#### Indicators
Actually, many things can be configured/extended for different actuator endpoints by implementing corresponding `*Indicator` interfaces