# Git HOL 2 — .gitignore

Follow the lab steps from `2. Git-HOL.docx` to:

- Create `.log` files and `log/` folder in a repo and update `.gitignore` to ignore them.
- Verify with `git status` that they are not tracked.

Suggested commands:
```
echo "test" > ignoreme.log
mkdir log
echo "temp" > log/run.log
# update .gitignore with lines:
# *.log
# /log/
git status
```
