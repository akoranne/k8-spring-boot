# Spring Boot Example - K8

This is a simple spring boot application. 

## Build and test

Maven build and run the app

```
	$ ./mvnw clean spring-boot:run 
	$ curl http://localhost:8080
	  {"greeting":"hello world"}
 	$ curl http://localhost:8080/message
 	  Message: Hello default [dburl: jdbc:oracle:thin:demo/demo@host:port:sid]
```

## Docker Compose

Package the archive and run as docker compose

```
	$ ./mvnw clean package
	$ docker-compose up -d
	$ docker images
	$ curl http://localhost:8080
	  {"greeting":"hello world"}
 	$ curl http://localhost:8080/message
 	  Message: Hello default [dburl: jdbc:oracle:thin:demo/demo@host:port:sid]
```

Build the runtime image and push to docker hub

```
	$ docker build -t k8-spring-boot .
	$ docker tag k8-spring-boot askxtreme/k8-spring-boot
 	$ docker push askxtreme/k8-spring-boot
```

## K8s 

Start minikube

```
	$ minikube version
	$ minikube start
	$ minikube status

	$ kubectl get pods,deployments,services,secrets -l app=k8-spring-boot
```

Set environmental properties. These properties can be from a file.

```
	$ kubectl create secret generic vcap-env --from-literal=vcap.env.user.name=scott --from-literal=vcap.env.user.password=tiger
		
	$ kubectl get secrets vcap-env -o yaml
		apiVersion: v1
		data:
		  vcap.env.user.name: c2NvdHQ=
		  vcap.env.user.password: dGlnZXI=
		kind: Secret
		metadata:
		  creationTimestamp: 2018-03-16T22:41:02Z
		  name: vcap-env
		  namespace: default
		  resourceVersion: "7364"
		  selfLink: /api/v1/namespaces/default/secrets/vcap-env
		  uid: 1aff2f25-296b-11e8-9c54-080027c538e4
		type: Opaque
```

Deploy application

````
	$ cd k8s
	
	$ kubectl apply -f deployment.yml
	$ kubectl get pods,deployments,services,secrets -l app=k8-spring-boot

	$ kubectl apply -f service.yml
	$ kubectl get pods,deployments,services,secrets -l app=k8-spring-boot
	
	$ minikube service k8-spring-boot-service --url
	  192.168.99.100:31369
	  
	$ curl $(minikube service k8-spring-boot-service --url)
	  {"greeting":"hello world"}
	  
	$ curl $(minikube service k8-spring-boot-service --url)/message
	  Message: Hello default [dburl: jdbc:oracle:thin:scott/tiger@host:port:sid]
	  
	$ minikube service k8-spring-boot-service 
	  http://192.168.99.100:31369/
```

Clean up

```
	$ kubectl get pods,deployments,services,secrets -l app=k8-spring-boot
	NAME                                          READY     STATUS    RESTARTS   AGE
	po/k8-spring-boot-deployment-cf96685c-p2wpt   1/1       Running   0          21m
	
	NAME                               DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE
	deploy/k8-spring-boot-deployment   1         1         1            1           28m
	
	NAME                         TYPE       CLUSTER-IP      EXTERNAL-IP   PORT(S)          AGE
	svc/k8-spring-boot-service   NodePort   10.96.131.239   <none>        8080:31618/TCP   17m
	
	$ kubectl delete -f service.yml
	$ kubectl delete -f deployment.yml
	$ kubectl delete secret vcap-env
	$ kubectl get pods,deployments,services,secrets

	$ minikube stop
```


## References

* https://github.com/sbrosinski/spring-boot-on-k8s 
* https://dzone.com/articles/configuring-spring-boot-on-kubernetes-with-secrets
