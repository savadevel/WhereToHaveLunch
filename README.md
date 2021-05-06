# Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.

The task is:

Build a voting system for deciding where to have lunch.

2 types of users: admin and regular users Admin can input a restaurant and it's lunch menu of the day (2-5 items
usually, just a dish name and price)
Menu changes each day (admins do the updates)
Users can vote on which restaurant they want to have lunch at Only one vote counted per user. If user votes again the
same day. If it is before 11:00 we assume that he changed his mind. If it is after 11:00 then it is too late, vote can't be
changed. Each restaurant provides a new menu each day.

Diagram of entities / tables:

![diagram.png](diagram.png)

## Using cURL for manage system for deciding where to have lunch by REST queries

| URL                                                                                                                       | Method | Role  | Description                                                                  |
| ---                                                                                                                       |---     |---    | ---                                                                          |
| [/rest/restaurants/menus](#Displays-menus-of-restaurants-on-current-date)                                                 | GET    | USER  | displays menus of restaurants on current date                                |
| [/rest/restaurants/votes](#Display-amount-votes-on-restaurants-on-current-date)                                           | GET    | USER  | displays amount votes for restaurants on current date                        |
| [/rest/restaurants/votes/{vote-id}](#Get-vote-by-id)                                                                      | GET    | USER  | get vote by id                                                               |
| [/rest/restaurants/votes](#Add-only-one-vote-for-the-restaurant-on-current-date)                                          | POST   | USER  | add only one vote for the restaurant on current date, if it's before 11:00   |
| [/rest/restaurants/votes/{vote-id}](#Change-vote-for-the-restaurant-on-current-date)                                      | PATCH  | USER  | change vote for the restaurant on current date, if it's before 11:00         |
| [/rest/profile/vote](#Displays-vote-of-the-user-on-current-date)                                                          | GET    | USER  | displays vote of the user on current date                                    |
| [/rest/admin/restaurants](#Displays-dictionary-of-restaurants)                                                            | GET    | ADMIN | displays dictionary of restaurants                                           |
| [/rest/admin/restaurants/{restaurant-id}](#Get-restaurant-by-id)                                                          | GET    | ADMIN | get restaurant by id                                                         |
| [/rest/admin/restaurants](#Add-a-restaurant-to-dictionary)                                                                | POST   | ADMIN | add restaurant to dictionary                                                 |
| [/rest/admin/dishes](#Displays-dictionary-of-dishes)                                                                      | GET    | ADMIN | displays dictionary of dishes                                                |
| [/rest/admin/dishes/{dish-id}](#Get-dish-by-id)                                                                           | GET    | ADMIN | get dish by id                                                               |
| [/rest/admin/dishes](#Add-a-dish-to-dictionary)                                                                           | POST   | ADMIN | add dish to dictionary                                                       |
| [/rest/admin/restaurants/{restaurant-id}/menus](#Displays-dictionary-of-menus-the-restaurant-by-dates)                    | GET    | ADMIN | displays dictionary of menus the restaurant by dates                         |
| [/rest/admin/restaurants/{restaurant-id}/menus?date=2021-01-01](#Displays-dictionary-of-menus-the-restaurant-on-the-date) | GET    | ADMIN | displays dictionary of menus the restaurant on the date                      |
| [/rest/admin/restaurants/menus/{menu-id}](#Get-menu-by-id)                                                                | GET    | ADMIN | get menu by id                                                               |
| [/rest/admin/restaurants/menus](#Add-dish-to-the-menu-of-the-restaurant-on-current-date)                                  | POST   | ADMIN | add dish to the menu of the restaurant on current date                       |

### Displays menus of restaurants on current date
Sorting the menus: asc name of restaurant, after this asc name of dish
The operation available for USER role, request's caching (cache name's 'restaurants')

Code samples (shell)

```shell
curl \
  -X GET \
  --user username:password \
  http://localhost:8080/wthl/rest/restaurants/menus
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

### Display amount votes on restaurants on current date
Sorting the votes: desc by date and amount votes, after this asc name of restaurant
The operation available for USER role, request's caching (cache name's 'votes')

Code samples (shell)

```shell
curl \
  -X GET \
  --user username:password \
  http://localhost:8080/wthl/rest/restaurants/votes
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

### Get vote by id

The operation available only to the owner of the voice

Code samples (shell)

```shell
curl \
  -X GET \
  --user username:password \
  http://localhost:8080/wthl/rest/restaurants/votes/{vote-id}
```

Response

```
Status: 200 OK
```

```
{
    "id":1,
    "restaurant":
        {
            "id": 2,
            "name": "Good restaurant"
        },
    "date":"2021-01-01"
} 
```

### Add only one vote for the restaurant on current date

The operation available for USER role, if it is before 11:00 and the restaurant has a menu.
Request will evict 'votes' cache

Code samples (shell)

```shell
curl \
  -X POST \
  --user username:password \
  http://localhost:8080/wthl/rest/restaurants/votes \
  -d '{"restaurantsId":2}'
```

Response

```
Status: 201 Created
Location: /rest/restaurants/votes/{vote-id}
```

```
{
    "id":4,
    "restaurant":
        {
            "id": 2,
            "name": "Good restaurant"
        },
    "date":"2021-01-01"
} 
```

### Change vote for the restaurant on current date

The operation available for USER role, if it is before 11:00 and the restaurant has a menu.
Request will evict 'votes' cache

Code samples (shell)

```shell
curl \
  -X PATCH \
  --user username:password \
  http://localhost:8080/wthl/rest/restaurants/votes/{vote-id} \
  -d '{"id":4,"restaurantsId":3}'
```

Response

```
Status: 204 No Content
```

### Displays vote of the user on current date 

The operation available for USER role

Code samples (shell)

```shell
curl \
  -X GET \
  --user username:password \
  http://localhost:8080/wthl/rest/profile/vote
```

Response

```
Status: 200 OK
```

```
{
    "id":1,
    "restaurant":
        {
            "id": 2,
            "name": "Good restaurant"
        },
    "date":"2021-01-01"
} 
```

### Displays dictionary of restaurants

The operation available for ADMIN role

Code samples (shell)

```shell
curl \
  -X GET \
  --user username:password \
  http://localhost:8080/wthl/rest/admin/restaurants
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

### Get restaurant by id

The operation available for ADMIN role

Code samples (shell)

```shell
curl \
  -X GET \
  --user username:password \
  http://localhost:8080/wthl/rest/admin/restaurants/1
```

Response

```
Status: 200 OK
```

```
{
    "id": 1,
    "restaurant": "Good restaurant",
}
```

### Add a restaurant to dictionary

The operation available for ADMIN role

Code samples (shell)

```shell
curl \
  -X POST \
  --user username:password \
  http://localhost:8080/wthl/rest/admin/restaurants \
  -d '{"name":"Name of new Restaurant"}'
```

Response

```
Status: 201 Created
Location: /rest/admin/restaurant/{restaurant-id}
```

```
{
    "id": 2,
    "name": "Name of new Restaurant"
}
```

### Displays dictionary of dishes

The operation available for ADMIN role

Code samples (shell)

```shell
curl \
  -X GET \
  --user username:password \
  http://localhost:8080/wthl/rest/admin/dishes
```

Response

```
Status: 200 OK
```

```
[
    {
        "id:3,
        "dish":"First dish", 
        "price":1.23 
    }
]
```

### Get dish by id

The operation available for ADMIN role

Code samples (shell)

```shell
curl \
  -X GET \
  --user username:password \
  http://localhost:8080/wthl/rest/admin/dishes/3
```

Response

```
Status: 200 OK
```

```
{
    "id:3,
    "dish":"First dish", 
    "price":1.23 
}
```

### Add a dish to dictionary

The operation available for ADMIN role

Code samples (shell)

```shell
curl \
  -X POST \
  --user username:password \
  http://localhost:8080/wthl/rest/admin/dishes \
  -d '{"name":"Name of new dish","price":1.23}'
```

Response

```
Status: 201 Created
Location: /rest/admin/dishes/{dish-id}
```

```
{
    "id": 4,
    "name": "Name of new dish",
    "price": 1.23
}
```

### Displays dictionary of menus the restaurant by dates

The operation available for ADMIN role

Code samples (shell)

```shell
curl \
  -X GET \
  --user username:password \
  http://localhost:8080/wthl/rest/admin/restaurants/{restaurant-id}/menus
```

Response

```
Status: 200 OK
```

```
[
    {
        "id":5,
        "date":"2021-01-01",
        "restaurant":
            {
                "id": 1,
                "name": "Good restaurant"
            },
        "dish":
            {
                "id":3,
                "name":"First dish", 
                "price":1.23 
            }
    } 
]
```

### Displays dictionary of menus the restaurant on the date

The operation available for ADMIN role

Code samples (shell)

```shell
curl \
  -X GET \
  --user username:password \
  http://localhost:8080/wthl/rest/admin/restaurants/{restaurant-id}/menus?date=2021-01-01
```

Response

```
Status: 200 OK
```

```
[
    {
        "id":5,
        "date":"2021-01-01",
        "restaurant":
            {
                "id": 1,
                "name": "Good restaurant"
            },
        "dish":
            {
                "id":3,
                "name":"First dish", 
                "price":1.23 
            }
    } 
]
```

### Get menu by id

The operation available for ADMIN role

Code samples (shell)

```shell
curl \
  -X GET \
  --user username:password \
  http://localhost:8080/wthl/rest/admin/menus/{menu-id}
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
    "dish":
        {
            "id":3,
            "name":"First dish", 
            "price":1.23 
        }
} 
```

### Add dish to the menu of the restaurant on current date

The operation available for ADMIN role, request will evict 'votes' and 'restaurants' caches

Code samples (shell)

```shell
curl \
  -X POST \
  --user username:password \
  http://localhost:8080/wthl/rest/admin/menus \
  -d '{"restaurantId":2,"dishId":4}'
```

Response

```
Status: 201 Created
Location: /rest/admin/menus/{menu-id}
```

```
{
    "id": 1,
    "date":"2021-01-01",
    "restaurant":
    {
        "id":2
    },
    "dish":
    {
        "id:4 
    }
}
```