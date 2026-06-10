/*
 *
 *  * Copyright (c) 2024. Manuel Daniel Dahmen
 *  *
 *  *
 *  *    Copyright 2024 Manuel Daniel Dahmen
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 *
 */

package one.empty3.library1.tree;

import one.empty3.library.StructureMatrix;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListInstructions {
    private HashMap<String, Double> currentParamsValues = new HashMap<>();
    private HashMap<String, String> currentParamsValuesVec = new HashMap<>();
    private HashMap<String, StructureMatrix<Double>> currentParamsValuesVecComputed = new HashMap<>();

    public String evaluate(String s) {
        return "";
    }

    public static class Instruction {
        private int id;
        private String leftHand;
        private String expression;
        StringAnalyzerJava1.TokenExpression2 tokenExpression2;
        private String originalString;

        public Instruction(int id, String leftHand, String expression, @NotNull String originalString) {
            this.id = id;
            this.leftHand = leftHand;
            setExpression(expression);
            setOriginalString(originalString);
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

            return tokenExpression2.toString();
        }

        public String getExpressionAlgebraicTree() {

            return expression;
        }

        public void setExpression(String expression) {
            if (expression != null) {
                this.expression = expression;
                StringAnalyzerJava1 stringAnalyzerJava1 = new StringAnalyzerJava1();
                tokenExpression2 = stringAnalyzerJava1.new TokenExpression2();
                tokenExpression2.parse(expression, 0);
            }
        }

        public StringAnalyzerJava1.TokenExpression2 getExpressionTokenString() {
            return tokenExpression2;
        }

        public void setTokenExpression2(StringAnalyzerJava1.TokenExpression2 expression) {
            //setExpression(expression.parse(expression., 0));
            this.tokenExpression2 = expression;
        }

        public String getOriginalString() {
            return originalString;
        }

        public void setOriginalString(String originalString) {

            this.originalString = originalString != null ? originalString.trim() : "";
        }

        @Override
        public String toString() {
            if (tokenExpression2 != null && tokenExpression2.isSuccessful()) {
                expression = tokenExpression2.toString();
                if (expression.startsWith("."))
                    expression = expression.substring(1);
            } else {
                if (expression.startsWith("."))
                    expression = expression.substring(1);
            }
            return expression;
        }

        public String toStringAlgebraicTree() {
            return expression;
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
        if (assignations == null)
            assignations = new ArrayList<>();


        if (toString != null && !toString.isEmpty()) {
            assignations = new ArrayList<>();

            String text = toString;

            String[] splitLines = text.split("\n");

            for (int i = 0; i < splitLines.length; i++) {

                final String line = splitLines[i];

                String[] splitInstructionEquals = line.split("=");

                String value = null;
                String variable = null;
                if (splitInstructionEquals.length == 1) {
                    variable = splitInstructionEquals[0].trim();
                    value = splitInstructionEquals[0].trim();
                }
                if (splitInstructionEquals.length == 2) {
                    variable = splitInstructionEquals[0].trim();
                    value = splitInstructionEquals[1].trim();
                }
                boolean assigned = false;
                if (splitInstructionEquals.length >= 1) {

                    if ((variable != null ? variable.length() : 0) > 0 && Character.isLetter(variable.toCharArray()[0])) {
                        int j = 0;
                        while (j < variable.length() && (Character.isLetterOrDigit(variable.toCharArray()[j])
                                || variable.toCharArray()[j] == '_')) {
                            j++;
                        }
                        if (j == variable.length()) {
                            assignations.add(new Instruction(i, variable, value, line));
                            assigned = true;
                        } else {
                            assignations.add(new Instruction(i, null, value, line));
                            assigned = true;
                        }
                    } else {
                        assignations.add(new Instruction(i, null, value, line));
                        assigned = true;
                    }
                }
                if (!assigned) {
//                    if (splitInstructionEquals.length == 1) {
//                                                                if (!value.startsWith("#")) {
                    assignations.add(new Instruction(i, variable, value, line));
//                        }
//                    }
                }
            }
        }
    }

    public List<String> runInstructions() {
        List<String> returnedCode = new ArrayList<>();
        Instruction[] instructions = new Instruction[assignations.size()];

        assignations.toArray(instructions);


        if (currentParamsValues == null)
            currentParamsValues = new HashMap<>();
        if (currentParamsValuesVec == null)
            currentParamsValuesVec = new HashMap<>();
        if (currentParamsValuesVecComputed == null)
            currentParamsValuesVecComputed = new HashMap<>();
        int countInstructions = 0;
        for (Instruction instruction : instructions) {
            countInstructions++;
            String key = instruction.getLeftHand();
            String value = instruction.getExpressionAlgebraicTree();


            if (key != null)
                key = key.trim();
            if (value != null) {
                value = value.trim();


            }
            StructureMatrix<Double> resultVec = null;
            Double resultDouble = null;

            if ((key != null || value != null) && !instruction.originalString.startsWith("##")) {

                try {
                    /*/if (value.startsWith("#")) {
                        i++;
                        continue;
                    }*/
                    System.err.println("Value passed as formula: " + value);
                    AlgebraicTree tree = new AlgebraicTree(value);
                    tree.setParametersValues(currentParamsValues);
                    tree.setParametersValuesVec(currentParamsValuesVec);
                    tree.setParametersValuesVecComputed(currentParamsValuesVecComputed);
                    try {
                        tree.construct();
                    } catch (AlgebraicFormulaSyntaxException | RuntimeException ex) {
                        //String errors1 = String.format(
                        //        "\n##Error: can't execute");
                        //returnedCode.add(instruction.originalString + errors1);
                        returnedCode.add(instruction.originalString);
                        continue;
                    }
                    resultVec = tree.eval();

                    if (resultVec != null) {
                        if (key != null) {
                            Logger.getAnonymousLogger().log(Level.INFO, "key: " + key + " value: " + value + " computed: " + resultVec);
                            if (resultVec.getDim() == 1) {
                                currentParamsValuesVecComputed.put(key, resultVec);
                                currentParamsValuesVec.put(key, value);

                                String errors1 = String.format("\n##line : (%d)%s=%s ", countInstructions, value, resultVec.toStringLine());
                                returnedCode.add(key + "=" + value + errors1);


                            } else if (resultVec.getDim() == 0) {
                                currentParamsValuesVecComputed.put(key, resultVec);
                                currentParamsValuesVec.put(key, value);
                                currentParamsValues.put(key, resultVec.getElem());


                                String errors1 = String.format("\n##line : (%d)%s=%s ", countInstructions, value, resultVec.toStringLine());
                                returnedCode.add(instruction.originalString + errors1);

                            }
                        } else if (instruction.originalString.startsWith("##")) {
                        } else {
                            String errors1 = String.format("\n##line : (%d)%s=%s ", countInstructions, value, resultVec.toStringLine());
                            returnedCode.add(instruction.originalString + errors1);

                        }
                    } else if (instruction.originalString.startsWith("##")) {
                    } else if (!instruction.originalString.startsWith("##") &&
                            instruction.originalString.startsWith("#")) {
                        returnedCode.add(instruction.originalString);
                    }/* else if (getCurrentParamsValuesVecComputed().get(key) != null) {
                        String errors1 = String.format(
                                "\n##line : (%d)%s=%s ", countInstructions, value, resultVec.toStringLine());
                        returnedCode.add(instruction.originalString + errors1);
                    }*/ else {
                        returnedCode.add(instruction.originalString);
                    }
                } catch (AlgebraicFormulaSyntaxException | TreeNodeEvalException |
                         NullPointerException e) {
                    returnedCode.add(instruction.originalString);
                }
            } else if (instruction.originalString.startsWith("##")) {
            } else {
                returnedCode.add(instruction.originalString);
            }
        }

        return returnedCode;
    }

    public HashMap<String, Double> getCurrentParamsValues() {
        return currentParamsValues;
    }

    public HashMap<String, String> getCurrentParamsValuesVec() {
        return currentParamsValuesVec;
    }

    public HashMap<String, StructureMatrix<Double>> getCurrentParamsValuesVecComputed() {
        return currentParamsValuesVecComputed;
    }

    public void setCurrentParamsValues(HashMap<String, Double> currentVars) {
        this.currentParamsValues = currentVars;
    }

    public void setCurrentParamsValuesVec(HashMap<String, String> currentVecs) {
        this.currentParamsValuesVec = currentVecs;
    }

    public void setCurrentParamsValuesVecComputed(HashMap<String, StructureMatrix<Double>> currentVecsComputed) {
        this.currentParamsValuesVecComputed = currentVecsComputed;
    }
}
