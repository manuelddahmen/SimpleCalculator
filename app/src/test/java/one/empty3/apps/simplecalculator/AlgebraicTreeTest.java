/*__

 Empty3
 Copyright (C) 2010-2019  Manuel DAHMEN

 This program is free software; you can redistribute it and/or
 modify it under the terms of the GNU General Public License
 as published by the Free Software Foundation; either version 2
 of the License, or (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package one.empty3.apps.simplecalculator;

import one.empty3.apps.tree.altree.AlgebraicFormulaSyntaxException;
import one.empty3.apps.tree.altree.AlgebricTree;
import one.empty3.apps.tree.altree.TreeNodeEvalException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/*__
 * Created by Manuel Dahmen on 15-12-16.
 */
public class AlgebraicTreeTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void add() throws Exception {

    }

    @Test
    public void addFactors() throws Exception {

    }

    @Test
    public void addTerms() throws Exception {

    }

    @Test
    public void addExponent() throws Exception {

    }

    @Test
    public void addFunction() throws Exception {

    }

    private boolean testResultVariable(String expr, double expectedResult, Map<String, Double> map, boolean echo) {
        AlgebricTree algebricTree = null;
        try {
            System.out.println("Expression string : " + expr);
            algebricTree = new AlgebricTree(expr);
            algebricTree.setParametersValues(map);
            algebricTree.construct();
            if (echo)
                System.out.println(algebricTree);
            try {
                Object result;
                result = algebricTree.eval();
                if (echo)
                    System.out.println("Result : " + result);
                if (echo)
                    System.out.println("Expected : " + expectedResult);
                assertTrue((double) result == expectedResult);
                return true;
            } catch (TreeNodeEvalException e) {
                e.printStackTrace();
                assertFalse(true);
            }
        } catch (AlgebraicFormulaSyntaxException e) {
            e.printStackTrace();
            assertFalse(true);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            assertFalse(true);
        }
        return false;
    }

    protected boolean testResult(String expr, double expectedResult, boolean echo) {
        AlgebricTree algebricTree = null;
        try {
            System.out.println("Expression string : " + expr);
            algebricTree = new AlgebricTree(expr);
            algebricTree.construct();
            if (echo)
                System.out.println(algebricTree);
            try {
                Object result;
                result = algebricTree.eval();
                if (echo)
                    System.out.println("Result : " + result);
                if (echo)
                    System.out.println("Expected : " + expectedResult);
                assertEquals((double) result, expectedResult, 0.0);
                return true;
            } catch (TreeNodeEvalException e) {
                e.printStackTrace();
                fail();
            }
        } catch (AlgebraicFormulaSyntaxException | NullPointerException e) {
            e.printStackTrace();
            fail();
        }
        return false;
    }

    @Test
    public void testSimpleEquation1() {
        testResult("1", 1.0, false);
    }

    @Test
    public void testSimpleEquationAdd() {
        testResult("1+1", 2.0, false);
    }

    @Test
    public void testSimpleEquationAddSubMult() {
        testResult("2*3+1*6-4", 2.0 * 3 + 1. * 6 - 4, false);
    }

    @Test
    public void testSimpleEquationAddSubMult2() {
        testResult("2*3-1*6-4", 2.0 * 3 - 1. * 6 - 4, false);
    }

    @Test
    public void testSimpleEquationAddMult() {
        testResult("2*3+1*6+4", 2 * 3 + 1 * 6 + 4, false);
    }

    @Test
    public void testSimpleEquationMult() {
        testResult("2*3", 6.0, false);
    }

    @Test
    public void testSimpleEquationAddAdd() {
        testResult("1+2+3+4+5+6", 1.0 + 2 + 3 + 4 + 5 + 6, false);
    }

    @Test
    public void testSimpleEquationAddSub() {
        testResult("1-2+3-4+5-6", 1.0 - 2 + 3 - 4 + 5 - 6, false);
    }

    @Test
    public void testSimpleEquationBracedAddAdd() {
        testResult("1+2+3-(4*2/1.5+5)*22+6", 1 + 2 + 3 - (4 * 2 / 1.5 + 5) * 22 + 6, false);
    }

    @Test
    public void testSimpleEquationAddSub2() {

        testResult("4*2/5", 4 * 2.0 / 5, false);
    }

    @Test
    public void testSimpleAddSubMulDiv() {

        testResult("4*2/5+8*9/10-4-4*5/9-2*3+1.2", 4*2/5.+8*9/10.-4-4*5/9.-2*3+1.2, false);
    }
    @Test
    public void testZeroPlusZero() {

        testResult("0+0", 0, false);
    }
    /*@Test
    public void testMultSign() {

        testResult("-10*-10", 100, false);
    }*/

    @Test
    public void testVariable() {

        HashMap<String, Double> vars = new HashMap<>();
        vars.put("u", 4.0);
        vars.put("v", 13.0);
        testResultVariable("u+v", 4.0 + 13.0, vars, true);
    }

    @Test
    public void testVariableZeroPlusZero() {

        HashMap<String, Double> vars = new HashMap<>();
        vars.put("R", 0.0);
        vars.put("u", 0.0);
        testResultVariable("R+u", 0, vars, false);
    }

    @Test
    public void testVariableCircle() {

        HashMap<String, Double> vars = new HashMap<>();
        vars.put("coordArr", 4.0);
        vars.put("y", 13.0);
        vars.put("z", 13.0);
        vars.put("R", 20.0);

        testResultVariable("coordArr*coordArr+y*y+z*z-R*R", 4.0 * 4 + 13.0 * 13 + 13.0 * 13.0 - 20.0 * 20.0, vars, false);
    }

    @Test
    public void testSimpleEquationBracedMultDiv() {
        testResult("1*2*3/4*5*4", 1.0 * 2.0 * 3.0 / 4.0 * 5.0 * 4.0, false);
    }
    @Test
    public void testComplexFunMultiple1() {
        testResult("sin(1)*sin(2)*sin(2)",//2*exp(3/4)+0.5-5*4*cos(2)",
                Math.sin(1) * Math.sin(2) * Math.sin(2), false);
    }
    @Test
    public void testComplexFunFunMultiple2() {
        testResult("sin(1)*sin(2*cos(0.2)*sin(2))+2*exp(3/4)+0.5-5*4*cos(2)",
                Math.sin(1)*Math.sin(2*Math.cos(0.2)*Math.sin(2))+
                        +2*Math.exp(3./4)+0.5-5*4*Math.cos(2), false);

    }
    @Test
    public void testComplexFunFunMultiple3() {
        testResult("sin(1)*sin(2*2)+2*exp(3/4)+0.5-5*4*cos(2)",
                Math.sin(1)*Math.sin(2*2)+2*Math.exp(3/4.)+0.5-5*4*Math.cos(2), false);
    }

    @Test
    public void testSimpleFunction() {
        testResult("sin(3.14)*4", Math.sin(3.14) * 4, false);
    }

    @Test
    public void testSimpleFunction1() {
        double u = 10;
        HashMap<String, Double> vars = new HashMap<>();
        vars.put("u", u);
        testResultVariable("10*cos(10*u)", 10 * Math.cos(10 * u), vars, true);
    }

    @Test
    public void testSimpleFunction3() {
        double u = 10;
        HashMap<String, Double> vars = new HashMap<>();
        vars.put("u", u);
        testResultVariable("cos(10*u)+u", Math.cos(10 * u) + u, vars, true);
    }

    @Test
    public void testSimpleFunction2() {
        double u = 10;
        HashMap<String, Double> vars = new HashMap<>();
        vars.put("u", u);


        testResultVariable("cos(5*u)*10", Math.cos(5 * u) * 10, vars, true);
    }

    @Test
    public void testSimple() {
        assertTrue(testResult("1", 1.0, false));
    }

    @Test
    public void testSimple3() {
        assertTrue(testResult("1+1", 2.0, false));
    }
@Test
    public void testSimple4() {
        assertTrue(testResult("1*8*-8", 1*8.*-8, false));
    }

    @Test
    public void testSimple5() {
        assertTrue(testResult("6-6*-12", 6-6*-12.0 , false));
    }   
 @Test
    public void testSimple6() {
        assertTrue(testResult("5/-5*3.0",-3.0 , false));
    }
    @Test
    public void testSimple7() {
        assertTrue(testResult("1-1/3*4/5*2",1-1/3.*4/5.*2 , false));
    }
    @Test
    public void testSimple8() {
        assertTrue(testResult("1-2-3-4-5-6",1.-2-3-4-5-6 , false));
    }
    @Test
    public void testSimple9() {
        assertTrue(testResult("1/2/3/4/5/6",1./2/3/4/5/6 , false));
    }
    @Test
    public void testSimple10() {
        assertTrue(testResult("-1",-1 , false));
    }
    @Test
    public void testSimpleParentheses() {
        assertTrue(testResult("(2+3)*(4+5)", (2 + 3) * (4 + 5), true));
    }

    @Test
    public void testSimpleParentheses3() {
        assertTrue(testResult("(2+3)*(4+5)*(6+7)", (2 + 3) * (4 + 5)*(6+7), true));
    }
    @Test
    public void testSimple2() {
        assertTrue(testResult("1.5", 1.5, false));
    }
}
