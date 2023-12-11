# testApiRandom
Use the api RandomUser in the Android app

Important to use this project:

* To view the details of the maps, it is necessary to obtain a Google Maps API KEY and enter it in the project's manifest.

Description the test

Apply the api RandomUser https://randomuser.me/api/ in a app Android with Kotlin. All the information about the API can be found at: https://randomuser.me

The exercise involves searching for users and displaying them on a first screen in a list. Then, on another screen, show the details of each user.


Other info

* MVVM
* Clean Architecture
* Retrofit
* Navigation Component
* Coroutines
* Unit Test (Mockk)
* Picasso
* Google Maps

Note:
The project has been carried out between December 8th and December 10th. An attempt has been made to structure it with Clean Architecture, making use of coroutines, among other technologies. Unit tests have been conducted using Mockk for the service class. Icons could not be integrated as they could not be downloaded from the previous example; instead, color spaces have been used. The search by name or email requested could not be integrated. An issue encountered was with the service model; the documentation indicates that IDs cannot be null, but in some cases, they were. This posed a challenge and required time to understand the issue since not all users were affected. The solution, once discovered, was straightforward.
