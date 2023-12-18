
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

/*
 *  This file is part of Empty3.
 *
 *     Empty3 is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Empty3 is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Empty3.  If not, see <https://www.gnu.org/licenses/>. 2
 */

/*
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>
 */

package one.empty3.library1.tree;

import android.media.audiofx.DynamicsProcessing;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import one.empty3.library.StructureMatrix;


/*__
 * Created by Manuel Dahmen on 15-12-16.
 */
public class AlgebricTree extends Tree {

    private String formula = "0.0";
    Map<String, Double> parametersValues = new HashMap<>();
    Map<String, String> parametersValuesVec = new HashMap<>();
    private HashMap<String, StructureMatrix<Double>> parametersValuesVecComputed = new HashMap<>();
    private TreeNode root;
    private int stackSize = 0;

    public AlgebricTree(String formula) throws AlgebraicFormulaSyntaxException {
        this.formula = formula;
    }

    public AlgebricTree(String formula, Map<String, Double> parametersValues) {
        this.formula = formula;
        this.parametersValues = parametersValues;
    }

    public void setParameter(String s, Double d) {
        this.parametersValues.put(s, d);
    }

    public AlgebricTree construct() throws AlgebraicFormulaSyntaxException {
        root = new TreeNode(this, formula);
        stackSize = 0; // Restine sommaire//
        add(root, formula);
        return this;
    }

    /***
     * Particularité
     * @param src
     */
    private void checkForSignTreeNode(TreeNode src) {
        //System.out.println("DEBUG TREE: current tree" +src);
        if (src.getChildren().size() >= 2 && src.getChildren().get(1).type.getClass()
                .equals(SignTreeNodeType.class)) {
            TreeNode sign = src.getChildren().remove(1);
            TreeNode son0 = src.getChildren().remove(0);
            double sign1 = ((SignTreeNodeType) sign.type).getSign();
            src.getChildren().add(new TreeNode(src, new Object[]{"" + sign1},
                    new SignTreeNodeType(sign1)));
            TreeNode son1 = src.getChildren().get(0);
            son1.getChildren().add(son0);
        }
    }

    public boolean add(TreeNode src, String subformula) throws AlgebraicFormulaSyntaxException {

        stackSize++;
        if (stackSize > 300) {
            //UNSECURE throw new AlgebraicFormulaSyntaxException("Recursive error (bad formula form)");
        }

        if (src == null || subformula == null || subformula.length() == 0)
            return false;

        int i = 1;
        boolean added = false;
        int last = 12;
        while (i <= last && !added) {
            boolean exception = false;
            src.getChildren().clear();
            try {
                int caseChoice = -1;
                int lastAdded = -1;
                subformula = addSpaces(subformula);
                switch (i) {

                    case 1:
                        added = addVector2(src, subformula);
                        if (added) caseChoice = 1;
                        break;
                    case 2:
                        added = addTerms(src, subformula);
                        if (added) caseChoice = 2;
                        break;
                    //case 3:
                    //    added = addSingleSign(src, subformula);
                    //    if (added) caseChoice = 3;
                    //    break;
                    case 4:
                        added = addFactors(src, subformula);
                        if (added) caseChoice = 4;
                        break;
                    case 5:
                        added = addPower(src, subformula);
                        if (added) caseChoice = 5;
                        break;
                    case 6:
                        added = addFormulaSeparator(src, subformula);
                        if (added) caseChoice = 6;
                        break;
                    case 8:
                        added = addDouble(src, subformula);
                        if (added) caseChoice = 8;
                        break;
                    case 9:
                        added = addFunction(src, subformula);
                        if (added) caseChoice = 9;
                        break;
                    case 10:
                        added = addBracedExpression(src, subformula);
                        if (added) caseChoice = 10;
                        break;
                    case 11:
                        added = addVariable(src, subformula);
                        if (added) caseChoice = 11;
                        break;
                    case 12: // Mettre - en 4??
                        added = addSingleSign(src, subformula);
                        if (added) caseChoice = 12;
                        break;
                    case 13:
                        added = addFunctionDefinition(src, subformula);
                        if (added) caseChoice = 13;
                        break;
                    default:
                        break;
                }
                if (added)
                    checkForSignTreeNode(src);

            } catch (AlgebraicFormulaSyntaxException ex) {
                exception = true;
            }
            if (added && !exception) {
                stackSize--;
                return true;
            }
            i++;


            //System.out.println("formula = " + subformula);
        }
        throw new AlgebraicFormulaSyntaxException("Cannot add to treeNode or root.", this);
    }

    private String addSpaces(String subformula) {
        while(subformula!=null && !subformula.isEmpty() &&
                    (subformula.charAt(0)=='\s' || subformula.charAt(0)=='\n' ||
                    subformula.charAt(0)=='\r' || subformula.charAt(0)=='\t'))
                subformula = subformula.substring(1);
        return subformula;
    }

    /***
     *
     * @param src
     * @param subformula
     * @return
     */
    private boolean addFormulaSeparator(TreeNode src, String subformula) {
        if(subformula==null || subformula.isEmpty())
            return false;

        String[] s;
        int i=0;
        int count=0;
        while(i<subformula.length()) {
            char currentChar = subformula.charAt(i);
            if(currentChar=='(')
                count++;
            if(currentChar==')')
                count--;
            if(currentChar=='=' && count==0) {
                s = new String[2];
                s[0] = subformula.substring(0, i);
                s[1] = subformula.substring(i+1);
                EquationTreeNode tt = new EquationTreeNode(src, new Object[]{
                        subformula, parametersValues, parametersValuesVec, parametersValuesVecComputed},
                        new EquationTreeNodeType(1.0));
                tt.getChildren().add( new TreeNode(src, new Object[]{
                        s[0], parametersValues, parametersValuesVec, parametersValuesVecComputed},
                        new IdentTreeNodeType()));
                tt.getChildren().add( new TreeNode(src, new Object[]{
                        s[1], parametersValues, parametersValuesVec, parametersValuesVecComputed},
                        new IdentTreeNodeType()));
                try {
                    if(add(tt.getChildren().get(0), s[0]) && add(tt.getChildren().get(1), s[1])) {
                        src.getChildren().add(tt);
                        return true;
                    }
                } catch (AlgebraicFormulaSyntaxException e) {
                    throw new RuntimeException(e);
                }
                return true;

            }
            i++;
        }
        return false;
    }

    private boolean addVariable(TreeNode src, String subformula)
            throws AlgebraicFormulaSyntaxException {
        if (Character.isLetter(subformula.charAt(0))) {
            int i = 1;
            while (i < subformula.length() && Character.isLetterOrDigit(subformula.charAt(i))) {
                i++;
            }

            VariableTreeNodeType variableTreeNodeType = new VariableTreeNodeType(this);
            variableTreeNodeType.setValues(new Object[]{subformula.substring(0, i), parametersValues, parametersValuesVec, parametersValuesVecComputed});
            src.getChildren().add(new TreeNodeVariable(src, new Object[]{subformula.substring(0, i), parametersValues}, variableTreeNodeType));

            if (subformula.length() > i)
                throw new AlgebraicFormulaSyntaxException("var tree node test failed. error in formula+ \n" +
                        subformula.substring(0, i) + " of " + subformula
                );


        }
        return src.getChildren().size() > 0;
    }


    private boolean addDouble(TreeNode src, String subformula) {
        try {
            Double d = Double.parseDouble(subformula);
            DoubleTreeNodeType doubleTreeNodeType = new DoubleTreeNodeType(this);
            doubleTreeNodeType.setValues(new Object[]{subformula, d});
            src.getChildren().add(new TreeNodeDouble(src, new Object[]{subformula, d}, doubleTreeNodeType));

            return true;
        } catch (NumberFormatException ex) {
            return src.getChildren().size() > 0;
        }
    }

    private boolean addSingleSign(TreeNode src, String subformula) throws AlgebraicFormulaSyntaxException {
        if (subformula.length() > 1 && subformula.charAt(0) == '-') {
            TreeNode treeNode = new TreeNode(src, new Object[]{subformula.substring(1)}, new SignTreeNodeType(-1.0));
            if (add(treeNode, subformula.substring(1))) {
                src.getChildren().add(treeNode);
                return true;
            }
        }
        return false;

    }

    public boolean addPower(TreeNode t, String values) throws AlgebraicFormulaSyntaxException {
        int countTerms = 0;

        TreeNode t2;
        int i = 0;
        boolean firstExpFound = false;
        boolean isNewExp = false;
        int count = 0;
        int newExpPos = 0;
        int oldExpPos = 0;
        char newExp = '^';
        double newExpSign = 1;
        double oldExpSign = 1;
        while (i < values.length()) {

            if (values.charAt(i) == '^' && /*9(i < values.length() - 1 || values.charAt(i + 1) != '*') &&*/ count == 0) {
                newExp = '^';
                newExpPos = i;
                isNewExp = true;
                firstExpFound = true;
                newExpSign = 1;
            } else if (values.charAt(i) == '/' && count == 0) {
                newExp = '/';
                isNewExp = true;
                newExpPos = i;
                firstExpFound = true;
                newExpSign = -1;
            }
            if (i == values.length() - 1 && firstExpFound) {
                isNewExp = true;
                newExpPos = i + 1;
            }
            if (values.charAt(i) == '(') {
                count++;
            } else if (values.charAt(i) == ')') {
                count--;
            }
            if (values.charAt(values.length() - 1) == '*' || values.charAt(values.length() - 1) == '/')
                return false;


            if (isNewExp && count == 0) {
                String subsubstring = values.substring(oldExpPos, newExpPos);


                if (subsubstring.length() > 0) {
                    t2 = new TreeNode(t, new Object[]{subsubstring}, new PowerTreeNodeType(oldExpSign));
                    if (subsubstring.charAt(0) == '-') {
                        subsubstring = subsubstring.substring(1);
                        SignTreeNodeType signTreeNodeType = new SignTreeNodeType(-1.0);
                        signTreeNodeType.instantiate(new Object[]{subsubstring});

                        t2 = new TreeNode(t2, new Object[]{subsubstring}, signTreeNodeType);
                    }
                    if (subsubstring.length() > 0 && !add(t2, subsubstring)) {
                        return false;
                    } else {
                        t.getChildren().add(t2);
                        countTerms++;
                    }
                }

//ab44md78
//gen44md78
                isNewExp = false;
                newExpPos = i + 1;
                oldExpPos = i + 1;
                oldExpSign = newExpSign;
            }

            i++;


        }
        return t.getChildren().size() > 0 && countTerms > 0;
    }

    public boolean addFactors(TreeNode t, String values) throws AlgebraicFormulaSyntaxException {
        int countTerms = 0;

        TreeNode t2;
        int i = 0;
        boolean firstTermFound = false;
        boolean isNewFactor = false;
        int count = 0;
        int newFactorPos = 0;
        int oldFactorPos = 0;
        char newFactor = '*';
        double newFactorSign = 1;
        double oldFactorSign = 1;
        while (i < values.length()) {

            if (values.charAt(i) == '*' && /*9(i < values.length() - 1 || values.charAt(i + 1) != '*') &&*/ count == 0) {
                newFactor = '*';
                newFactorPos = i;
                isNewFactor = true;
                firstTermFound = true;
                newFactorSign = 1;
            } else if (values.charAt(i) == '/' && count == 0) {
                newFactor = '/';
                isNewFactor = true;
                newFactorPos = i;
                firstTermFound = true;
                newFactorSign = -1;
            }
            if (i == values.length() - 1 && firstTermFound) {
                isNewFactor = true;
                newFactorPos = i + 1;
            }
            if (values.charAt(i) == '(') {
                count++;
            } else if (values.charAt(i) == ')') {
                count--;
            }
            if (values.charAt(values.length() - 1) == '*' || values.charAt(values.length() - 1) == '/')
                return false;


            if (isNewFactor && count == 0) {
                String subsubstring = values.substring(oldFactorPos, newFactorPos);


                if (subsubstring.length() > 0) {
                    t2 = new TreeNode(t, new Object[]{subsubstring}, new FactorTreeNodeType(oldFactorSign));
                    if (subsubstring.charAt(0) == '-') {
                        subsubstring = subsubstring.substring(1);
                        SignTreeNodeType signTreeNodeType = new SignTreeNodeType(-1.0);
                        signTreeNodeType.instantiate(new Object[]{subsubstring});

                        t2 = new TreeNode(t2, new Object[]{subsubstring}, signTreeNodeType);
                    }
                    if (!add(t2, subsubstring)) {
                        return false;
                    } else {
                        t.getChildren().add(t2);
                        countTerms++;
                    }
                }


                isNewFactor = false;
                newFactorPos = i + 1;
                oldFactorPos = i + 1;
                oldFactorSign = newFactorSign;
            }

            i++;
        }
        return t.getChildren().size() > 0 && countTerms > 0;
    }

    public boolean addTerms(TreeNode t, String values) throws AlgebraicFormulaSyntaxException {
        int countTerms = 0;

        TreeNode t2;
        int i = 0;
        boolean firstTermFound = false;
        boolean isNewFactor = false;
        int count = 0;
        int newFactorPos = 0;
        int oldFactorPos = 0;
        char newFactor = '+';
        double newFactorSign = 1;
        double oldFactorSign = 1;
        while (i < values.length()) {
            if (values.charAt(i) == '+' && count == 0 && i > 0/*&& (i < values.length() - 1 || values.charAt(i + 1) != '+')*/ && count == 0) {
                newFactor = '+';
                newFactorPos = i;
                isNewFactor = true;
                firstTermFound = true;
                newFactorSign = 1;
            } else if (values.charAt(i) == '-' && count == 0 && i > 0) {
                newFactor = '-';
                isNewFactor = true;
                newFactorPos = i;
                firstTermFound = true;
                newFactorSign = -1;
            }
            if ((values.charAt(i) == '-' || values.charAt(i) == '+') && i == 0) {
            }

            if (values.charAt(i) == '(') {
                count++;
            } else if (values.charAt(i) == ')') {
                count--;
            }
            if (i == values.length() - 1 && firstTermFound) {
                isNewFactor = true;
                newFactorPos = i + 1;
            }

            if (values.charAt(values.length() - 1) == '+' || values.charAt(values.length() - 1) == '-')
                return false;


            if (isNewFactor && count == 0) {
                countTerms++;
                isNewFactor = false;
                char op = newFactor;


                String subsubstring = values.substring(oldFactorPos, newFactorPos);


                if (subsubstring.length() > 0) {
                    t2 = new TreeNode(t, new Object[]{subsubstring}, new TermTreeNodeType(oldFactorSign));
                    if (!add(t2, subsubstring)) {
                        return false;
                    } else {
                        t.getChildren().add(t2);
                        countTerms++;
                    }
                } else
                    return false;


                isNewFactor = false;
                newFactorPos = i + 1;
                oldFactorPos = i + 1;
                newFactor = 0;
                oldFactorSign = newFactorSign;
            }

            i++;


        }

        return t.getChildren().size() > 0 && countTerms > 0;
    }


    public boolean addVector2(TreeNode t, String values) throws AlgebraicFormulaSyntaxException {
        int countComponents = 0;

        TreeNode t2;
        int i = 0;
        boolean firstTermFound = false;
        boolean isNewFactor = false;
        int count = 0;
        int newFactorPos = 0;
        int oldFactorPos = 0;
        char newFactor = ',';
        double newFactorSign = 1;
        double oldFactorSign = 1;
        while (i < values.length()) {
            if (values.charAt(i) == ',' && count == 0 && i > 0/*&& (i < values.length() - 1 || values.charAt(i + 1) != '+')*/ && count == 0) {
                newFactor = ',';
                newFactorPos = i;
                isNewFactor = true;
                firstTermFound = true;
                newFactorSign = 1;
            } else if (values.charAt(i) == ',' && count == 0 && i > 0) {
                newFactor = ',';
                isNewFactor = true;
                newFactorPos = i;
                firstTermFound = true;
                newFactorSign = -1;
            }
            if ((values.charAt(i) == ',') && i == 0) {
            }

            if (values.charAt(i) == '(') {
                count++;
            } else if (values.charAt(i) == ')') {
                count--;
            }
            if (i == values.length() - 1 && firstTermFound) {
                isNewFactor = true;
                newFactorPos = i + 1;
            }

            if (values.charAt(values.length() - 1) == ',')
                return false;


            if (isNewFactor && count == 0) {
                countComponents++;
                isNewFactor = false;
                char op = newFactor;


                String subsubstring = values.substring(oldFactorPos, newFactorPos);


                if (subsubstring.length() > 0) {
                    t2 = new VectorTreeNode(t, new Object[]{subsubstring}, new VectorTreeNodeType(oldFactorSign));
                    if (!add(t2, subsubstring)) {
                        return false;
                    } else {
                        t.getChildren().add(t2);
                        countComponents++;
                    }
                } else
                    return false;


                isNewFactor = false;
                newFactorPos = i + 1;
                oldFactorPos = i + 1;
                newFactor = 0;
                oldFactorSign = newFactorSign;
            }

            i++;


        }

        return t.getChildren().size() > 0 && countComponents > 0;
    }

    public boolean addFunction(TreeNode t, String values) throws AlgebraicFormulaSyntaxException {
        try {
            int i = 1;
            int count = 0;
            int argumentStart = 0;
            int argumentEnd = 0;
            int functionNameStart = 0;
            int countLetters = 0;
            boolean isName = true;
            while (i < values.length()) {
                if (isName && Character.isLetter(values.charAt(0)) && Character.isLetterOrDigit(values.charAt(i)) && count == 0) {
                    countLetters++;
                    isName = true;
                } else if (values.charAt(i) == '(' && i > 0) {
                    isName = false;
                    if (count == 0) {
                        argumentStart = i + 1;
                    }
                    count++;
                } else if (values.charAt(i) == ')' && i > 1) {
                    count--;
                    argumentEnd = i;
                } else if (i < 2)
                    return false;


                i++;

            }
            if (count == 0 && values.charAt(i - 1) == ')') {


                String fName = values.substring(functionNameStart, argumentStart - 1);
                String fParamString = values.substring(argumentStart, argumentEnd);


                TreeTreeNodeType mathFunctionTreeNodeType = new TreeTreeNodeType(
                        fParamString, parametersValues
                );

                TreeNode t2 = new TreeTreeNode(t, new Object[]{fParamString, parametersValues, fName},
                        mathFunctionTreeNodeType);
                if (!add(t2, fParamString))
                    return false;
                t.getChildren().add(t2);


            }
        } catch (Exception ex) {
            return false;
        }
        return t.getChildren().size() > 0;
    }

    /*
     * add method calls
     * examples
     * a = new Point(0.0, y/this.getResY());
     * b.x >= p.plus(p2.mult(3.0).add(p3)).getY();

     * ajouter {; , .}
     */

    public boolean addMethodCall(TreeNode t, String values) throws AlgebraicFormulaSyntaxException {
        int i = 1;
        boolean isNewFactor = false;
        int count = 0;
        int newFactorPos = 0;
        int oldFactorPos = 0;
        char newFactor = 0;
        int countLetters = 0;

        while (i < values.length()) {
            if (Character.isLetter(values.charAt(0)) && Character.isLetterOrDigit(values.charAt(i)) && count == 0) {
                countLetters++;
            } else if (values.charAt(i) == '(') {
                if (count == 0) {
                    newFactorPos = i + 1;
                }
                count++;
            } else if (values.charAt(i) == ')') {
                count--;
            } else if (i < 1)
                return false;


            if (count == 0 && values.charAt(i) == ')') {


                String fName = values.substring(oldFactorPos, newFactorPos - 1);
                String fParamString = values.substring(newFactorPos, i);


                TreeTreeNodeType mathFunctionTreeNodeType = new TreeTreeNodeType(
                        "", parametersValues
                );

                TreeNode t2 = new TreeTreeNode(t, new Object[]{fName, parametersValues, fParamString},
                        mathFunctionTreeNodeType);
                if (!add(t2, fParamString))
                    return false;
                t.getChildren().add(t2);

            }


            i++;

        }

        return t.getChildren().size() > 0;
    }
    public boolean addFunctionDefinition(TreeNode t, String values)
            throws AlgebraicFormulaSyntaxException {
        int i = 1;
        boolean isNewFactor = false;
        int count = 0;
        int newFactorPos = 0;
        int oldFactorPos = 0;
        char newFactor = 0;
        int countLetters = 0;
        boolean fNameAdded = false;
        int listInstructionDef = -1;
        while (i < values.length()) {
            if (Character.isLetter(values.charAt(0)) && Character.isLetterOrDigit(values.charAt(i)) && count == 0) {
                countLetters++;
            } else if (values.charAt(i) == '(') {
                if (count == 0) {
                    newFactorPos = i + 1;
                }
                count++;
            } else if (values.charAt(i) == ')') {
                count--;
            } else if(values.charAt(i) == '{') {
                if(count==0 && fNameAdded) {
                    listInstructionDef = i + 1;
                }
            }
            else if (i < 1)
                return false;

            TreeNode t2 = null;
            if (count == 0 && values.charAt(i) == ')') {


                String fName = values.substring(oldFactorPos, newFactorPos - 1);
                String fParamString = values.substring(newFactorPos, i);


                TreeTreeNodeType mathFunctionTreeNodeType = new TreeTreeNodeType(
                        "", parametersValues
                );

                t2 = new TreeTreeNode(t, new Object[]{fName, parametersValues, fParamString},
                        mathFunctionTreeNodeType);
                if (!add(t2, fParamString))
                    return false;

            } else if(values.charAt(i)=='{' && t2!=null && listInstructionDef>0) {
                listInstructionDef = i+1;
                if(addFunctionBody(t2, values.substring(listInstructionDef)))
                    t.getChildren().add(t2);
                else
                    return false;
            }


            i++;

        }
        return false;
    }

    public boolean addFunctionBody(TreeNode t2, String substring) {
        return false;
    }

    public boolean addBracedExpression(TreeNode src, String values) throws AlgebraicFormulaSyntaxException {
        TreeNode tBraced;
        int i = 0;
        int count = 0;
        while (i < values.length()) {
            if (values.charAt(i) == ')') {
                count--;
            } else if (values.charAt(i) == '(') {
                count++;
            } else if (i < 1)
                return false;

            if (i == values.length() - 1 && count == 0 && values.charAt(i) == ')') {
                String subsubstring = values.substring(1, values.length() - 1);
                TreeTreeNodeType mathFunctionTreeNodeType = new TreeTreeNodeType(
                        subsubstring, parametersValues
                );
                TreeNode t2 = new TreeNode(src, new Object[]{subsubstring, parametersValues, ""}, mathFunctionTreeNodeType);
                if (!add(t2, subsubstring))
                    return false;
                src.getChildren().add(t2);
            }


            i++;

        }

        return src.getChildren().size() > 0;
    }

    public StructureMatrix<Double> eval() throws TreeNodeEvalException, AlgebraicFormulaSyntaxException {

        return root.eval();
    }

    @NonNull
    public String toString() {
        String s = "Arbre algébrique\n";
        if (root != null) {
            s += "Racine: " + root.getClass() + root.toString();
        }
        return s;
    }

    public void setParametersValues(Map<String, Double> parametersValues) {
        this.parametersValues = parametersValues;
    }
    public void setParametersValuesVec(Map<String, String> parametersValuesVec) {
        this.parametersValuesVec = parametersValuesVec;
    }

    public Map<String, Double> getParametersValues() {
        return parametersValues;
    }
    public Map<String, String> getParametersValuesVec() {
        return parametersValuesVec;
    }
    public void setParametersValuesVecComputed(HashMap<String, StructureMatrix<Double>> parametersValuesVecComputed) {
        this.parametersValuesVecComputed = parametersValuesVecComputed;
    }
    public HashMap<String, StructureMatrix<Double>>  getParametersValuesVecComputed() {
        return parametersValuesVecComputed;
    }

    public String getFormula() {
        return formula;
    }
}
