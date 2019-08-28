# IM-Assignment

This assignment implements core Android functionalities such as :
- SQLiteDatabase
- Recycler View
- Loaders
- HTTPUrlConnection
- Async Tasks

Further advanced implementations added:
- MVVM architecture in [ContactListFragment](https://github.com/chauhan-abhi/IM-Assignment/blob/master/app/src/main/java/com/example/demo_day1/ui/contactList/ContactListFragment.kt)
- [RxJava](https://github.com/ReactiveX/RxJava)
- [Dagger](https://github.com/google/dagger)
- [Retrofit](https://github.com/square/retrofit)
- Repository Pattern
- [AndroidX](https://developer.android.com/jetpack/androidx)
- [Standard Functions](https://kotlinlang.org/docs/reference/scope-functions.html)
- [Kotlin Extensions](https://kotlinlang.org/docs/reference/extensions.html)
- [Binding Adapters](https://developer.android.com/topic/libraries/data-binding/binding-adapters)

Repository implementation for chaining multiple data sources:
- Used concat operator with delayedError to combine observables from local database and remote service in 
[ContactsRepository](https://github.com/chauhan-abhi/IM-Assignment/blob/master/app/src/main/java/com/example/demo_day1/ui/contactList/repository/ContactsRepository.kt)

```kotlin
 fun getContactList(): Observable<List<Contact>> {
        val hasConnection = appUtils.isConnectedToInternet()
        return if (hasConnection)
            Observable.concat(
                getContactsFromDb().subscribeOn(Schedulers.io()),
                getContactsFromApi().subscribeOn(Schedulers.io()))
        else
            getContactsFromDb()
    }
```
