
# Group 11: Make It Safe

## Commands
SHOW all possible commands available:
```
help: Display available commands and password security rules
```

DISPLAY all usernames is empty or be specific with website and username if needed:
```
display <Optional WEBSITE> <Optional USERNAME>: Display all USERNAMES
```

ADD
```
add <WEBSITE> <USERNAME> <PASSWORD>: Add USERNAME with PASSWORD to WEBSITE
```

UPDATE
```
update <WEBSITE> <USERNAME> <PASSWORD>: Update USERNAME with PASSWORD to WEBSITE
```

DELETE
```
delete <WEBSITE> <USERNAME>: Delete USERNAME for the given WEBSITE
```


PASSWORD SECURITY RULES:

- Rule 1 : Less than 8 characters

- Rule 2 : Has letters

- Rule 3 : Has numbers

- Rule 4 : Has special characters

Password levels: Really Weak (1/4), Weak (2/4), Medium(3/4), Strong (4/4)


## Command file example

```
help
add www.google.com username1 password1
add www.google.com username11 password11
display
```

In order to execute the JAR file, a file path needs to be specified as a parameter for MakeItSafe.jar in the following way:

```
java -jar MakeItSafe.jar commands.txt
```
