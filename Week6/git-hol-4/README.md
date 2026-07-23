# Git HOL 4 — Conflict Resolution

Follow `4. Git-HOL.docx` to:

- Create conflicting changes to `hello.xml` on a branch and on `master`.
- Use `git merge` and resolve conflicts with a 3-way merge tool (P4Merge suggested).
- Add backup files to `.gitignore` and commit the change.

Quick commands:
```
git checkout -b GitWork
echo "from branch" > hello.xml
git add hello.xml; git commit -m "branch edit"
git checkout master
echo "from master" > hello.xml
git add hello.xml; git commit -m "master edit"
git merge GitWork
# resolve conflicts, then
git add hello.xml
git commit -m "resolve conflict"
```
