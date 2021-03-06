// library for calculation of version numbers
import java.text.SimpleDateFormat

def get_branch(scm) {
    def BRANCH = scm.branches[0].name.replaceAll("/","-")
    return BRANCH
}

def get_cmk_version(scm, VERSION) {
    def BRANCH = get_branch(scm)
    def DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd")
    def DATE = new Date()

    if (BRANCH == 'master' && VERSION == 'daily') {
        return DATE_FORMAT.format(DATE)
    } else if (VERSION == 'daily') {
        return BRANCH + '-' + DATE_FORMAT.format(DATE)
    } else {
        return VERSION
    }
}

def get_branch_version() {
    return sh(returnStdout: true, script: "grep -m 1 BRANCH_VERSION defines.make | sed 's/^.*= //g'").trim()
}

def get_git_hash() {
    def HASH = sh(returnStdout: true, script: "git log -n 1 --pretty=format:'%h'").trim()
    return HASH
}

def get_date() {
    def DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd")
    def DATE = new Date()
    return DATE_FORMAT.format(DATE)
}

def get_docker_tag(scm) {
    def BRANCH = get_branch(scm)
    def DATE = get_date()
    def HASH = get_git_hash()
    return BRANCH + '-' + DATE + '-' + HASH
}

def select_docker_tag(BRANCH, BUILD_TAG, FOLDER_TAG) {
    // Empty folder prperties are null pointers
    // Other emput string variables have the value ''
    if (BUILD_TAG != '') {
        return BUILD_TAG
    }
    if (FOLDER_TAG != null) {
        return FOLDER_TAG
    }
    return BRANCH + '-latest'
}

def print_image_tag() {
    sh "cat /version.txt"
}

return this
