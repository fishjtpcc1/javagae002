# JTPGAE001
connected to codenvy (hopefully)

this is a proto project inspired by the difficulty in writing educational java console apps with full read/write capabilities in the cloud.

##### getting codenvy and github accounts to play nicely:
know: both github and codenvy manage ssh keys at account [ie the lead human coder] level
* using ssh - setup codenvy remote with github ssh address // setup github ssh keys from codenvy generated key window.prefs.sshkeystore.generate

##### editing at both ends of the cloud:
* edit in github: commit in file web editor: codenvy.remote.pull: [workspace has clone of github]
* edit in codenvy: git.commit: [github is not updated] git.remotes.push: [github=codenvy]
* [using same file eg this] edit in github // edit in codenvy: commit both ends: codenvy.push // [github is idle]: [denied: fetch first] pull: [denied: wld be overridden]: fetch: [ok] merge with remote branch: [fail: see file name for conflict markup]: commit: push: [denied: master->master failed]: fix conflict: commit: push: [yayy!!!]   

##### cloneing a codenvy workspace stored as a github repo:
* create blank project: git.init repo: remotes.add with ssh url from github sidebar: [merge error but repos appears aok]
* do something new eg this: commit: push: [success]
* TODO: test if it runs out of the box now

##### push-to-deploy from github directly to gae:
does committing this in github make magic happen??: nope!
edit done in codenvy and committed and pushed...
edit done in c9 after deleting local repos and git init https://github.com/fishjtpcc1/JTPGAE001.git
now attempting push to github to trigger gcloud to refresh and trigger rebuild [due to push event broadcasting perhaps?]... fail
deleted repo and recreated with git clone [to set config correctly for origin alias] https://github.com/fishjtpcc1/JTPGAE001.git: edit this and push with git push [to origin]...
