
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

package one.empty3.apps.tree.altree;

import java.util.HashMap;
import java.util.Map;
import java.util.List;


/*__
 * Created by Manuel Dahmen on 15-12-16.
 */
public class AlgebricTree extends Tree {

    private String formula = "0.0";
    Map<String, Double> parametersValues = new HashMap<>();
    private Tree t;
    private TreeNode root;

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
        root = new TreeNode(formula);
        add(root, formula);
        return this;
    }

    public boolean add(TreeNode src, String subformula) throws AlgebraicFormulaSyntaxException {

        if (src == null || subformula == null || subformula.length() == 0)
            return false;

        if (
                addFormulaSeparator(src, subformula) ||
                        addTerms(src, subformula) ||
                        addFactors(src, subformula) ||
                        addPower(src, subformula) ||
                        addSingleSign(src, subformula) ||
                        addDouble(src, subformula) ||
                        addFunction(src, subformula) ||
                        addBracedExpression(src, subformula)||
                        addVariable(src, subformula)

        ) {
            /*Iterator<TreeNode> it = src.getChildren().iterator();
            while (it.hasNext()) {
                TreeNode children = it.next();
                if (!add(children, children.getExpressionString())) {
                    //throw new AlgebraicFormulaSyntaxException();
                }
            }*/
        } else
            throw new AlgebraicFormulaSyntaxException(this);
        return true;
    }

    private boolean addFormulaSeparator(TreeNode src, String subformula) {
        String[] s;
        s = subformula.split("=");
        if (s.length > 1) {
            EquationTreeNode tt = new EquationTreeNode(subformula);
            tt.getChildren().add(new EquationTreeNode(s[0]));
            tt.getChildren().add(new EquationTreeNode(s[1]));
        } else
            return false;
        return true;
    }

    private boolean addVariable(TreeNode src, String subformula)
            throws AlgebraicFormulaSyntaxException {
        if (Character.isLetter(subformula.charAt(0))) {
            int i = 1;
            while (i < subformula.length() && Character.isLetterOrDigit(subformula.charAt(i))) {
                i++;
            }

            VariableTreeNodeType variableTreeNodeType = new VariableTreeNodeType();
            variableTreeNodeType.setValues(new Object[]{subformula.substring(0, i), parametersValues});
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
            DoubleTreeNodeType doubleTreeNodeType = new DoubleTreeNodeType();
            doubleTreeNodeType.setValues(new Object[]{subformula, d});
            src.getChildren().add(new TreeNodeDouble(src, new Object[]{subformula, d}, doubleTreeNodeType));

            return true;
        } catch (NumberFormatException ex) {
            return src.getChildren().size() > 0;
        }
    }

    private boolean addSingleSign(TreeNode src, String subformula) {
        if (subformula.charAt(0) == '-') {
            src.getChildren().add(new TreeNode(src, new Object[]{subformula.substring(1)}, new SignTreeNodeType(-1.0)));
            return true;
        }
        return false;

    }

    public boolean addPower(TreeNode t, String values) throws AlgebraicFormulaSyntaxException {
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
            if (values.charAt(i) == '^' && /*9(i < values.length() - 1 || values.charAt(i + 1) != '*') &&*/ count == 0) {
                newFactor = '^';
                newFactorPos = i;
                isNewFactor = true;
                firstTermFound = true;
                newFactorSign = 1;
            } else if (i == values.length() - 1 && count == 0 && firstTermFound) {
                isNewFactor = true;
                newFactorPos = i + 1;
                /*if (values.charAt(oldFactorPos - 1) == '/') {
                    newFactorSign = -1;
                    newFactor = '/';//??
                } else if (values.charAt(oldFactorPos - 1) == '*') {
                    newFactorSign = 1;
                    newFactor = '*';//??
                } else throw new AlgebraicFormulaSyntaxException("Ni + ni -");
            */
            }
            if (values.charAt(i) == '(') {
                count++;
            } else if (values.charAt(i) == ')') {
                count--;
            }
            if (values.charAt(values.length() - 1) == '^' || values.charAt(values.length() - 1) == '^')
                return false;


            if (isNewFactor && count == 0) {
                String subsubstring = values.substring(oldFactorPos, newFactorPos);


                if (subsubstring.length() > 0) {
                    t2 = new TreeNode(t, new Object[]{subsubstring}, new PowerTreeNodeType(oldFactorSign));
                    t.getChildren().add(t2);
                    if (!add(t2, subsubstring)) {
                        return false;
                    } else
                        countTerms++;

                }


                isNewFactor = false;
                count = 0;
                newFactorPos = i + 1;
                oldFactorPos = i + 1;
                newFactor = 0;
                oldFactorSign = newFactorSign;
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
            if (i == values.length() - 1 && count == 0 && firstTermFound) {
                isNewFactor = true;
                newFactorPos = i + 1;
                /*if (values.charAt(oldFactorPos - 1) == '/') {
                    newFactorSign = -1;
                    newFactor = '/';//??
                } else if (values.charAt(oldFactorPos - 1) == '*') {
                    newFactorSign = 1;
                    newFactor = '*';//??
                } else throw new AlgebraicFormulaSyntaxException("Ni + ni -");
            */
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
                    if (subsubstring.charAt(0) == '(' && subsubstring.charAt(subsubstring.length() - 1) == ')') {
                        t2 = new TreeNode(t, new Object[]{subsubstring}, new FactorTreeNodeType(oldFactorSign));
                        t.getChildren().add(t2);
                        if (!add(t2, subsubstring.substring(1, subsubstring.length() - 1))) {
                            return false;
                        } else {
                            countTerms++;

                            TreeNode t3 = new TreeNode(t2,
                                    new Object[]{subsubstring.substring(1, subsubstring.length() - 1)},
                                    new IdentTreeNodeType());

                            t2.getChildren().add(t3);
                            if (!add(t3, subsubstring.substring(1, subsubstring.length() - 1)))
                                return false;
                        }
                    } else {
                        t2 = new TreeNode(t, new Object[]{subsubstring}, new FactorTreeNodeType(oldFactorSign));
                        t.getChildren().add(t2);
                        if (!add(t2, subsubstring)) {
                            return false;
                        } else
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
            if (values.charAt(i) == '+' /*&& (i < values.length() - 1 || values.charAt(i + 1) != '+')*/ && count == 0) {
                newFactor = '+';
                newFactorPos = i;
                isNewFactor = true;
                firstTermFound = true;
                newFactorSign = 1;
            } else if (values.charAt(i) == '-' && count == 0) {
                newFactor = '-';
                isNewFactor = true;
                newFactorPos = i;
                firstTermFound = true;
                newFactorSign = -1;
            }

            if (values.charAt(i) == '(') {
                count++;
            } else if (values.charAt(i) == ')') {
                count--;
            }
            if (i == values.length() - 1 && count == 0 && firstTermFound) {
                isNewFactor = true;
                newFactorPos = i + 1;
/*                if (values.charAt(oldFactorPos - 1) == '-') {
                    newFactorSign = -1;
                    newFactor = '-';
                } else if (values.charAt(oldFactorPos - 1) == '+') {
                    newFactorSign = 1;
                    newFactor = '+';
                }
*/               // else throw new AlgebraicFormulaSyntaxException("Ni + ni -");

            }
            /*
            if(i>0&&i==values.length()-1&&(values.charAt(i-1)=='+'||values.charAt(i-1)=='-')) {
                return false;
            }*/


            if (values.charAt(values.length() - 1) == '+' || values.charAt(values.length() - 1) == '-')
                return false;


            if (isNewFactor && count == 0) {
                countTerms++;
                isNewFactor = false;
                char op = newFactor;


                String subsubstring = values.substring(oldFactorPos, newFactorPos);


                if (subsubstring.length() > 0) {
                    if (subsubstring.charAt(0) == '(' && subsubstring.charAt(subsubstring.length() - 1) == ')') {
                        t2 = new TreeNode(t, new Object[]{subsubstring}, new TermTreeNodeType(oldFactorSign));
                        t.getChildren().add(t2);
                        if (!add(t2, subsubstring)) {
                            return false;
                        } else {
                            countTerms++;
//
                            TreeNode t3 = new TreeNode(t2,
                                    new Object[]{subsubstring.substring(1, subsubstring.length() - 1)},
                                    new IdentTreeNodeType());

                            t2.getChildren().add(t3);
                            if (!add(t3, subsubstring.substring(1, subsubstring.length() - 1)))
                                return false;
                        }
                    } else {
                        t2 = new TreeNode(t, new Object[]{subsubstring}, new TermTreeNodeType(oldFactorSign));
                        t.getChildren().add(t2);
                        if (!add(t2, subsubstring)) {
                            return false;
                        } else
                            countTerms++;
//
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


    public boolean addFunction(TreeNode t, String values) throws AlgebraicFormulaSyntaxException {
        try {
            int i = 1;
            int count = 0;
            int argumentStart = 0;
            int argumentEnd = 0;
            int functionNameStart = 0;
            int countLetters = 0;

            while (i < values.length()) {
                if (Character.isLetter(values.charAt(0)) && Character.isLetterOrDigit(values.charAt(i)) && count == 0) {
                    countLetters++;
                } else if (values.charAt(i) == '(' && i > 0) {
                    if (count == 0) {
                        argumentStart = i + 1;
                    }
                    count++;
                } else if (values.charAt(i) == ')' && i > 1 && count == 1) {
                    count--;
                    argumentEnd = i;
                } else if (i < 2)
                    return false;


                if (count == 0 && values.charAt(i) == ')') {


                    String fName = values.substring(functionNameStart, argumentStart - 1);
                    String fParamString = values.substring(argumentStart, argumentEnd);


                    TreeTreeNodeType mathFunctionTreeNodeType = new TreeTreeNodeType(
                            fParamString, parametersValues
                    );

                    TreeNode t2 = new TreeTreeNode(t, new Object[]{fName, parametersValues, fParamString},
                            mathFunctionTreeNodeType);
                    if(!add(t2, fParamString))
                        return false;

                    t.getChildren().add(t2);

                }


                i++;

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
                        fParamString, parametersValues
                );

                TreeNode t2 = new TreeTreeNode(t, new Object[]{fName, parametersValues},
                        mathFunctionTreeNodeType);
                t.getChildren().add(t2);
                if(!add(t2, fParamString))
                    return false;

            }


            i++;

        }

        return t.getChildren().size() > 0;
    }

    public boolean addBracedExpression(TreeNode t, String values) throws AlgebraicFormulaSyntaxException {
        TreeNode tBraced;
        int i = 1;
        int count = 1;
        if (values.charAt(0) == '(' && values.length() >= 2) {
            count++;
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
                    TreeNode t2 = new TreeTreeNode(t, new Object[]{subsubstring, parametersValues, ""}, mathFunctionTreeNodeType);
                    t.getChildren().add(t2);
                    if(!add(t2, subsubstring))
                        return false;
                }


                i++;

            }

        } else return false;

        return t.getChildren().size() > 0;
    }

    private void grammar() {
        t = new Tree();

    }


    public Double eval() throws TreeNodeEvalException, AlgebraicFormulaSyntaxException {
        //System.out.println(parametersValues.size());
        return root.eval();
    }

    public String toString() {
        String s = "Arbre algébrique\n" +
                "Racine: " + root.getClass() + root.toString();
        return s;
    }

    public void setParametersValues(Map<String, Double> parametersValues) {
        this.parametersValues = parametersValues;
    }

    public Map<String, Double> getParametersValues() {
        return parametersValues;
    }
}
