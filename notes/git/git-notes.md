# Git - Basic notes

## Rebase vs Merging

<details>
<summary>Rebase vs Merge - main difference</summary>

> *  Merge - creates extra commit with "changes after merge"
> * Rebase - goes to first common commit, **copies** commits from other branch and adds itsown to the top
</details>

<details>
<summary>What about solving conflicts?</summary>

> Merge - solved only once when creating merge commit
> Rebase - has to be solved to each "recreated / copied" commit, so possibly many conflicts have to be solved
</details>

<details>
<summary>What is an explicit merge</summary>

> `git merge --no-ff` will perform the merge but with not fast-forward if applicable. It means it will always create a new merge commit also in cases where fast forward would be possible. 
> <details>
> <summary>When to use it? </summary>
> 
> > For example in a case where we want to keep clear merging history - every merge action should be clearly seen. 
> </details>
> </details>

<details>
<summary>Example of case where Rebase is better than Merge</summary>

> If we want to prefer a straightforward , linear commit history and we are not interested in keeping the history as it was. 
> Rebase could be better with I work with my own not published branch, so I can squash commits before merging back to, for example, dev / main
</details>

<details>
<summary>Should I rebase in my feature branch before merging it to main?</summary>

> Sure - rebase will rewrite the history, so recreate commit with new hash and timestamp, so merging a recently 
> rebased branch with result just in a fast-forward merge.
</details>

<br>

<details>
<summary>Interactive mode options: </summary>

> * **pick** = use commit
> * **reword** = use commit, but edit the commit message
> * edit = use commit, but stop for amending
> * **squash** = use commit, but meld into previous commit
> * fixup = like "squash", but discard this commit's log message
> * exec = run command (the rest of the line) using shell
> * **drop** = remove commit
> > <details>
> > <summary>Example of squash three last commits:</summary>
> >
> > `git rebase -i HEAD~3`
> > 
> > ```
> > pick    [FEATURE-1443] third, the recent one (will be picked) 
> > squash  [FEATURE-1443] second commit (will be squashed to the newer one)  
> > squash  [FEATURE-1443] first commit (will be squashed to the newer one)
> > ``` 
> </details>
</details>

<details>
<summary>How Git knows which commits from main are already merged into by feature branch?</summary>

> The key is the merge commit which holds the information about last commit from `main` which was considered during this merge.
> It means, that later on when we merge our feature branch to main, or main to branch, Git knows how far should it go in main to 
> identify the first spot of possible differences. 
</details>
