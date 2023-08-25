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

package one.empty3.apps.tree;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
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

        if(toString!=null && !toString.isEmpty()) {
            listVariablesDef = new LinkedHashMap<>();

            String text = toString;

            String [] splittedLines = text.split("\n");

            for (int i = 0; i < splittedLines.length; i++) {

                String line = splittedLines[i];

                String[] splitInstructionEquals = line.split("=");

                String value = null;
                String variable = null;
                if(splitInstructionEquals.length==1) {
                    variable = splitInstructionEquals[0].trim();
                    value = splitInstructionEquals[0].trim();
                }
                if (splitInstructionEquals.length == 2) {
                    variable = splitInstructionEquals[0].trim();
                    value = splitInstructionEquals[1].trim();
                }
                if(splitInstructionEquals.length>=1) {

                    if ((variable != null ? variable.length() : 0) >0 && Character.isLetter(variable.toCharArray()[0])) {
                        int j = 0;
                        while (j < variable.length() && (Character.isLetterOrDigit(variable.toCharArray()[j])
                                || variable.toCharArray()[j] == '_')) {
                            j++;
                        }
                        if (j == variable.length()) {
                            listVariablesDef.put(variable, value);
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
                if(key!=null && value!=null && result!=null) {
                    currentParamsValues.put(key, result);
                }
            } catch (AlgebraicFormulaSyntaxException | TreeNodeEvalException e) {
                e.printStackTrace();
            }

            System.out.printf(String.format(Locale.getDefault(), "Result of line : (%s) <<< %f", key, result));

        }
    }
}
