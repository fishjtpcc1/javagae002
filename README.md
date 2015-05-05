# JTPGAE001
connected to codenvy (hopefully)

this is a proto project inspired by the difficulty in writing educational java console apps with full read/write capabilities in the cloud.

using ssh. setup codenvy remote with github ssh address. setup github ssh keys from codenvy generated key

edit in github: commit in file web editor: codenvy.remote.pull: [workspace has clone of github]
edit in codenvy: git.commit: [github is not updated] git.remotes.push: [github=codenvy]
[using same file eg this] edit in github // edit in codenvy: commit both ends: codenvy.push // [github is idle]: [denied: fetch first] pull: [denied: wld be overridden]: fetch: [ok] merge with remote branch: [file is unchanged from codenvy]: commit:  