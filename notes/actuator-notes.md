## Something about Spring Actuator
Possible endpoint config: 
* enable / disable
* expose or not

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