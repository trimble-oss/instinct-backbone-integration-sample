#!/bin/sh

set -o errexit

cd sample-app

./gradlew lint test --stacktrace \
  -Ptt_artifactory_username="$ARTIFACTORY_USER" \
  -Ptt_artifactory_password="$ARTIFACTORY_PASSWORD" \
  -Partifactory_user="$ARTIFACTORY_USER" \
  -Partifactory_password="$ARTIFACTORY_PASSWORD"
