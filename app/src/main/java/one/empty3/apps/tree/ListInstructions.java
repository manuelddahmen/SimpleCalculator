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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ListInstructions {
    public class Instruction {
        private int id;
        private String leftHand;
        private String expression;

        public Instruction(int id, String leftHand, String expression) {
            this.id = id;
            this.leftHand = leftHand;
            this.expression = expression;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLeftHand() {
            return leftHand;
        }

        public void setLeftHand(String leftHand) {
            this.leftHand = leftHand;
        }

        public String getExpression() {
            return expression;
        }

        public void setExpression(String expression) {
            this.expression = expression;
        }
    }
    private ArrayList<Instruction> assignations;

    public ListInstructions() {

    }

    public ArrayList<Instruction> getAssignations() {
        return assignations;
    }

    public void setAssignations(ArrayList<Instruction> assignations) {
        this.assignations = assignations;
    }

    public void addInstructions(@NotNull String toString) {

        if(toString!=null && !toString.isEmpty()) {
            assignations = new ArrayList<>();

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
                            assignations.add(new Instruction(i, variable, value));
                        }
                    }
                }
            }
        }
    }
    public String[] runInstructions() {
        String [] errors = new String[assignations.size()];
        Instruction[] instructions = new Instruction[assignations.size()];

        assignations.toArray(instructions);

        HashMap<String, Double> currentParamsValues = new HashMap<>();
        int i=0;
        for(Instruction instruction : instructions) {
            String key = (String) instruction.getLeftHand();
            String value = (String) instruction.getExpression();

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