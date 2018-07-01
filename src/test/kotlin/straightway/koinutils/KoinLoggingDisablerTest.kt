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
package straightway.koinutils

import com.nhaarman.mockito_kotlin.mock
import org.junit.jupiter.api.Test
import org.koin.Koin
import org.koin.log.Logger
import straightway.testing.flow.Same
import straightway.testing.flow.as_
import straightway.testing.flow.expect
import straightway.testing.flow.is_

class KoinLoggingDisablerTest {

    @Test
    fun `logging is disabled until close is called`() {
        val originalLogger = Koin.logger
        val loggerBefore = mock<Logger>()
        Koin.logger = loggerBefore
        KoinLoggingDisabler().use {
            expect(Koin.logger is_ Same as_ IgnoreLogger)
        }

        expect(Koin.logger is_ Same as_ loggerBefore)
        Koin.logger = originalLogger
    }
}