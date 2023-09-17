/*
 * Copyright (c) 2022-2023. Manuel Daniel Dahmen
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

package one.empty3.library.lang;

import java.io.File;
import java.util.List;

public class Runner {
    public static void main(String [] args) {
        Classes classes = new Classes();
        File zip = new File(args[0]);
        ParseCode parseCode = new ParseCode();
        List<Token> tokens = parseCode.parseFile(zip);
        classes.add(new Node(tokens));
        run(classes);
    }

    private static void run(Classes classes) {
        Node node = searchForMainMethod(classes);
        if(node.canExec()) {
            node.run();
        }
    }

    private static Node searchForMainMethod(Classes classes) {
        return new EntryPoint(null);
    }
}
