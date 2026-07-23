# Git HOL 3 — Branching & Merging

Follow `3. Git-HOL.docx` to:

- Create branch `GitNewBranch`, add files, commit, and merge into `master`.
- Use `git log --oneline --graph --decorate` to inspect history.
- (Optional) Configure `p4merge` as your difftool/mergetool for visualization.

Quick commands:
```
git checkout -b GitNewBranch
echo "branch file" > branch.txt
git add .
git commit -m "add branch file"
git checkout master
git merge GitNewBranch
git log --oneline --graph --decorate --all
```
