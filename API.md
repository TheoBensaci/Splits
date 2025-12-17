# Users API
The SPLITS API allows to manage run, players data, split and more. It uses the HTTP protocol and the JSON format.

The API is based on the CRUD pattern. It has the following operations:
- Create (sign in) a new player
- Create a new run
- Start, stop, forfeit a run
- Post "split" to a run
- Get all runs of a games (finished or currently running)
- Get the state of a run (current split, list of all past split, statue, players involve

## Endpoints
The origin of the API routing is `/api/*`
### Sign in a new player
- `POST /api/player/new`
Create a new player with the given
#### Request 
The request body need a JSON object like this :
```JSON
{
  "username": "{requested username}"
}
```

#### Respond
The respond will contain the following JSON object
```JSON
{
  "token": "{token}"
}
```
1) If the username requested was available, then the **{token}** in the response will be the token link to the player.
This token will be used to as the key component of a bunch of request, so, be care full and dont lose it.
**WARNING** : at the time, there is no server database, therefor every server restart will clear all player and tokens

#### Status codes
- `201` (Created) - The player has been successfully created.
- `400` (Bad Request) - The request body is invalid.
- `409` (Conflict) - The player already exists.

### Get player information
- `POST /api/player/get`
Get player data using the token, also useful to see if a token still work
#### Request
The request body need a JSON object like this :
```JSON
{
  "token": "{token}"
}
```

#### Respond
The respond will contain the following JSON object
```JSON
{
  "username": "{username of the player}"
}
```

#### Status codes
- `201` (Success) - The token is correct
- `400` (Bad Request) - The request body is invalid.
- `409` (Fail) - The token isn't use by any player.



base for every entry
```md
### Sign in a new player
- `POST /player/new`
  DESC
#### Request
The request body need a JSON object like this :
```JSON
{
  "username": "{requested username}"
}
```

#### Respond
The respond will contain the following JSON object
```JSON
{
  "token": "{token}"
}
```

#### Status codes
- `201` (Created) - The user has been successfully created.
- `400` (Bad Request) - The request body is invalid.
- `409` (Conflict) - The user already exists.
```

