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
### Get list of available games
- `GET /games`
Returns the list of all available games on the server.
#### Request 
This endpoint does not require a request body.

#### Respond
The respond will contain the following JSON object
```JSON
[
  "borris",
  "jermy",
  "minecraft",
  "celeste"
]
```
#### Status codes
200 OK – Games list successfully retrieved


### Get game data
- `GET /game/{gameName}`
Returns detailed information about a specific game.
##### Request
This endpoint does not require a request body.

##### Respond
Example of respond : 
```JSON
{
	"name": "borris",
	"finalFlagIndex": 6,
	"flags": [
		{
			"name": "sword",
			"priority": 0
		},
		{
			"name": "magic burst",
			"priority": 0
		},
		{
			"name": "fast travel",
			"priority": 0
		},
		{
			"name": "sword dash",
			"priority": 0
		},
		{
			"name": "master key",
			"priority": 1
		},
		{
			"name": "ascension",
			"priority": 2
		},
		{
			"name": "gg ?",
			"priority": 3
		}
	],
	"numberOfRun": 0,
	"numberOfFlags": 7,
	"runs": []
}
```
#### Status codes
- `200` OK – Game found
- `404` Not Found – Game does not exist

### Get runs list for a game
- `GET /game/{gameName}/runs`
Returns all runs associated with a game.
##### Request
No request body required.
##### Respond
List of data describing the current state of all runs
```JSON
[
  {
    "entries": [
      {
        "player": {
          "username": "Bowser"
        },
        "startTime": 0,
        "splits": [],       // list of all splists
        "actualSplit": null,  // actual splits
        "running": false,   // if the entry is running
        "finish": false     // if the entry as finish
      }
    ],
    "id": 0,
    "running": false,   // if the run is running
    "finish": false     // if the run is finish
  }
]
```
#### Status codes
- `200` OK – Game found
- `404` Not Found – Game does not exist

### Create a new run
- `POST /game/{gameName}/create`
Creates a new run for the specified game.
This request will also make the user creating the run the owner of the run and a participant of it
##### Request
The request require the following body
```JSON
{
  "token": {user token}
}
```
##### Respond
No respond body will be send.
#### Status codes
- `201` CREATED – Run was successfully created
- `500` SERVER INTERNAL ERROR - Unknown error as occur
- `404` Not Found – Game does not exist
- `400` Bad Request – The body as malformed

### Get run state
- `GET /game/{gameName}/{id}`
Get a specific run data

##### Request
No request body required.
##### Respond
Example respond
```JSON
{
"entries": [
  {
    "player": {
      "username": "Bowser"
    },
    "startTime": 0,
    "splits": [],       // list of all splists
    "actualSplit": null,  // actual splits
    "running": false,   // if the entry is running
    "finish": false     // if the entry as finish
  }
],
"id": 0,
"running": false,   // if the run is running
"finish": false     // if the run is finish
}
```
#### Status codes
- `200` OK – Run found
- `404` Not Found – Game or run not found

### Join a run
- `POST /game/{gameName}/{id}/join`
Make a user join a run (Is it as not all ready start of finish)
##### Request
The request require the following body
```JSON
{
  "token": {user token}
}
```
##### Respond
No respond body will be send.
#### Status codes
- `200` OK – Player joined
- `400` Bad Request – Run is full or already started or the body as malformed
- `409` Conflit - The user is all ready in this run
- `404` Not Found – Game or run not found
- `500` Internal Server Error – Server error

### Start a run
- POST `/game/{gameName}/{id}/start`
Make a run start, can only be done by the owner of the run
##### Request
The request require the following body
```JSON
{
  "token": {user token}
}
```
##### Respond
No respond body will be send.
#### Status codes
- `200` OK – Run started
- `400` Bad Request – Run already started or the body as malformed
- `404` Not Found – Run not found
- `500` Internal Server Error – Server error
- `401` Unauthorized - the user is not the owner of the run


### Post a split
- `POST /game/{gameName}/{id}/put`
Submits a new split time for the current player.
##### Request
The request require the following body
```JSON
{
  "token": {user token},
  "flagIndex": {current flag index},
  "time" : {delta from the start of the run in time in millisec}
}
```
##### Respond
No respond body will be send.
#### Status codes
- `200` OK – Split recorded
- `400` Bad Request – the body as malformed or the split was invalid (time need to be greater then the last)
- `404` Not Found – Run not found
- `500` Internal Server Error – Server error

### Delete a run
- `DELETE /game/{gameName}/{id}`
Delete the run
Only the owner is authorized to do that
##### Request
The request require the following body
```JSON
{
  "token": {user token}
}
```
##### Respond
No respond body will be send.
#### Status codes
- `200` OK – Run deleted
- `400` Bad Request – the body as malformed
- `404` Not Found – Run not found
- `500` Internal Server Error – Server error
- `401` Unauthorized - the user is not the owner of the run


### Create a player
- `POST /user/create`
Create a new user
##### Request
The request require the following body
```JSON
{
  "token": {user token}
}
```
##### Respond
The respond will containe the token of the user, don't lose it. There will be no way to get it again
```JSON
{
  "token": {user token}
}
```
#### Status codes
- `201` Created – Player created
- `400` Bad Request – Invalid username
- `500` Internal Server Error – Server error


### Get player data
- `GET /user?username={username}`

Returns data of a specific user
##### Request
No request body required.
##### Respond
Example respond
```JSON
{
  "name": "Bowser"
}
```
#### Status codes
- `200` OK – Player data retrieved
- `404` Not Found – Player not found
- `500` Internal Server Error – Server error


### Change player username
- `PATCH /user/change`
change the username of a user.
##### Request
The request require the following body
```JSON
{
  "token": {user token},
  "name" : {new name}
}
```
##### Respond
No respond body will be send.
#### Status codes
- `200` OK – Username changed
- `400` Bad Request – Invalid username or the body as malformed
- `404` Not Found – Player not found
- `500` Internal Server Error – Server error