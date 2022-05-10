#!/bin/bash

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
TESTS=(
  test_repository_sync
  test_repository_sync_ignore_list
  test_repository_sync_does_not_fail_on_no_changes
)

test_repository_sync() {
  create_local_repository "source-repo" "master"
  create_local_bare_repository "target-repo" "main"
  create_file_in_path "source-repo/source-file.md" "# Hello World"
  commit "source-repo" "mock"
  export CURRENT_BRANCH="master"
  export SOURCE_BRANCH="master"
  export TARGET_BRANCH="main"
  TARGET_REPOSITORY="$(cd "target-repo" && pwd)"
  export TARGET_REPOSITORY

  cd "source-repo" || exit 1
  "$SCRIPT_DIR/gitsync.sh"
  cd .. || exit 1

  git clone "$TARGET_REPOSITORY" "test-repo"
  unset CURRENT_BRANCH
  unset SOURCE_BRANCH
  unset TARGET_BRANCH
  unset TARGET_REPOSITORY

  assert_files_equal "source-repo/source-file.md" "test-repo/source-file.md"
  return $?
}

test_repository_sync_does_not_fail_on_no_changes() {
  create_local_repository "source-repo" "master"
  create_local_bare_repository "target-repo" "main"
  create_file_in_path "source-repo/source-file.md" "# Hello World"
  commit "source-repo" "mock"

  export CURRENT_BRANCH="master"
  export SOURCE_BRANCH="master"
  export TARGET_BRANCH="main"
  TARGET_REPOSITORY="$(cd "target-repo" && pwd)"
  export TARGET_REPOSITORY
  git clone "$TARGET_REPOSITORY" "test-repo"
  cp "source-repo/source-file.md" "test-repo/source-file.md"
  commit "test-repo" "mock"
  cd "test-repo" || exit 1
  git push origin HEAD
  cd .. || exit 1

  cd "source-repo" || exit 1
  "$SCRIPT_DIR/gitsync.sh"
  exit_code="$?"
  cd .. || exit 1

  unset CURRENT_BRANCH
  unset SOURCE_BRANCH
  unset TARGET_BRANCH
  unset TARGET_REPOSITORY
  return $exit_code
}

test_repository_sync_ignore_list() {
  create_local_repository "source-repo" "master"
  create_local_bare_repository "target-repo" "main"
  create_file_in_path "source-repo/source-file.md" "# Hello World"
  create_file_in_path "source-repo/ignored.md" "# Don't Include Me"
  create_file_in_path "source-repo/.hidden/ignored.md" "# Don't Include Me"
  create_file_in_path "source-repo/.syncignore" "$(
      printf "%s\n%s\n" ".hidden/" "ignored.md"
  )"

  commit "source-repo" "mock"
  export CURRENT_BRANCH="master"
  export SOURCE_BRANCH="master"
  export TARGET_BRANCH="main"
  TARGET_REPOSITORY="$(cd "target-repo" && pwd)"
  export TARGET_REPOSITORY

  cd "source-repo" || exit 1
  "$SCRIPT_DIR/gitsync.sh"
  cd .. || exit 1

  git clone "$TARGET_REPOSITORY" "test-repo"
  unset CURRENT_BRANCH
  unset SOURCE_BRANCH
  unset TARGET_BRANCH
  unset TARGET_REPOSITORY

  assert_files_missing "test-repo/.hidden/ignored.md" "test-repo/ignored.md"
  return $?
}

create_local_repository() {
  local repository_name="$1"
  local initial_branch="${2:-main}"
  mkdir "$repository_name"
  cd "$repository_name" || return 1
  git init "${initial_branch:+--initial-branch=$initial_branch}"
  cd .. || return 1
}

assert_files_equal() {
  local expected="$1"
  local actual="$2"
  local expected_sum
  local actual_sum
  expected_sum="$(sha256sum "$expected" | awk '{print $1}')"
  actual_sum="$(sha256sum "$actual" | awk '{print $1}')"

  if ! [ "$expected_sum" =  "$actual_sum" ]; then
    {
      printf "Expected: %s (from: %s)\n" "$expected_sum" "$expected"
      printf "  Actual: %s (from: %s)\n" "$actual_sum" "$actual"
    } >&2
    return 1
  fi
}

assert_files_missing() {
  local files=( "$@" )
  local result=0
  for f in "${files[@]}"; do
    if [ -f "$f" ]; then
      printf "File should not exist: %s\n" "$f" >&2
      result=1
    fi
  done
  return $result
}

create_local_bare_repository() {
  local repository_name="$1"
  local initial_branch="${2:-main}"
  mkdir "$repository_name"
  cd "$repository_name" || return 1
  git init --bare "${initial_branch:+--initial-branch=$initial_branch}"
  cd .. || return 1
}

create_file_in_path() {
  local path="$1"
  local content="$2"
  local dir
  dir="$(dirname "$path")"
  [ -d "$dir" ] || mkdir "$dir"
  touch "$path"
  printf "%s\n" "$content" > "$path"
}

commit() {
  local repository_name="$1"
  cd "$repository_name" || return 1
  git add .
  git commit -m "$2"
  cd .. || return 1
}

trap clean EXIT
trap clean SIGTERM

clean() {
  rm -rf "$TEMP_DIR"
  rm -f "$TEMP_LOG"
}

TEMP_DIR="$(mktemp -d "/tmp/${0##*/}-XXXXX")"
TEMP_LOG="$(mktemp "/tmp/${0##*/}-XXXXX.log")"

for test in "${TESTS[@]}"; do
  [ -d "$TEMP_DIR" ] || mkdir "$TEMP_DIR"
  cd "$TEMP_DIR" || exit 1
  if $test >"$TEMP_LOG" 2>&1; then
    echo -e "\e[32mTest $test OK\e[0m" >&2
  else
    cat "$TEMP_LOG"
    echo -e "\e[31mTest $test failed\e[0m" >&2
  fi
  clean
  cd /tmp || exit 1
done
