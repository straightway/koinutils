# koinutils
Utlitities for the Koin dependency injection framework

Allows building local contexts and instantiating classes using these local contexts. These classes may access Koin beans, factories and properies if they implement the `KoinModuleComponent` interface (preferably by the instance returned by `KoinModuleComponent()`).

Example:

    class Component : KoinModuleComponent by KoinModuleComponent() {
      private val otherComponent by inject<OtherComponent>()
    }

It is also possible to have the `KoinModuleContext` as property and use it to inject other components:

    class Component {
      private val koin = KoinModuleComponent8)
      private val otherComponent by koin.inject<OtherComponent>()
    }

This looks almost list an ordinary Koin component implemented using plain Koin. But since this class uses `KoinModuleComponent`, we can instantiate it as follows:

    var component = withContext {
      bean { OtherComponent() }
    } make {
      Component()
    }

(see the `WithModules` class for more sophisticated ways to instantiate components with contexts and modules).

This allows having different contexts and module sets for each instantiation, and no need to use the global context with Koin's `startKoin` method. This makes unit testing much easier, but also can support situations, where you want to instantiate classes with different contexts. In addition, it solves multi threading issues that may occur with `startKoin` (if you call `startKoin` from multiple threads).

A component once instantiated with a context keeps this context. If it injects other components or gets them later on, it uses this stored context and is thus independent of other components with a different context.

The context is assigned in a thread safe way to the components, so using this feature simultaneously in multiple threads is no problem.