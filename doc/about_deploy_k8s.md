# 在服务器上部署k8s

> 部署k8s唯一的障碍就是防火墙。

我们采用的是[kubespray](https://github.com/kubernetes-sigs/kubespray)来进行自动化的部署，但是在镜像拉取过程中会遇到无法拉取的错误。

为了成功的在服务器上部署k8s，以下是目前找到的三种较为可靠的方式：
1. 手动在国内源中拉取镜像，再逐个打上标签，推送到各个节点上。
2. 使用192.168.2.24下提供的离线安装包。
3. 在服务器上使用VPS代理，使得kubespray能够在部署过程中能够拉取到需要的镜像。

## 方式一：手动拉取镜像
参考：
1. https://github.com/tx19980520/new-tech-stack/blob/master/k8s/kubeadm%20install.md

## 方式二：离线安装包
参考：
1. https://se-cloud.gitbook.io/openstack/
2. https://veiasai.github.io/2019/04/17/kubespray-%E8%B6%85%E5%BF%AB%E4%B9%90%E7%9A%84offline%E5%AE%89%E8%A3%85k8s/

## 方式三：VPS代理
参考：
1. https://github.com/Shadowsocks-Wiki/shadowsocks/blob/master/6-linux-setup-guide-cn.md
2. http://godsing.top/2018/04/13/Linux%E4%B8%8Bshadowsocks%E5%90%84%E7%A7%8D%E4%BB%A3%E7%90%86%E6%96%B9%E5%BC%8F%E6%80%BB%E7%BB%93/
3. http://unodba.github.io/2015/06/04/sys-linux-proxy/
4. https://kubernetes.feisky.xyz/bu-shu-pei-zhi/cluster/kubespray
