#!/bin/bash
# [gitsync.sh]
# Clone a target repository
# Checkout target branch and delete its files
# Copy the current repository in working directory to the target repository
# Create or override .gitignore in target repository to remove paths from commit
# Commit and publish to target repository

TARGET_BRANCH="${TARGET_BRANCH:-main}"
TARGET_REPOSITORY="${TARGET_REPOSITORY:-$(exit 1)}"
IGNORE_FILE="${IGNORE_FILE:-.syncignore}"
TEMP_DIR="$(mktemp -d "/tmp/${0##*/}-XXXXXX")"

main() {
	activate_debug

	git clone "$TARGET_REPOSITORY" "$TEMP_DIR"

	git_checkout_and_delete_files "$TEMP_DIR" "$TARGET_BRANCH"

	# Copy over this repository content
	rsync -a --exclude '.git' . "$TEMP_DIR"

	# Move ignore file content to .gitignore of target repository
	if [ -f "$IGNORE_FILE" ]; then
		cp "$IGNORE_FILE" "$TEMP_DIR/.gitignore"
	else
		log warning "Ignore list doesn't exist, all files in this repository will be added in $TARGET_REPOSITORY"
	fi

	# Commit and push target repository
	pushd "$TEMP_DIR"
	
	if [ -z "$(git status --porcelain=v1)" ]; then
		log info "No changes to push to target repository"
		exit 0
	fi

	git config user.email "aws.preprod@peoplenetonline.com"
  	git config user.name "Hawkmoon Instinct"
	git rm -r --cached .bpac/ .syncignore
	git add .
	git commit -m "Sync $(date +%F-%T)"
	git push origin "$TARGET_BRANCH"
	popd
}

git_checkout_and_delete_files() {
	local repo_path="$1"
	local branch="$2"
	pushd "$repo_path"
	git checkout -B "$branch"
	find . ! -path './.git/*' -a ! -path './.git' -delete
	popd
}

activate_debug() {
	if [ -n "${DEBUG+x}" ]; then
		set -x
	fi
}

check_git_repository_root() {
	local path="${1:-.}"
	if ! [ -d "$path/.git" ]; then
		log error "Not a git directory: $(cd "$path" && pwd)"
		exit 1
	fi
}

log() {
  local type="$1"
  local message="$2"
  local now
  now="$(date +%F-%T)"

  if [ "$type" = "error" ]; then
    printf "%s [%s] %s\n" "$now" "$type" "$message" >&2
  else
    printf "%s [%s] %s\n" "$now" "$type" "$message"
  fi
}

remote_has_branch() {
  local remote="$1"
  local branch="$2"
  git ls-remote --exit-code --heads "$remote" "$branch" >/dev/null
  return $?
}

clean() {
	rm -rf "$TEMP_DIR"
}

trap clean EXIT
trap clean SIGTERM
set -o errexit
main
