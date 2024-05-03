## Git

### git fetch --prune
(Nice explanation: https://stackoverflow.com/a/47939403/1534456)

Removing branches which no longer exist on remote repo.
<details>
<summary>In such case how many branches are we considering indeed? </summary>

> Three.
> <details>
> <summary>What area these three branches?</summary>
> 
> * **Remote branch** `feature/TASK-1234` => the branch `feature/TASK-1234`
> * **Local branch** `remotes/origin/feature/TASK-1234` => _"This is what the remote told me its feature/X branch was, last time we talked"_
> * **Local branch** `feature/TASK-1234` => local branch poiting to last commit and TRACKS the `remotes/origin/feature/TASK-1234` 
> </details>

</details>

`git branch -a` will list all branches - local and remote ones the git is now aware of: 
```
* dev
  main
  migrate-theory
  remotes/origin/HEAD -> origin/main
  remotes/origin/dev
  remotes/origin/main
  remotes/origin/migrate-theory
```

`git fetch --prune` will update the state and remove "remotes/" branches with are no longer on remote repo: 
```
$ git fetch --prune
From github.com:emradbuba/learning-project
 - [deleted]         (none)     -> origin/create-new-bracch
```
Then, the local branches can be remoted as well (`git branch -D create-new-bracch`)