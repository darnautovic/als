##Application Licence Service (ALS)
Application Licence service is a simple web service / application demo created to support my MSC's thesis ("Web Service Design Patterns").

### Application overview

####Tools and lagnugage

ALS is built using  [Play framework](http://www.playframework.com/"Play framework") (2.1.3) and [Scala language ](http://www.scala-lang.org/ "Scala language").

Als demo is also deployable on Heroku (demo available  [here](http://application-licence-manager.herokuapp.com "Scala language")).

Front - end part of application is created using [Play templating engine](http://www.playframework.com/documentation/2.0/ScalaTemplates "!") using [Bootstrap responsive admin template](http://www.egrappler.com/bootstrap-responsive-admin-template/).


### Use case overview
Application enable users (usually developers that want to track their applications registration) to manage their Apps, create serial numbers for each app and provide licence files.

#### User part
1. Users create application for which they want to track registrations
2. Upon application creation public / private key-pair is generated to enable validation and encription for later use (licence files)
2. For each application user can generate serial numbers (UUID)

#### End user application part
1. User can enable registration of their app clients through simple API that ALS provides 
2. Upon client registration licence is generated

Licence file is generated:

> E(H(client data + serial number))  |pk

Hash file created from client data and serial number is encrypted (signed) using application's private key (generated in step 1. in User part)

Application can then in any given time request licence check from ALS:

1. Get licence file from user
2. Decrypt file using application's public key (public key could be shipped with application) - integrity and authenticity check
3. Hash local registration data and compare with licence hash (must be equal)


