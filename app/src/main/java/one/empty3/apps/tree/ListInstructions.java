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

import androidx.core.util.ConsumerKt;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

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
    public String[] runInstructions() {
        String [] errors = new String[listVariablesDef.size()];
        Map.Entry<String, String>[] instructions;
        instructions = new Map.Entry[listVariablesDef.size()];

        listVariablesDef.entrySet().toArray(instructions);

        HashMap<String, Double> currentParamsValues = new HashMap<>();
        int i=0;
        for(Map.Entry instruction : instructions) {
            String key = (String) instruction.getKey();
            String value = (String) instruction.getValue();

            Double result = null;
            try {
                if(value!=null) {
                    AlgebricTree tree = new AlgebricTree(value);
                    tree.setParametersValues(currentParamsValues);

                    tree.construct();

                    result = tree.eval();
                }
                if(key!=null && value!=null && result!=null) {
                    currentParamsValues.put(key, result);
                }
            } catch (AlgebraicFormulaSyntaxException | TreeNodeEvalException e) {
                System.err.println("Was null 133131");
            } catch (NullPointerException ignored) {
                System.err.println("Was null 133531");
            }

            errors[i] = String.format(Locale.getDefault(), "-- Result of line : (%d) <<< %f ", i, result);
            i++;
        }

        return errors;
    }
}
