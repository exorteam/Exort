# Helm & Tiller & Istio

References:

1. https://github.com/tx19980520/new-tech-stack/blob/master/k8s/kubeadm%20install.md
2. https://istio.io/zh/docs/setup/kubernetes/install/helm/
3. https://stackoverflow.com/questions/46672523/helm-list-cannot-list-configmaps-in-the-namespace-kube-system
4. https://yq.aliyun.com/articles/679787

Steps:

1. 从Github上下载最新的helm复制到`/usr/local/bin/`下。
2. 从Github上下载**Istio 1.1.6**解压到服务器上。(注意：一定要用1.1.6)
3. 在Istio目录下执行:
	```
   kubectl create namespace istio-system
   kubectl apply -f ./install/kubernetes/helm/istio-init/files/crd-11.yaml
   kubectl apply -f ./install/kubernetes/helm/istio-init/files/crd-certmanager-10.yaml
   kubectl apply -f ./install/kubernetes/helm/istio-init/files/crd-10.yaml
   ```
   然后执行：
   `kubectl get crds | grep 'istio.io\|certmanager.k8s.io' | wc -l`
   结果应为**56**。
4. 接下来再通过helm生成yaml，然后向k8s部署该yaml文件：
   ```
   helm template install/kubernetes/helm/istio --name istio --namespace istio-system --set sidecarInjectorWebhook.enabled=true --set ingress.service.type=NodePort --set gateways.istio-ingressgateway.type=NodePort --set gateways.istio-egressgateway.type=NodePort --set tracing.enabled=true --set servicegraph.enabled=true --set prometheus.enabled=true --set tracing.jaeger.enabled=true --set grafana.enabled=true > istio.yaml
   kubectl apply -f istio.yaml
   ```
5. 在Istio目录下执行`export PATH=$PWD/bin:$PATH`。
