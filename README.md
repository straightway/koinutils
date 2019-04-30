# koinutils
Utlitities for the Koin dependency injection framework in kotlin.

This library allows building local contexts and instantiating classes using these local contexts
(instead of a global context established with `startKoin`). These classes may access Koin beans,
factories and properies if they implement the `KoinModuleComponent` interface (preferably by the
instance returned by `KoinModuleComponent()`).

Example:

    class Component : KoinModuleComponent by KoinModuleComponent() {
      private val otherComponent by inject<OtherComponent>()
    }

It is also possible to have the `KoinModuleContext` as property and use it to inject other
components:

    class Component {
      private val koin = KoinModuleComponent()
      private val otherComponent by koin.inject<OtherComponent>()
    }

This looks almost like an ordinary Koin component implemented using plain Koin. But since this
class uses `KoinModuleComponent`, we can instantiate it as follows:

    var component = withContext {
      bean { OtherComponent() }
    } make {
      Component()
    }

(see the [`WithModules`](src/main/kotlin/straightway/koinutils/WithModules.kt) class
and the according [unit tests](src/test/kotlin/straightway/koinutils/WithModulesTest.kt)
for more sophisticated ways to instantiate components with contexts and modules).

This allows having different contexts and module sets for each instantiation, and no need to use
the global context with Koin's `startKoin` method. This makes unit testing much easier, but also
can support situations, where you want to instantiate classes with different contexts. In addition,
it solves multi threading issues that may occur with `startKoin` (if you call `startKoin` from
multiple threads).

A component once instantiated with a context keeps this context. If it injects other components or
gets them later on, it uses this stored context and is thus independent of other components with a
different context.

The context is assigned in a thread safe way to the components, so using this feature
simultaneously in multiple threads is supported.

## Status

This software is in pre-release state. Every aspect of it may change without announcement or
notification or downward compatibility. As soon as version 1.0 is reached, all subsequent changes
for sub versions will be downward compatible. Breaking changes will then only occur with a new
major version with according deprecation marking.

## Include in gradle builds

To include this library in a gradle build, add

    repositories {
        ...
        maven { url "https://straightway.github.io/repo" }
    }

Then you can simply configure in your dependencies:

    dependencies {
        compile "straightway:koinutils:<version>"
    }
