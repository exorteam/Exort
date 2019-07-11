#!/bin/bash

sudo locale-gen zh_CN.UTF-8

sudo apt update
sudo apt -y install python
sudo apt -y install docker.io

sudo docker load -i kube.tar
rm ./kube.tar

sudo mkdir /tmp/releases
sudo mv ./* /tmp/releases/
