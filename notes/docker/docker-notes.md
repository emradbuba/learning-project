## Docker

### Intial commands overview

#### -- Images --

<details>
<summary>Build an image</summary>

> ```
> # Build an image
> docker image build -t emradbuba/learning-project-repo:tag-1.0.0 .
> docker build --no-cache -t emradbuba/learning-project-repo:tag-1.0.0 .
> ```
</details>

<details>
<summary>Build image when jar and Dockerfile in different directories</summary>

> `docker build -t emradbuba/repo-name:image-name-2024 -f infrastructure/main-app/Dockerfile .`<br>
> Can't COPY anything not from build context (.), so build context must to "." but Dockerfile has to be pointed (-f)
</details>

<br>

<details>
<summary>Push an image to repo</summary>

> ```
> # Push an image
> docker image push emradbuba/learning-project-repo:tag-1.0.0
> 
> docker login  # <-- login to docker
> ```
</details>

<br>

<details>
<summary>List images</summary>

> ```
> docker images / docker image ls
> ```
</details>

<details>
<summary>List "hanging" images</summary>

>```
> docker images -f dangling=true
> ```
</details>

<details>
<summary>Remove image</summary>

> ```
> docker image rm emradbuba/learning-project-repo:tag-1.0.0
> docker image rmi emradbuba/learning-project-repo:tag-1.0.0
> dicker rmi image-id # <-- will try to remove :latest
> ```
</details>

<details>
<summary>Remove "hanging" images</summary>

> `docker images purge`
</details>

#### -- Containers --
 
<details>
<summary>Run a container</summary>

> ```
> $ docker build -t emradbuba/repo-name:image-name-2024
> $ docker container run -d --name web -p 8083:8080 emradbuba/repo-name:image-name-2024
> ```
</details>

<details>
<summary>What if image does not exist?</summary>

> Docker will download it
> <details>
> <summary>Download from?</summary>
> 
> Default location is **DockerHub**.
> </details>
</details>

<details>
<summary>Sample output of docker run</summary>

> ```
> $ docker container run -d --name web -p 8083:8080 emradbuba/repo-name:image-name-2024
> Unable to find image 'emradbuba/repo-name:image-name-2024' locally
> image-name-2024: Pulling from emradbuba/repo-name
> 4abcf2066143: Already exists                                                                                                                                                                                                                                             
> ...
> Digest: sha256:631ece8b2a148f5d345e4481e464d24faeea72b506c3f8737f77e9a34b9814bb
> Status: Downloaded newer image for emradbuba/repo-name:image-name-2024
> 40853ddbe7266be733a476a9b1d10147a0ac45cafa323911420037622c714290 <-- ID OF RUNNING CONTAINER
> ```
</details>

<br> 

<details>
<summary>Check running containers...</summary>

> ```
> $ docker container ls
> CONTAINER ID   IMAGE                                 COMMAND         CREATED          STATUS          PORTS                    NAMES
> 40853ddbe726   emradbuba/repo-name:image-name-2024   "node app.js"   48 seconds ago   Up 47 seconds   0.0.0.0:8083->8080/tcp   web
> ```
</details>


<details>
<summary>Check all containers</summary>

> ```
> $ docker container ls -a
> CONTAINER ID   IMAGE                                  COMMAND         CREATED              STATUS              PORTS                    NAMES
> efc9120bc477   emradbuba/repo-name:image-name-2024b   "node app.js"   About a minute ago   Up About a minute   0.0.0.0:8086->8080/tcp   web3
> 667af8e3133a   emradbuba/repo-name:image-name-2024    "node app.js"   3 minutes ago        Up 3 minutes        0.0.0.0:8084->8080/tcp   web0
> 40853ddbe726   emradbuba/repo-name:image-name-2024    "node app.js"   7 minutes ago        Up 23 seconds       0.0.0.0:8083->8080/tcp   web
> ```
</details>

<br>

<details>
<summary>Start/Stop container(s)</summary>

> ```
> $ docker container [start/stop] web web2
> web
> ```
</details>

<details>
<summary>Remove container</summary>

> ```
> docker rm containerId
> ```
</details>

#### -- docker-compose --
<details>
<summary>Sample docker-compose file</summary>

> ```yaml
> networks:
>   learning-app-network:
> 
> volumes:
> learning-app-volume:
> 
> services:
> amq-broker:
> build: . # <-- context, all required files should be here, like Dockerfile
> container_name: activemq-artemis-container
> ports:
> - "8161:8161"
> - "61616:61616"
> volumes:
> - type: volume
> # source: name of the volume on docker host we refer to:
> source: learning-app-volume
> # target: mount point inside container (so test-app-vol will be accessible in container under /activemq-artemis-vol)
> # all data stored in container under /activemq-artemis-vol will be actually stored under learning-app-vol (cross-container-vol)
> target: /activemq-artemis-volume
> ```
</details>

<details>
<summary>What in case of changes in `docker-compose` between runs?</summary>

> ```
>  $ docker-compose up
>  [+] Running 1/2
>   Volume "learning-project_learning-app-volume"  Created                                                                                                                                                                                                                                                      0.0s
>  - Container activemq-artemis                     Recreated                                                                                                                                                                                                                                                    0.1s
>   Attaching to activemq-artemis
>  ```
</details>

<details>
<summary>How docker-compose knows when to rebuild image? </summary>

> * `docker-compose up --build` will always rebuild image
> * Changes in Dockerfile
> * Changes in files use in build (like `COPY`ied or `ADD`ed files)
> * Changes in docker-file
> * 
</details>

### Exposing / publishing ports: 
<details>
<summary>Is there a difference between <code>EXPOSE</code> and <code>-p 8080:8090</code> ?</summary>

> Yes - there is. <br>
> Difference between exposing (`EXPOSE`) and publishing (`-p 8080:8090`)
> is well [described here](https://www.mend.io/free-developer-tools/blog/docker-expose-port/)
> 
> * **Exposing** - #intern #network - is for inter-container communication purposes (like exposing 6379 port on redis container to be accessible for other containers in network)
> * **Publishing** - #outside #world - it produces a firewall rule that binds a container port to a port on the Docker host, ensuring the ports are accessible to any client that can communicate with the host.
</details>

### Docker cache
```
$ docker system prune -a
WARNING! This will remove:
- all stopped containers
- all networks not used by at least one container
- all images without at least one container associated to them
- all build cache
y
```

<details>
<summary>When docker uses cache?</summary>

> * Changes in DockerFile Layers, like `ADD`, `COPY`, `RUN`...
> * Invalidates cache - consequtive layers
> * 
</details>

