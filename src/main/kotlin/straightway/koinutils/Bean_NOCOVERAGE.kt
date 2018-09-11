/*
 * Copyright 2016 github.com/straightway
 *
 *  Licensed under the Apache License, Version 2.0 (the &quot;License&quot;);
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an &quot;AS IS&quot; BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
@file:Suppress("MatchingDeclarationName")
package straightway.koinutils

import org.koin.core.parameter.Parameters
import org.koin.dsl.context.emptyParameters

/**
 * Access to beans, either by injection or by directly getting them.
 */
object Bean {

    inline fun <reified T> KoinModuleComponent.inject(name: String = "") = kotlin.lazy {
        get<T>(name, emptyParameters())
    }

    inline fun <reified T> KoinModuleComponent.inject(
            name: String,
            noinline parameters: Parameters
    ) = kotlin.lazy { get<T>(name, parameters) }

    inline fun <reified T> KoinModuleComponent.inject(
            noinline parameters: Parameters
    ) = kotlin.lazy { get<T>("", parameters) }

    inline fun <reified T> KoinModuleComponent.get(name: String = "") =
            withOwnContext { get<T>(name, emptyParameters()) }

    inline fun <reified T> KoinModuleComponent.get(
            name: String,
            noinline parameters: Parameters
    ) = withOwnContext { get<T>(name, parameters) }

    inline fun <reified T> KoinModuleComponent.get(noinline parameters: Parameters) =
            withOwnContext { get<T>("", parameters) }

    inline fun <reified T> get(name: String = "") =
            KoinModuleComponent.currentContext!!.get<T>(name)

    inline fun <reified T> get(
            name: String,
            noinline parameters: Parameters) =
            KoinModuleComponent.currentContext!!.get<T>(name, parameters)

    inline fun <reified T> get(noinline parameters: Parameters) =
            KoinModuleComponent.currentContext!!.get<T>("", parameters)

    /**
     * Get a value from the koin context via a KoinModuleComponent at
     * initialization time, e.g. when implementing interfaces using the by
     * keyword using another component retrieved from koin.
     */
    inline fun <reified T> init(getter: KoinModuleComponent.() -> T) =
            KoinModuleComponent().getter()
}