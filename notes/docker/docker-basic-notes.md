## Docker Basic notes

### Docker Build

Dockerfile example (from https://docs.docker.com/build/concepts/dockerfile/): 

```dockerfile
# syntax=docker/dockerfile:1
FROM ubuntu:22.04

# install app dependencies
RUN apt-get update && apt-get install -y python3 python3-pip
RUN pip install flask==3.0.*

# install app
COPY hello.py /

# final configuration
ENV FLASK_APP=hello
EXPOSE 8000
CMD ["flask", "run", "--host", "0.0.0.0", "--port", "8000"]
```

<details>
<summary>Docker Build layers?</summary>

> Each line in Dockerfile adds sth more to previous layers...
> In case of changes, the layer and all consecutive layers a invalidated
> 
</details>

<details>
<summary><code>CMD</code> in Dockerfile</summary>

> * Only last defined is respected
> * Runs program once container starts
</details>

<details>
<summary>What does to <code>COPY</code> command</summary>

Copy file to a filesystem of a container
</details>

<details>
<summary>What does to <code>EXPOSE</code> command</summary>

Tell that container has a service listening on specific port (internally, inside containers network)
</details>

<details>
<summary>What is build context?</summary>

> set of files that you can access in Dockerfile instructions like `COPY`, `ADD` etc...
</details>

<details>
<summary>Two types of build context</summary>

> **Local** and **Remote**
</details>


