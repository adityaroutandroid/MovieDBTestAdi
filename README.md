# MovieDBTestAdi
 showcasing the latest technologies and architecture patterns using the [Movie Database](https://www.themoviedb.org/) APIs.
# Technologies
- Kotlin Coroutines, Flow, StateFlow
- Hilt
- Paging3
- Navigation Component
- LiveData
- ViewModel
- Room
- Retrofit
- OkHttp3
- Glide
- jUnit
- Mockk
- Coroutine Test

# Architecture
A custom architecture inspired by the [Google MVVM] and the [Clean architecture]

This architecture allows app to be offline first. It gets data from the network if it doesn't exist in the local database and persists it. 
Local database is the single source of truth of the app and after its data changes, it notifies other layers using coroutine flows. 

# Build
Clone the repository and get an [API key](https://www.themoviedb.org/settings/api) from the Movie Database and put it in the `local.properties` file as below:

```properties
apikey="YOUR_API_KEY"
