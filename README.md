# iMakerBot-BackendEnd
iMakerBot-BackendEnd Project.


# Zuul Gateway documentation :
1- change the database propreties with your own proprietes such as username , password, url (Make sure to create the database before you run the project ).

2- After running the project , in your browser tape http://localhost:8080/swagger-ui.html to acces the rest api documentation , in the home controller panel you will find three request :
  - A POST request for registration  http://localhost:8080/api/home/signup ie: make sure you register      with a valid mail .
  - A POST request for account confirmation example : http://localhost:8080/api/home/validation?token=63067742-dcb6-45b9-bace-252618ffe0a6 ie: you can just pass the token recieved by mail in the swagger-ui .
  - A POST request for login example :  http://localhost:8080/api/home/signin ie : after successful login you will find the token in the header like this authorazation: Bearer xxxxxxxxxxxxxxxxxxxxxx.   
