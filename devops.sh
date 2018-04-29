#!/bin/bash

function main()
{
    if [ "$1" == "test" ]; then
        unitTest
    elif [ "$1" == "package" ]; then
        package
    elif [ "$1" == "deploy-staging" ]; then
        deploy_staging
    elif [ "$1" == "e2e" ]; then
        e2eTest
    elif [ "$1" == "deploy-prod" ]; then
        deploy_staging
    fi
}

function unitTest()
{
    mvn antrun:run@ktlint
    mvn test
}

function package()
{
    mvn verify
}

function deploy_staging()
{
    serverless deploy
}

function deploy_prod()
{
    serverless deploy --stage prod
}

function e2eTest()
{
    newman run e2e/schoolticketsalesserver.postman_collection.json --global-var "url=https://xkmi9ww5m1.execute-api.us-east-1.amazonaws.com/staging"
}

main $1