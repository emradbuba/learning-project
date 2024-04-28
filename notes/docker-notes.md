## Docker - basic notes

### Intial commands overview
<details>
<summary>Basic Image commands</summary>

```
# Build an image
docker image build -t emradbuba/learning-project-repo:tag-1.0.0 .
docker build --no-cache -t emradbuba/learning-project-repo:tag-1.0.0 .

# Login to you dockerHub repo
docker login 

# Push an image
docker image push emradbuba/learning-project-repo:tag-1.0.0

docker image ls
docker image rm emradbuba/learning-project-repo:tag-1.0.0
```
</details>

<details>
<summary>Basic Container commands</summary>

Staring a container - if image does not exists, docker tries to download it.
Default repo is the DockerHub.
```
$ docker container run -d --name web -p 8083:8080 emradbuba/repo-name:image-name-2024
Unable to find image 'emradbuba/repo-name:image-name-2024' locally
image-name-2024: Pulling from emradbuba/repo-name
4abcf2066143: Already exists                                                                                                                                                                                                                                             
...
Digest: sha256:631ece8b2a148f5d345e4481e464d24faeea72b506c3f8737f77e9a34b9814bb
Status: Downloaded newer image for emradbuba/repo-name:image-name-2024
40853ddbe7266be733a476a9b1d10147a0ac45cafa323911420037622c714290 <-- ID OF RUNNING CONTAINER
```

Checking current containers:
```
$ docker container ls
CONTAINER ID   IMAGE                                 COMMAND         CREATED          STATUS          PORTS                    NAMES
40853ddbe726   emradbuba/repo-name:image-name-2024   "node app.js"   48 seconds ago   Up 47 seconds   0.0.0.0:8083->8080/tcp   web
```

... and even not running:
```
$ docker container ls -a
CONTAINER ID   IMAGE                                  COMMAND         CREATED              STATUS              PORTS                    NAMES
efc9120bc477   emradbuba/repo-name:image-name-2024b   "node app.js"   About a minute ago   Up About a minute   0.0.0.0:8086->8080/tcp   web3
667af8e3133a   emradbuba/repo-name:image-name-2024    "node app.js"   3 minutes ago        Up 3 minutes        0.0.0.0:8084->8080/tcp   web0
40853ddbe726   emradbuba/repo-name:image-name-2024    "node app.js"   7 minutes ago        Up 23 seconds       0.0.0.0:8083->8080/tcp   web
```

```
$ docker container [start/stop] web web2
web
```
</details>


### Exposing / publishing ports: 
<details>
<summary>Is there a different between <code>EXPOSE</code> and <code>-p 8080:8090</code> ?</summary>

> Yes - there is. <br>
> Difference between exposing (`EXPOSE`) and publishing (`-p 8080:8090`)
> is well [described here](https://www.mend.io/free-developer-tools/blog/docker-expose-port/)
> 
> * **Exposing** - is for inter-container communication purposes (like exposing 6379 port on redis container to be accessible for other containers in network)
> * **Publishing** - it produces a firewall rule that binds a container port to a port on the Docker host, ensuring the ports are accessible to any client that can communicate with the host.
</details>

