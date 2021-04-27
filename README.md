# Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.

The task is:

Build a voting system for deciding where to have lunch.

2 types of users: admin and regular users Admin can input a restaurant and it's lunch menu of the day (2-5 items
usually, just a dish name and price)
Menu changes each day (admins do the updates)
Users can vote on which restaurant they want to have lunch at Only one vote counted per user If user votes again the
same day:
If it is before 11:00 we assume that he changed his mind. If it is after 11:00 then it is too late, vote can't be
changed Each restaurant provides a new menu each day.

As a result, provide a link to github repository. It should contain the code, README.md with API documentation and
couple curl commands to test it.

P.S.: Make sure everything works with latest version that is on github :)

P.P.S.: Assume that your API will be used by a frontend developer to build frontend on top of that.

Diagram of entities / tables (https://app.sqldbm.com/PostgreSQL/Edit/p158862/#):
![diagram.png](diagram.png)

## Using cURL for manage system for deciding where to have lunch by REST queries

| URL                                                       | Method | Role  | Description                                                                  |
| ---                                                       |---     |---    | ---                                                                          |
| /rest/restaurants/votes                                   | GET    | USER  | displays amount votes on restaurants by dates                                |
| /rest/restaurants/votes?date="2021-01-01"                 | GET    | USER  | displays amount votes on restaurants on date                                 |
| /rest/restaurants                                         | GET    | ADMIN | displays list restaurants                                                    |
| /rest/restaurants/{restaurant-id}                         | GET    | ADMIN | get restaurant by id                                                         |
| /rest/restaurants                                         | POST   | ADMIN | add restaurant                                                               |
| /rest/dishes                                              | GET    | ADMIN | displays list dishes                                                         |
| /rest/dishes/{dish-id}                                    | GET    | ADMIN | get dish by id                                                               |
| /rest/dishes                                              | POST   | ADMIN | add dish                                                                     |
| /rest/restaurants/{restaurant-id}/menus                   | GET    | ADMIN | displays list menus of the restaurant by dates                               |
| /rest/restaurants/{restaurant-id}/menus/{menu-id}         | GET    | ADMIN | get menu of the restaurant by id                                             |
| /rest/restaurants/{restaurant-id}/menus                   | POST   | ADMIN | add dish to the menu of the restaurant on current date                       |
| /rest/restaurants/{restaurant-id}/votes/{vote-id}         | GET    | USER  | get vote by id                                                               |
| /rest/restaurants/{restaurant-id}/votes                   | POST   | USER  | add only one vote for the restaurant on current date (if it's before 11:00)  |
| /rest/restaurants/{restaurant-id}/votes/{vote-id}         | PUT    | USER  | change vote for the restaurant on current date (if it's before 11:00)        |
| /rest/profile/votes                                       | GET    | USER  | displays list of votes by date of the user                                   |
| /rest/profile/votes?date="2021-01-01"                     | GET    | USER  | displays vote of the user on date                                            |

### Display amount votes on restaurants by dates, order desc by: date, votes and name of restaurant

The operation available for USER only.

Code samples (shell)

```shell
curl \
  -X GET \
  http://localhost:8080/wthl/rest/restaurants/votes
```

Response

```
Status: 200 OK
```

```
[
    {
        "onDate":"2021-01-02",
        "restaurant":
            {
                "id": 2,
                "name": "Best restaurant"
            },
        "votes:4
    },
    {
        "onDate":"2021-01-02",
        "restaurant":
            {
                "id": 1,
                "name": "Good restaurant"
            },
        "votes:3
    },
    {
        "onDate":"2021-01-01",
        "restaurant":
            {
                "id": 1,
                "name": "Good restaurant"
            },
        "votes:5
    },
    {
        "onDate":"2021-01-01",
        "restaurant":
            {
                "id": 2,
                "name": "Best restaurant"
            },
        "votes:3
    }
]
```

### Display amount votes on restaurants on date, order desc by: votes and name of restaurant

The operation available for USER only.

Code samples (shell)

```shell
curl \
  -X GET \
  http://localhost:8080/wthl/rest/restaurants/votes?date="2021-01-01"
```

Response

```
Status: 200 OK
```

```
[
    {
        "date":"2021-01-01",
        "restaurant":
            {
                "id": 1,
                "name": "Good restaurant"
            },
        "votes:4
    },
    {
        "date":"2021-01-01",
        "restaurant":
            {
                "id": 2,
                "name": "Best restaurant"
            },
        "votes:3
    }
]
```

### Display list restaurants

The operation available for ADMIN only.

Code samples (shell)

```shell
curl \
  -X GET \
  http://localhost:8080/wthl/rest/restaurants
```

Response

```
Status: 200 OK
```

```
[
    {
        "id": 1,
        "restaurant": "Good restaurant",
    }
]
```

### Add a restaurant

The operation available for ADMIN only.

Code samples (shell)

```shell
curl \
  -X POST \
  http://localhost:8080/wthl/rest/restaurants
  -d '{"name":"Name of new Restaurant"}'
```

Response

```
Status: 201 Created
Location: /rest/restaurant/{restaurant-id}
```

```
{
    "id": 10,
    "name": "Name of new Restaurant"
}
```

### Display list dishes

The operation available for ADMIN only

Code samples (shell)

```shell
curl \
  -X GET \
  http://localhost:8080/wthl/rest/dishes
```

Response

```
Status: 200 OK
```

```
[
    {
        "id:2,
        "dish":"First dish", 
        "price":1.23 
    }
]
```

### Add a dish

The operation available for ADMIN only

Code samples (shell)

```shell
curl \
  -X POST \
  http://localhost:8080/wthl/rest/dishes
  -d '{"name":"Name of new dish","price":1.23}'
```

Response

```
Status: 201 Created
Location: /rest/dishes/{dish-id}
```

```
{
    "id": 20,
    "name": "Name of new dish",
    "price": 1.23
}
```

### Displays list menus of the restaurant by dates

The operation available for ADMIN only

Code samples (shell)

```shell
curl \
  -X GET \
  http://localhost:8080/wthl/rest/restaurants/{restaurant-id}/menus
```

Response

```
Status: 200 OK
```

```
[
    {
        "id":1,
        "date":"2021-01-01",
        "restaurant":
            {
                "id": 2,
                "name": "Good restaurant"
            },
        "dish":
            {
                "id:3,
                "name":"First dish", 
                "price":1.23 
            }
    } 
]
```

### Add dish to the menu of the restaurant on current date

The operation available for ADMIN only

Code samples (shell)

```shell
curl \
  -X POST \
  http://localhost:8080/wthl/rest/restaurants/{restaurant-id}/menus 
  -d '{"dishId":10}'
```

Response

```
Status: 201 Created
Location: /rest/restaurants/{restaurant-id}/menus/{menu-id}
```

```
{
    "id": 1,
    "date":"2021-01-01",
    "restaurant":
    {
        "id": 2,
        "name": "Good restaurant"
    },
    "dish":
    {
        "id:3,
        "name":"First dish", 
        "price":1.23 
    }
}
```

### Get vote by id

The operation available for USER only

Code samples (shell)

```shell
curl \
  -X GET \
  http://localhost:8080/wthl/rest/restaurants/{restaurant-id}/votes/{vote-id}
```

Response

```
Status: 200 OK
```

```
{
    "id":1,
    "date":"2021-01-01",
    "restaurant":
        {
            "id": 2,
            "name": "Good restaurant"
        },
    "user":
        {
            "id:3,
            "name":"user"
        }
} 
```

### Add only one vote for the restaurant on current date (if it is before 11:00)

The operation available for USER only

Code samples (shell)

```shell
curl \
  -X POST \
  http://localhost:8080/wthl/rest/restaurants/{restaurant-id}/votes
```

Response

```
Status: 201 Created
Location: /rest/restaurants/{restaurant-id}/votes/{vote-id}
```

```
{
    "id":1,
    "date":"2021-01-01",
    "restaurant":
        {
            "id": 2,
            "name": "Good restaurant"
        },
    "user":
        {
            "id:3,
            "name":"user"
        }
} 
```

### Change vote for the restaurant on current date (if it is before 11:00)

The operation available for USER only

Code samples (shell)

```shell
curl \
  -X PUT \
  http://localhost:8080/wthl/rest/restaurants/{restaurant-id}/votes/{vote-id} 
```

Response

```
Status: 204 No Content
```

### Displays list of votes by date of the user

The operation available for USER only

Code samples (shell)

```shell
curl \
  -X GET \
  http://localhost:8080/wthl/rest/profile/votes
```

Response

```
Status: 200 OK
```

```
[
    {
        "id":1,
        "date":"2021-01-01",
        "restaurant":
            {
                "id": 2,
                "name": "Good restaurant"
            },
        "user":
            {
                "id:3,
                "name":"user"
            }
    }
] 
```

### Displays lvote of the user on date

The operation available for USER only

Code samples (shell)

```shell
curl \
  -X GET \
  http://localhost:8080/wthl/rest/profile/votes?date="2021-01-01"
```

Response

```
Status: 200 OK
```

```
{
    "id":1,
    "date":"2021-01-01",
    "restaurant":
        {
            "id": 2,
            "name": "Good restaurant"
        },
    "user":
        {
            "id:3,
            "name":"user"
        }
} 
```