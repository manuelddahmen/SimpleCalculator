/*
 * Copyright (c) 2023.
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

package one.empty3.apps.tree.altree;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ListInstructions {
    LinkedHashMap<String, String> listVariablesDef = new LinkedHashMap<>();

    public ListInstructions() {

    }

    public void setListVariablesDef(LinkedHashMap<String, String> listVariablesDef) {
        this.listVariablesDef = listVariablesDef;
    }

    public LinkedHashMap<String, String> getListVariablesDef() {
        return listVariablesDef;
    }

    public void addInstructions(@NotNull String toString) {

        if(toString!=null && toString.isEmpty()) {
            listVariablesDef = new LinkedHashMap<>();

            String text = toString;

            String [] split = text.split("\n");

            for (int i = 0; i < split.length; i++) {

                String line = split[i];

                String[] split1 = line.split("=");

                String value = null;
                String variable = null;
                if(split1.length==1) {
                    variable = "";
                    value = split1[0];
                }
                if (split1.length == 2) {
                    variable = split[0].trim();

                    value = split[1].trim();
                }
                if(split1.length>=1) {
                    String formula = variable;

                    if (Character.isLetter(formula.toCharArray()[0])) {
                        int j = 1;
                        while (j < formula.length() && (Character.isLetterOrDigit(formula.toCharArray()[j])
                                || formula.toCharArray()[j] == '_')) {
                            j++;
                        }
                        if (j == formula.length()) {
                            listVariablesDef.put(formula, value);
                        }
                    }
                }
            }
        }
    }
    public void runInstructions() {
        HashMap<String, Double> currentParamsValues = new HashMap<>();
        for(Map.Entry<String, String> entry:listVariablesDef.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            Double result = null;
            try {
                if(value!=null) {
                    AlgebricTree tree = new AlgebricTree(value);
                    tree.construct();
                    tree.setParametersValues(currentParamsValues);

                    result = tree.eval();
                }
                if(key!=null && value!=null) {
                    currentParamsValues.put(key, result);
                }
            } catch (AlgebraicFormulaSyntaxException | TreeNodeEvalException e) {
                e.printStackTrace();
            }
        }
    }
}
