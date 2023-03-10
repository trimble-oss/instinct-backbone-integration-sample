#!/bin/sh

set -o errexit

cd sample-app

./gradlew clean lint test build --stacktrace \
  -Ptt_artifactory_username="$ARTIFACTORY_USER" \
  -Ptt_artifactory_password="$ARTIFACTORY_PASSWORD" \
  -Partifactory_user="$ARTIFACTORY_USER" \
  -Partifactory_password="$ARTIFACTORY_PASSWORD" \
  -Partifactory_contextUrl='https://trimbletransportation.jfrog.io/trimbletransportation' \
  -Prepo='ttm-mep-sample-app' \
  -PpublishRepositoryKey='ttm-mvn-mobile-ecosystem'
