Reference: 

1. http://pauldone.blogspot.com/2017/06/deploying-mongodb-on-kubernetes-gke25.html
2. https://blog.csdn.net/fate_xuhaidong/article/details/83026433

To generate ssl key: `openssl rand -base64 745 > internal-auth-mongodb-keyfile`

To deploy:

1. `kubectl apply -f storageClass-manual.yaml`
2. `kubectl apply -f mongo-secret.yaml`
3. `kubectl apply -f mongo-deploy.yaml`
