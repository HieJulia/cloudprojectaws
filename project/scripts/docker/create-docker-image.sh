#!/usr/bin/env bash

tags=( $@ )
len=${#tags[@]}


# Print help
printHelp(){
    echo "Create a docker image"

}


# Create docker ignore file
createDockerIgnoreFile(){
    echo ".git" > .dockerignore
    echo "bin" >> .dockerignore
    echo "src/main" >> .dockerignore

}


processOtherTags(){
    for otherTag in "$@" ; do
        echo "Tagging image $image:$firstTag with $otherTag..."
        docker tag $image:$firstTag $image:$otherTag
    done
}




# Get the image name
image=$(<DockerImageName)
if [ -z "$image" ]
then
  echo
  echo "Cannot find an image name in the required file 'DockerImageName'."
  printHelp
  exit 1
fi

if [ $len == 0 ]
then
  echo
  echo "No tags provided, assuming at single tag of 'latest'."
  firstTag=latest
else
  firstTag=${tags[0]}
fi


# Then create docker image

createDockerIgnoreFile

echo "Creating docker image "

docker build -f Dockerfile -t $image:$firstTag .

if [ $len -gt 1 ]
then
  otherTags=("${tags[@]:1:$len}")
  processOtherTags "${otherTags[@]}"
fi

docker images $image