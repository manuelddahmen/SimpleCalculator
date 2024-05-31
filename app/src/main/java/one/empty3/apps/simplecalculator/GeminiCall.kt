/*
 * Copyright (c) 2024.
 *
 *
 *  Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package one.empty3.apps.simplecalculator

import com.google.firebase.Firebase
import com.google.firebase.vertexai.type.GenerateContentResponse
import com.google.firebase.vertexai.type.GenerationConfig
import com.google.firebase.vertexai.vertexAI
import java.util.concurrent.Flow

class GeminiCall {
    fun call(generativeModel: com.google.firebase.vertexai.GenerativeModel): kotlinx.coroutines.flow.Flow<GenerateContentResponse> {
        // Initialize the Vertex AI service and the generative model
// Specify a model that supports your use case
// Gemini 1.5 Pro is versatile and can accept both text-only and multimodal prompt inputs

// Provide a prompt that includes only text
        val prompt = "Write a story about a magic backpack."

// To stream generated text output, call generateContentStream and pass in the prompt
        var response = ""
        val resp:kotlinx.coroutines.flow.Flow<GenerateContentResponse>  = generativeModel.generateContentStream(prompt)
        return resp
    }
}