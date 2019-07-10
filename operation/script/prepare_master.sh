#!/bin/bash

offline_pkg_ip="10.0.0.26"
offline_pkg_version="kube-1.14.1"
user="ubuntu"
node_ips=("10.0.0.11" "10.0.0.99" "10.0.0.69")

sudo locale-gen zh_CN.UTF-8

sudo apt update
sudo apt -y install python
sudo apt -y install python-pip
sudo apt -y install docker.io

wget $offline_pkg_ip/$offline_pkg_version/kube-1.14.1.tar
wget $offline_pkg_ip/$offline_pkg_version/kubespray-2.10.0.tar.gz
wget $offline_pkg_ip/$offline_pkg_version/releases/calicoctl
wget $offline_pkg_ip/$offline_pkg_version/releases/hyperkube
wget $offline_pkg_ip/$offline_pkg_version/releases/kubeadm
wget $offline_pkg_ip/$offline_pkg_version/releases/cni-plugins-amd64-v0.6.0.tgz

sudo mkdir /tmp/releases
sudo cp ./calicoctl /tmp/releases/
sudo cp ./hyperkube /tmp/releases/
sudo cp ./kubeadm /tmp/releases/
sudo cp ./cni-plugins-amd64-v0.6.0.tgz /tmp/releases/
sudo chmod u+x ./kubeadm
sudo cp ./kubeadm /usr/local/bin
sudo docker load -i kube-1.14.1.tar

chmod u+x ./append_host.sh
sudo ./append_host.sh

for ip in ${node_ips[*]}
do
	echo ">>> Now deploying [$ip]"
	ssh $user@$ip -C "/bin/bash" < ./append_host.sh
	scp ./kube-1.14.1.tar $user@$ip:/home/ubuntu/kube.tar
	scp ./kubeadm $user@$ip:/home/ubuntu/kube.tar
	scp ./calicoctl $user@$ip:/home/ubuntu/kube.tar
	scp ./hyperkube $user@$ip:/home/ubuntu/kube.tar
	scp ./cni-plugins-amd64-v0.6.0.tgz $user@$ip:/home/ubuntu/kube.tar
	ssh $user@$ip -C "/bin/bash" < ./prepare_node.sh
done

tar -xf ./kubespray-2.10.0.tar.gz
sudo pip install --upgrade pip
sudo pip install -r ./kubespray-2.10.0/requirements.txt

