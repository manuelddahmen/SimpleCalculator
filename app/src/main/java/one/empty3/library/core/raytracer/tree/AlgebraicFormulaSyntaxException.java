/*
 * Copyright (c) 2023. Manuel Daniel Dahmen
 *
 *
 *    Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package one.empty3.library.core.raytracer.tree;

/*__
 * Created by Manuel Dahmen on 15-12-16.
 */
public class AlgebraicFormulaSyntaxException extends Throwable {
    public AlgebraicFormulaSyntaxException(String msg) {
        super(msg);
    }

    public AlgebraicFormulaSyntaxException() {
        super();
    }

    public AlgebraicFormulaSyntaxException(Tree algebraicTree) {
        this();
        System.err.println(algebraicTree);
    }

    public AlgebraicFormulaSyntaxException(String s, AlgebricTree algebricTree) {
        this(s);
        System.err.println(algebricTree);
    }
}
