# a product release of javagae002

    product lifecycle name: javagae002
    version: as-is
    promise of usability: none
    blame: https://github.com/fishjtpcc1


## product lifecycle vision of javagae002

    id card:
        name: javagae002
        intent: to provide a 2-tier java-gae web app solution to AS CS Paper 1
        owner: https://github.com/fishjtpcc1
        about page: https://github.com/fishjtpcc1/javagae002 (README.md - no site, no wiki)
    people:
        producer: https://github.com/fishjtpcc1
        designer: https://github.com/fishjtpcc1
        user: open
    robots:
        tier2-runner builder:  
    producer's products:
        issues: https://github.com/fishjtpcc1/javagae002/issues
        releases: as-is informal via https://github.com/fishjtpcc1/javagae002 (master)
        core repo: https://github.com/fishjtpcc1/javagae002.git (intuitive structure, doc-in-code)
    runnable images:
        dev: http://direct-keel-94208.appspot.com/ (dev)
        acceptance: 
        as-released:
            owner-consumed: 
            licensee-consumed: open (unknown) via self-builds via self-served downloads of releases

## this repository contents
as found


## how to clone this lifecycle

to make a running (clone) system platform having:

    local-terminal (a linux machine eg cloud9 vm)
    librarian (a live remote git core repo eg a github repo)
    tier2 builder (a gcloud compute engine jenkins+java+maven vm job) "jenkins-vm-job001"
    tier2 runner (a gae project) "gae-project"
    
starting with:

    local-terminal (a linux machine eg cloud9 vm)
    template librarian [name=donor lifecycle name]
    gcloud account


human method:

    in gcloud:
        make a new project: get NEWGAEPID
    in local-terminal:
        invent a new lifecycle vision
            clone template to local-terminal
            modify the vision above inc unique name=NEWNAME and gae-pid=NEWGAEPID (global search and replace)
    in core repo host (eg github):
        make a new lifecycle container "clone" with name=NEWNAME
    in local-terminal:
        push to clone
    follow system-build.md


** Your lifecycle is now open for business!**
