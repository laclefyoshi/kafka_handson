#!/bin/sh
ARGS=$@
docker run --rm -u gradle -v "$PWD":/home/gradle/project -w /home/gradle/project gradle gradle $ARGS
