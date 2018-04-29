#!/bin/bash

function main()
{
    if [ "$1" == "test" ]; then
        unitTest
    elif [ "$1" == "package" ]; then
        package
    elif [ "$1" == "deploy" ]; then
        deploy
    elif [ "$1" == "e2e" ]; then
        e2eTest
    fi
}

function unitTest()
{
    mvn test
}

function package()
{
    mvn package
}

function deploy()
{
    serverless deploy
}

function e2eTest()
{
    newman run e2e/hello.json
}
main $1