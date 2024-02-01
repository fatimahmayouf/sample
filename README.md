# Spring Boot RESTful API Sample Project

This is a sample Spring Boot project for building RESTful APIs with a Microsoft Access database.

## Database

- **Database**: MS Access

## Setup Instructions

1. Clone the repository to your local machine.

   ```bash
   git clone <https://github.com/fatimahmayouf/sample.git>

2. In `application.yml`:
   - `url`: Provide the path to your Access database.

```
        url: jdbc:ucanaccess://${DATABASE_PATH} 
```    
   -  `username`: Your Microsoft account username.
```
      username:  ${MICROSOFT_USERNAME}
```           

-  `password`: If you are using the database attached with this project, the password is `Developer@123`.
   - If you are using a database from your end, please mention your password.
```    
        password: ${DATABASE_PASSWORD}

```
   - If you have the database without tables and you want the system to create tables for you, please uncomment the following lines:
       ```yaml
     hibernate:
        ddl-auto: update
      ```
     

## Testing

- **The project includes unit tests to ensure the correctness of the API endpoints and service layer logic**
- **Test classes can be found in the src/test/java directory.**

