/*
 *
 *  * Copyright 2021 Spikey Sanju
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     https://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */

package dev.spikeysanju.einsen.utils.viewstate

import dev.spikeysanju.einsen.model.task.Task

sealed class ViewState {
    // Represents different states for the All Task screen
    object Empty : ViewState()
    object Loading : ViewState()
    data class Success(val task: List<Task>) : ViewState()
    data class Error(val exception: Throwable) : ViewState()
}
