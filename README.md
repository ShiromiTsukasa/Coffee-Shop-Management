# Coffee Shop Management

# Table of Content
- [Coffee Shop Management](#coffee-shop-management)
- [Table of Content](#table-of-content)
- [Introduction](#introduction)
- [How to use](#how-to-use)
  - [Client](#client)
  - [Admin](#admin)
- [How to build](#how-to-build)
  - [Dependencies](#dependencies)

# Introduction
This project is a simple coffee shop management and ordering system.

# How to use
## Client
- Login to the account. if you don't have an account yet, you can click on `Register` to register the account and then you can login.
- Click on the drink that you like and then choose size and quantity.
- After choose all drinks that you want to buy click on `Confirm order` to finish your order.
- After finish your order you click on `Log out` to log out your account from application.

## Admin
- If it is the first time that you login, you will be asked to setup a first admin account.
- From there, you can view and complete orders as they come.
- To complete an order, select the order that you want to complete from the left table, then click on the `Complete the order` button.
- Don't forget to press `refresh`, we don't have an automatic refresh system yet.
- You can also press the `Add new Admin` button to add a new admin account for a new administrator.
- Press the `Log out` button to log out.


# How to build
## Dependencies
- [Maven](https://maven.apache.org/)

In case that you would like to build the project yourself, you can use the following commands to build the two programs.

Clone the `main` branch for the stable, but slower updates, or the `development` branch for newer and faster, but with possibly incomplete features, may crash, and not guarenteed to work. 

First, find the following import statement in the file `src/main/java/com/main/SuperMain.java`.
```java
import com.UserAs??.Main;
```

When you want to build the client program (`client.jar`), you change the import statement into
```java
import com.UserAsClient.Main;
```

else, if you want to build the admin program (`admin.jar`), you change the import statement into
```java
import com.UserAsAdmin.Main;
```

After changing the import statement, run this statements in the root directory of the project.
```pwsh
mvn clean package
```

From there, you should see the file `target/coffee-management-${version}.jar`.

Rename the file appropriately, and copy it to the directory that you would like it to be run.

**Note: Due to the way that it currently works as of v1.1.0, the `admin.jar` and the `client.jar` must be in the same directory to be able to run correctly.**
