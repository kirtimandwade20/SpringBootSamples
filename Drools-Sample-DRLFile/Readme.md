# Drools Sample with dynamic loading of multiple DRL files

This is a sample application to create a rule engine using drools. 
## Use case
Having 2 or more DRL files and having the ability to fire rules of any DRL file on the basis of
API Call. In the sample i have used 2 user's (static user's for now) and we can fire rules for different user
on the basis of the received request
We can also dynamically update the rules using reload API.



## Running the application 
You can directly start the application by executing the DroolsDemoApplication.class or


``` 
Java -jar drools-demo-0.0.1-SNAPSHOT.jar
```

## Testing

Executing rules for "Tenant 1" 
```
Method POST
http://localhost:8080/fireRules?tenant=tenant1
Request Body:
{
 "bankName": "Citi",
 "durationInYear": 1
}
```
Result

```
{
    "fields": {
        "bankName": "Citi",
        "durationInYear": 1
    },
    "results": {
        "rate": "2%"
    }
}
```


Executing rules for "Tenant 2" 
```
Method POST
http://localhost:8080/fireRules?tenant=tenant2
Request Body:
{
 "age": 3
}
```


Result

```
{
    "fields": {
        "age": 3
    },
    "results": {
        "type": "child"
    }
}
```

Reload Configuration:
You can update the configuration by making change in DRL file and then executing following API
```
Method GET
http://localhost:8080/reload?tenant=tenant2
```
Result

```
config for tenant2 updated
```
