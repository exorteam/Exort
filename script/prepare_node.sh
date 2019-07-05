sudo locale-gen zh_CN.UTF-8

sudo apt update
sudo apt -y install python
sudo apt -y install docker.io

sudo docker load -i kube.tar
