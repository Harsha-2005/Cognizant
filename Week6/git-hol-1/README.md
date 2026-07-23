# Git HOL 1 — Git Basics

Follow the lab steps from `1. Git-HOL.docx` to:

- Configure Git (`git config --global user.name` / `user.email`).
- Set Notepad++ as the default editor for Git.
- Initialize a repository, add a file `welcome.txt`, commit, and push to remote.

Commands hints:
```
git init GitDemo
cd GitDemo
echo "Hello" > welcome.txt
git add welcome.txt
git commit -m "Add welcome"
git remote add origin <your-remote-url>
git push -u origin master
```
