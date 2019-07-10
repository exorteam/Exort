# Helm & Tiller & Istio

References:

1. https://github.com/tx19980520/new-tech-stack/blob/master/k8s/kubeadm%20install.md
2. https://istio.io/zh/docs/setup/kubernetes/install/helm/
3. https://stackoverflow.com/questions/46672523/helm-list-cannot-list-configmaps-in-the-namespace-kube-system
4. https://yq.aliyun.com/articles/679787

Steps:

1. 从Github上下载最新的helm复制到`/usr/local/bin/`下。
2. 从Github上下载**Istio 1.1.6**解压到服务器上。(注意：一定要用1.1.6)
3. 在Istio目录下执行安装`helm install install/kubernetes/helm/istio --name istio --namespace istio-system`。
4. 在Istio目录下执行`export PATH=$PWD/bin:$PATH`。
5. 命令`kubectl get crds | grep 'istio.io\|certmanager.k8s.io' | wc -l`结果应为**53**。
