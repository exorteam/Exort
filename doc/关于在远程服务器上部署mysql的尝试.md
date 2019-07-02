# Deploy mysql on remote server

1. deploy as docker image
2. deploy by apt installation

Except for specific preparation for current way (eg. 'apt install mysql.server' or 'docker pull mysql'),
there are the same steps for both ways after installation:

1. Create an user like `'test'@'%"` and grant permission for the user.
2. Insert contents to `my.cnf` (or `my.ini` for Windows)

```
	[mysqld]
 	bind-address=0.0.0.0
```

3. Restart service, or mount correct config file for docker deploy instead.

> Due to the default request listening mechanism, Mysql may ignore requests from remote client. This will raise exceptions of client applications. To avoid such situation, it is necessary to modify environment argment **bind-address** in configuration file.

