# Pikmail

This is a utility written in [Kotlin](http://kotlinlang.org/) that consumes one public [picasa endpoint](http://picasaweb.google.com/data/entry/api/user/eduardo.alejandro.pool.ake@gmail.com?alt=json) to fetch the google profile picture for the email along with the nickname.

## Libraries

This project uses the following libraries internally to work.

### Development libraries

- [Retrofit](http://square.github.io/retrofit/) Used to consume the Picasa api.
- [Retrofit Gson Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/gson) Converter to deserialize JSON responses from the picasa api.
- [RxJava2](https://github.com/ReactiveX/RxJava) just because I wanted.
- [Okhttp Logging Interceptor](https://github.com/ReactiveX/RxJava) Used for logging http requests/responses for the Picasa api.

### Testing libraries

- [Spek](http://spekframework.org/) Used to write some integration tests as specs.