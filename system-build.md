# system-build.md

Instructions to build this lifecycle's system platform.

---

to make a running (clone) system platform having:

    local-terminal (a linux machine eg cloud9 vm)
    librarian (a live remote git core repo eg a github repo) "clone"
    tier2 builder (a gcloud compute engine jenkins+java+maven vm job) "jenkins-vm-job001"
    tier2 runner (a gae project) "gae-project"

    
starting with:

    local-terminal (a linux machine eg cloud9 vm)
    template librarian [name=donor lifecycle name]
    gcloud account

    
human method:

    offline:
        invent a new lifecycle vision NEWNAME, SECRETJPW
    in core repo host (eg github):
        make a new lifecycle container "clone" with NEWNAME: get COREREPOURL
    in gcloud:
        make a new gae-project with NEWNAME: get NEWGAEPID
    in gae-project.apis:
        authorize use of google compute engine api
    in local-terminal:
        make base jenkins-vm with the jenkins-vm completion script
    in gae-project:
        set allow http
            do a first-time follow of resulting ce instances.jenkins-vm.extIP link
    in jenkins-vm:
        login as user:user pw:SECRETJPW
        for each factory, create new job with the jenkins-vm-BRANCH-build completion script // note jenkins will fail until clone is merged
        get JENKINSURL to complete vision
    in local-terminal:
        clone template to local-terminal
    in master:
        complete mod to the vision with NEWNAME, SECRETJPW, COREREPOURL, NEWGAEPID, JENKINSURL (global search and replace)
        push to COREREPOURL
    in local-terminal:
        delete template to prepare for clean local dev next
    via librarian:
        update library card from vision eg description, blame
        replace manual copies of vision values eg blame links in index.html (as far as possible, hard-coded rpt values are best avoided)
        commence dev with a test that the vision performs as defined


jenkins-vm completion script:

    # to download and install gcloud sdk
    curl https://sdk.cloud.google.com | bash
    # reboot to complete
    # to auth
    gcloud auth login
    # follow link to get vcode and enter toi complete
    # to get bitnami-image name
    gcloud compute images list --project bitnami-launchpad | grep jenkins
    # create a running admin account on jenkins vm (note you will be jenkins admin user un:user pw:as-set-here) 
    PASSWORD=SECRETJPW                # 12 or more chars, with letters and numbers - 8 works
    PROJECT_ID=NEWGAEPID
    BITNAMI_IMAGE=<bitnami-image>      # e.g. bitnami-jenkins-1-606-0-linux-debian-7-x86-64
    gcloud compute \
        instances create bitnami-jenkins \
        --project ${PROJECT_ID} \
        --image-project bitnami-launchpad \
        --image ${BITNAMI_IMAGE} \
        --zone us-central1-a \
        --machine-type n1-standard-1 \
        --metadata "bitnami-base-password=${PASSWORD}" \
                 "bitnami-default-user=user" \
                 "bitnami-key=jenkins" \
                 "bitnami-name=Jenkins" \
                 "bitnami-url=//bitnami.com/stack/jenkins" \
                 "bitnami-description=Jenkins." \
                 "startup-script-url=https://dl.google.com/dl/jenkins/p2dsetup/setup-script.sh" \
        --scopes "https://www.googleapis.com/auth/userinfo.email" \
               "https://www.googleapis.com/auth/devstorage.full_control" \
               "https://www.googleapis.com/auth/projecthosting" \
               "https://www.googleapis.com/auth/appengine.admin" \
        --tags "bitnami-launchpad"


jenkins-vm-BRANCH-build completion script:

    name: BRANCH-build
    desc: continuously build and deploy from BRANCH to gae version=BRANCH
    from template: freestyle project
    jenkins toolset (run restriction) = cloud-dev-java
    source manager: git
    source location: COREREPOURL
    branch: */BRANCH
    source credentials: blank
    build trigger: poll H/5 * * * *
    build, test, and deploy execution steps (exe shell): mvn gcloud:deploy -Dgcloud.version=BRANCH // ie all in one go for java+maven
  	

References:

* https://cloud.google.com/tools/repo/push-to-deploy

