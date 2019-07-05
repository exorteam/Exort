sudo locale-gen zh_CN.UTF-8

sudo apt update
sudo apt -y install python
sudo apt -y install python-pip
sudo apt -y install docker.io

wget 10.0.0.26/kube-1.14.1/kube-1.14.1.tar
wget 10.0.0.26/kube-1.14.1/kubespray-2.10.0.tar.gz
wget 10.0.0.26/kube-1.14.1/releases/calicoctl
wget 10.0.0.26/kube-1.14.1/releases/hyperkube
wget 10.0.0.26/kube-1.14.1/releases/kubeadm
wget 10.0.0.26/kube-1.14.1/releases/cni-plugins-amd64-v0.6.0.tgz

sudo cp ./calicoctl /tmp/releases/
sudo cp ./hyperkube /tmp/releases/
sudo cp ./cni-plugins-amd64-v0.6.0.tgz /tmp/releases/
sudo cp ./kubeadm /usr/local/bin
sudo chmod u+x /usr/local/bin/kubeadm

sudo sh ./append_host.sh

ssh ubuntu@dbnode -C "/bin/bash" < ./append_host.sh
ssh ubuntu@servicenode1 -C "/bin/bash" < ./append_host.sh
ssh ubuntu@servicenode2 -C "/bin/bash" < ./append_host.sh

scp ./kube-1.14.1.tar ubuntu@dbnode:/home/ubuntu/kube.tar
scp ./kube-1.14.1.tar ubuntu@servicenode1:/home/ubuntu/kube.tar
scp ./kube-1.14.1.tar ubuntu@servicenode2:/home/ubuntu/kube.tar

ssh ubuntu@dbnode -C "/bin/bash" < ./prepare_node.sh
ssh ubuntu@servicenode1 -C "/bin/bash" < ./prepare_node.sh
ssh ubuntu@servicenode2 -C "/bin/bash" < ./prepare_node.sh

tar -xf ./kubespray-2.10.0.tar.gz
cd ./kubespray-2.10.0.tar.gz
sudo pip install -r requirements.txt

