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

package one.empty3.library1.tree;

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

import one.empty3.library.StructureMatrix;

public class ListInstructions {
    HashMap<String, Double> currentParamsValues = new HashMap<>();
    HashMap<String, String> currentParamsValuesVec = new HashMap<>();
    HashMap<String, StructureMatrix<Double>> currentParamsValuesVecComputed = new HashMap<>();

    public class Instruction {
        private int id;
        private String ins;

        public Instruction(int id, String ins) {
            this.id = id;
            this.ins = ins;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIns() {
            return ins;
        }

        public void setIns(String ins) {
            this.ins = ins;
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

        if (toString != null && !toString.isEmpty()) {
            assignations = new ArrayList<>();

            String text = toString;

            String[] splitLines = text.split("\n");

            for (int i = 0; i < splitLines.length; i++) {

                String line = splitLines[i];

                String[] splitInstructionEquals = new String[1];//line.split("=");

                splitInstructionEquals[0] = line;

                String value = null;

                if (splitInstructionEquals.length == 1) {
                    value = splitInstructionEquals[0].trim();
                }
                if (splitInstructionEquals.length == 2) {
                    value = splitInstructionEquals[1].trim();
                }
                boolean assigned = false;
                if (splitInstructionEquals.length >= 1) {

                }
                if (!assigned) {
                    if (!value.startsWith("#")) {
                        assignations.add(new Instruction(i, value));
                    }
                }
            }


        }
    }

    public String[] runInstructions() {
        String[] errors = new String[assignations.size()];
        Instruction[] instructions = new Instruction[assignations.size()];

        assignations.toArray(instructions);

        currentParamsValues = new HashMap<>();
        currentParamsValuesVec = new HashMap<>();
        currentParamsValuesVecComputed = new HashMap<>();
        int i = 0;
        for (Instruction instruction : instructions) {
            String ins = instruction.ins;
            StructureMatrix<Double> resultVec = null;
            Double resultDouble = null;
            try {
                if (ins != null) {
                    AlgebricTree tree = new AlgebricTree(ins);
                    tree.setParametersValues(currentParamsValues);
                    tree.setParametersValuesVec(currentParamsValuesVec);
                    tree.setParametersValuesVecComputed(currentParamsValuesVecComputed);

                    tree.construct();

                    resultVec = tree.eval();

                    if (resultVec != null) {
                        if (resultVec.getDim() == 1) {
                            //currentParamsValuesVecComputed.put(key, resultVec);
                        } else if (resultVec.getDim() == 0) {
                            //currentParamsValuesVecComputed.put(key, resultVec);
                        }
                    } else {
                        throw new AlgebraicFormulaSyntaxException("Result was null");
                    }
                    System.err.println("AlgebraicTree result : " + tree);
                }
            } catch (AlgebraicFormulaSyntaxException | TreeNodeEvalException e) {
                e.printStackTrace();
            } catch (NullPointerException ignored) {
                ignored.printStackTrace();
            }
            if (resultVec != null) {
                errors[i] = String.format(Locale.getDefault(), "# Result of line : (%d) <<< %s ", i, resultVec.toStringLine());
            } else {

            }
            i++;
        }

        return errors;
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
}
