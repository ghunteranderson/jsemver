#!/bin/bash
set -ev

cp .travis.settings.xml ${HOME}/.m2/settings.xml

if [ "TRAVIS_BRANCH" = "master" ]; then
  mvn versions:set -DremoveSnapshot=true
fi

mvn install