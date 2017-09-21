# Pikmail

This is a utility written in [Kotlin](http://kotlinlang.org/) that consumes one public [picasa endpoint](http://picasaweb.google.com/data/entry/api/user/eduardo.alejandro.pool.ake@gmail.com?alt=json) to fetch the google profile picture for the email along with the nickname.

## Libraries

This project uses the following libraries internally to work.

### Development libraries

- [Retrofit](http://square.github.io/retrofit/) used to consume the Picasa api.
- [Retrofit Gson Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/gson) converter to deserialize JSON responses from the picasa api.
- [RxJava2](https://github.com/ReactiveX/RxJava) async requests.
- [Okhttp Logging Interceptor](https://github.com/ReactiveX/RxJava) for logging http requests/responses for the Picasa api.

### Testing libraries

- [Spek](http://spekframework.org/) Used to write some integration tests as specs.

## Usage

### Dependency

```groovy
repositories {
    ...
    maven { url 'https://jitpack.io' }
    ...
}

dependencies {
    ...
    implementation "com.github.epool:pikmail:1.0.0"
    ...
}
```

### Blocking

Use it when blocking is safe to use like on web servers.

###### Java

```java
Profile profile = Pikmail.INSTANCE.getProfile("eduardo.alejandro.pool.ake@gmail.com").blockingGet();
profile.getProfilePictureUrl();
profile.resizeProfilePictureUrl(500);
profile.getNickname();
// OR
Pikmail.INSTANCE.getProfilePictureUrl("eduardo.alejandro.pool.ake@gmail.com", null).blockingGet();
Pikmail.INSTANCE.getProfilePictureUrl("eduardo.alejandro.pool.ake@gmail.com", 500).blockingGet();
Pikmail.INSTANCE.getProfileNickname("eduardo.alejandro.pool.ake@gmail.com").blockingGet();
```

###### Kotlin

```kotlin
val profile = Pikmail.getProfile("eduardo.alejandro.pool.ake@gmail.com").blockingGet()
profile.profilePictureUrl
profile.resizeProfilePictureUrl(500)
profile.nickname
// OR
Pikmail.getProfilePictureUrl(email).blockingGet()
Pikmail.getProfilePictureUrl(email, 500).blockingGet()
Pikmail.getProfileNickname(email).blockingGet()
```

### Async

Use it when blocking is not safe to use like on Android main thread.

###### Java

```java
Pikmail.INSTANCE.getProfile("eduardo.alejandro.pool.ake@gmail.com")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread()) // In case you are using it on Android or use any other scheduler you need
        .subscribe(
                new Consumer<Profile>() {
                    @Override
                    public void accept(Profile profile) throws Exception {
                        System.out.println(profile.toString());
                    }
                },
                new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println(throwable.getCause().toString());
                    }
                }
        );
```

###### Kotlin

```kotlin
Pikmail.getProfile("eduardo.alejandro.pool.ake@gmail.com")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                { println(it.toString()) },
                { println(it.cause.toString()) }
        )
```

**NOTE:** If the profile is not found or if the gmail address is invalid you will receive a [ProfileNotFountException](https://github.com/epool/pikmail/blob/master/src/main/kotlin/com/nearsoft/pikmail/ProfileNotFountException.kt). You could take a look on the integrations tests [here](https://github.com/epool/pikmail/blob/master/src/test/kotlin/com/nearsoft/pikmail/PikmailTest.kt) to get a better idea about how to use the Pikmail api. 

