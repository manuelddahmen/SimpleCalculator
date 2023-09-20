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
package one.empty3.apps.simplecalculator

import one.empty3.library.StructureMatrix
import one.empty3.library1.shader.Vec
import one.empty3.library1.tree.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

import kotlin.jvm.Throws;

/*__
 * Created by Manuel Dahmen on 15-12-16.
 * Updated by Manuel Dahmen on 10-11-23
 */
class ExampleUnitTest1() {
    @Before
    @Throws(Exception::class)
    fun setUp() {
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
    }

    @Test
    @Throws(Exception::class)
    fun add() {
    }

    @Test
    @Throws(Exception::class)
    fun addFactors() {
    }

    @Test
    @Throws(Exception::class)
    fun addTerms() {
    }

    @Test
    @Throws(Exception::class)
    fun addExponent() {
    }

    @Test
    @Throws(Exception::class)
    fun addFunction() {
    }

    private fun testResultVariable(
        expr: String,
        expectedResult: Double,
        map: Map<String, Double>,
        echo: Boolean
    ) {
        var algebricTree: AlgebricTree? = null
        try {
            println("Expression string : $expr")
            algebricTree = AlgebricTree(expr)
            algebricTree.parametersValues = map
            algebricTree.construct()
            //println(algebricTree.toString())
            if (echo) println(algebricTree)
            try {
                val result: Double = algebricTree.eval().getElem()
                if (echo) println("Result : $result")
                if (echo) println("Expected : $expectedResult")
                Assert.assertTrue(
                    result < expectedResult + DELTA(expectedResult)
                            && result > expectedResult - DELTA(expectedResult)
                )
            } catch (e: TreeNodeEvalException) {
                e.printStackTrace()
                Assert.assertFalse(true)
            }
        } catch (e: AlgebraicFormulaSyntaxException) {
            e.printStackTrace()
            Assert.assertFalse(true)
        } catch (ex: NullPointerException) {
            ex.printStackTrace()
            Assert.assertFalse(true)
        }
    }

    private fun DELTA(expectedResult: Double): Double {
        return Math.abs(Math.max(expectedResult / 10E5, 1E-5))
    }

    protected fun testResult(expr: String, expectedResult: Double, echo: Boolean): Boolean {
        var algebricTree: AlgebricTree? = null
        try {
            println("Expression string : $expr")
            algebricTree = AlgebricTree(expr)
            algebricTree.construct()
            if (echo) println(algebricTree)
            try {
                val result: Double
                result = algebricTree.eval().getElem()
                if (echo) println("Result : $result")
                if (echo) println("Expected : $expectedResult")
                Assert.assertTrue(
                    ((result < expectedResult + DELTA(expectedResult)
                            && result > expectedResult - DELTA(expectedResult)))
                )
                return true
            } catch (e: TreeNodeEvalException) {
                e.printStackTrace()
                Assert.fail()
            }
        } catch (e: AlgebraicFormulaSyntaxException) {
            e.printStackTrace()
            Assert.fail()
        } catch (e: NullPointerException) {
            e.printStackTrace()
            Assert.fail()
        }
        return false
    }

    protected fun testConstructOrEvalFails(
        expr: String,
        expectedResult: Double,
        echo: Boolean
    ): Boolean {
        var algebricTree: AlgebricTree? = null
        try {
            println("Expression string : $expr")
            algebricTree = AlgebricTree(expr)
            algebricTree.construct()
            if (echo) println(algebricTree)
            try {
                val result: Any
                result = algebricTree.eval()
                if (echo) println("Result : $result")
                if (echo) println("Expected : $expectedResult")
                Assert.fail()
                return false
            } catch (e: TreeNodeEvalException) {
                Assert.assertTrue(true)
                if (echo) e.printStackTrace()
            }
        } catch (e: AlgebraicFormulaSyntaxException) {
            Assert.assertTrue(true)
            if (echo) e.printStackTrace()
        } catch (e: NullPointerException) {
            Assert.assertTrue(true)
            if (echo) e.printStackTrace()
        }
        return false
    }

    @Test
    fun testSimpleEquation1() {
        testResult("1", 1.0, false)
    }
    @Test
    fun testSimpleEquation0() {
        testResult("0", 0.0, false)
    }

    @Test
    fun testSimpleEquationAdd() {
        testResult("1+1", 2.0, false)
    }

    @Test
    fun testSimpleEquationAddSubMult() {
        testResult("2*3+1*6-4", 2.0 * 3 + 1.0 * 6 - 4, false)
    }

    @Test
    fun testSimpleEquationAddSubMult2() {
        testResult("2*3-1*6-4", (2.0 * 3) - (1.0 * 6) - 4, false)
    }

    @Test
    fun testSimpleEquationAddMult() {
        testResult("2*3+1*6+4", ((2 * 3) + (1 * 6) + 4).toDouble(), false)
    }

    @Test
    fun testSimpleEquationMult() {
        testResult("2*3", 6.0, false)
    }

    @Test
    fun testSimpleEquationAddAdd() {
        testResult("1+2+3+4+5+6", 1.0 + 2 + 3 + 4 + 5 + 6, false)
    }

    @Test
    fun testSimpleEquationAddSub() {
        testResult("1-2+3-4+5-6", 1.0 - 2 + 3 - 4 + 5 - 6, false)
    }

    @Test
    fun testSimpleEquationBracedAddAdd() {
        testResult("1+2+3-(4*2/1.5+5)*22+6", 1 + 2 + 3 - (4 * 2 / 1.5 + 5) * 22 + 6, false)
    }

    @Test
    fun testSimpleEquationAddSub2() {
        testResult("4*2/5", 4 * 2.0 / 5, false)
    }

    @Test
    fun testSimpleAddSubMulDiv() {
        testResult(
            "4*2/5+8*9/10-4-4*5/9-2*3+1.2",
            (4 * 2 / 5.0 + 8 * 9 / 10.0) - 4 - (4 * 5 / 9.0) - (2 * 3) + 1.2,
            false
        )
    }

    @Test
    fun testZeroPlusZero() {
        testResult("0+0", 0.0, false)
    }

    /*@Test
    public void testMultSign() {

        testResult("-10*-10", 100, false);
    }*/
    @Test
    fun testVariable() {
        val vars = HashMap<String, Double>()
        vars["u"] = 4.0
        vars["v"] = 13.0
        testResultVariable("u+v", 4.0 + 13.0, vars, true)
    }

    @Test
    fun testVariableZeroPlusZero() {
        val vars = HashMap<String, Double>()
        vars["R"] = 0.0
        vars["u"] = 0.0
        testResultVariable("R+u", 0.0, vars, false)
    }

    @Test
    fun testVariableCircle() {
        val vars = HashMap<String, Double>()
        vars["coordArr"] = 4.0
        vars["y"] = 13.0
        vars["z"] = 13.0
        vars["R"] = 20.0
        testResultVariable(
            "coordArr*coordArr+y*y+z*z-R*R",
            (4.0 * 4) + (13.0 * 13) + (13.0 * 13.0) - 20.0 * 20.0,
            vars,
            false
        )
    }

    @Test
    fun testSimpleEquationBracedMultDiv() {
        testResult("1*2*3/4*5*4", (1.0 * 2.0 * 3.0 / 4.0) * 5.0 * 4.0, false)
    }

    @Test
    fun testComplexFunMultiple1() {
        testResult(
            "sin(1)*sin(2)*sin(2)",  //2*exp(3/4)+0.5-5*4*cos(2)",
            Math.sin(1.0) * Math.sin(2.0) * Math.sin(2.0), false
        )
    }

    @Test
    fun testComplexFunFunMultiple2() {
        testResult(
            "sin(1)*sin(2*cos(0.2)*sin(2))+2*exp(3/4)+0.5-5*4*cos(2)",
            (Math.sin(1.0) * Math.sin(2 * Math.cos(0.2) * Math.sin(2.0))) + (
                    +2 * Math.exp(3.0 / 4)) + 0.5 - 5 * 4 * Math.cos(2.0), false
        )
    }

    @Test
    fun testComplexFunFunMultiple3() {
        testResult(
            "sin(1)*sin(2*2)+2*exp(3/4)+0.5-5*4*cos(2)",
            (Math.sin(1.0) * Math.sin((2 * 2).toDouble())) + (2 * Math.exp(3 / 4.0)) + 0.5 - 5 * 4 * Math.cos(
                2.0
            ), false
        )
    }

    @Test
    fun testSimpleFunction() {
        testResult("sin(3.14)*4", Math.sin(3.14) * 4, false)
    }

    @Test
    fun testSimpleFunction1() {
        val u = 10.0
        val vars = HashMap<String, Double>()
        vars["u"] = u
        testResultVariable("10*cos(10*u)", 10 * Math.cos(10 * u), vars, true)
    }

    @Test
    fun testSimpleFunction3() {
        val u = 10.0
        val vars = HashMap<String, Double>()
        vars["u"] = u
        testResultVariable("cos(10*u)+u", Math.cos(10 * u) + u, vars, true)
    }

    @Test
    fun testSimpleFunction2() {
        val u = 10.0
        val vars = HashMap<String, Double>()
        vars["u"] = u
        testResultVariable("cos(5*u)*10", Math.cos(5 * u) * 10, vars, true)
    }

    @Test
    fun testSimple() {
        Assert.assertTrue(testResult("1", 1.0, false))
    }

    @Test
    fun testSimple3() {
        Assert.assertTrue(testResult("1+1", 2.0, false))
    }

    @Test
    fun testSimple4() {
        Assert.assertTrue(testResult("1*8*(-8)", 1 * 8.0 * -8, false))
    }

    @Test
    fun testSimple5() {
        Assert.assertTrue(testResult("6-6*(-12)", 6 - 6 * -12.0, false))
    }

    @Test
    fun testSimple6() {
        Assert.assertTrue(testResult("-5/-5*3.0", 3.0, false))
    }

    @Test
    fun testSimple7() {
        Assert.assertTrue(testResult("1-1/3*4/5*2", 1 - 1 / 3.0 * 4 / 5.0 * 2, false))
    }

    @Test
    fun testSimple8() {
        Assert.assertTrue(testResult("1-2-3-4-5-6", 1.0 - 2 - 3 - 4 - 5 - 6, false))
    }

    @Test
    fun testSimple9() {
        Assert.assertTrue(testResult("1/2/3/4/5/6", 1.0 / 2 / 3 / 4 / 5 / 6, false))
    }

    @Test
    fun testSimple10() {
        Assert.assertTrue(testResult("-1", -1.0, true))
    }

    @Test
    fun testSimpleParentheses() {
        Assert.assertTrue(testResult("(2+3)*(4+5)", ((2 + 3) * (4 + 5)).toDouble(), true))
    }

    @Test
    fun testSimpleParentheses3() {
        Assert.assertTrue(
            testResult(
                "(2+3)*(4+5)*(6+7)",
                ((2 + 3) * (4 + 5) * (6 + 7)).toDouble(),
                true
            )
        )
    }

    @Test
    fun testSimple2() {
        Assert.assertTrue(testResult("1.5", 1.5, false))
    }

    @Test
    fun testExp1() {
        Assert.assertTrue(testResult("2^3", Math.pow(2.0, 3.0), true))
    }

    @Test
    fun testExp2() {
        Assert.assertTrue(testResult("2^(3^4)", Math.pow(2.0, Math.pow(3.0, 4.0)), true))
    }

    @Test
    fun testExp3() {
        Assert.assertTrue(testResult("23^34", Math.pow(23.0, 34.0), true))
    }

    @Test
    fun testError() {
        testConstructOrEvalFails("2^6^", -2.0, false)
    }

    @Test
    fun testSimple11() {
        testResult("-1+9", -1 + 9.0, true)
    }

    @Test
    fun testSimple12() {
        testResult("(-1+9)", (-1 + 9.0), true)
    }

    @Test
    fun testSimpleVarMultVar() {
        val x = -2.0
        val vars = HashMap<String, Double>()
        vars["x"] = x
        testResultVariable("x*x", x * x, vars, true)
    }

    @Test
    fun testSimpleFunctionDefined() {
        val x = -2.0
        val vars = HashMap<String, Double>()
        vars["x"] = x
        testResultVariable("-x+(2*x)", -x + (2 * x), vars, true)
    }

    @Test
    fun testSimpleFunctionSinVar() {
        val r = 12.0
        val vars = HashMap<String, Double>()
        vars["r"] = r
        testResultVariable("sin(r*10)", Math.sin(r * 10), vars, true)
    }

    companion object {
        private val DELTA = Double.MIN_VALUE
    }

    @Test
    fun testVectorSimple1() {
        val r = 12.0
        val vars = HashMap<String, Double>()
        vars["r"] = r
        testResultVariable("(0,1,0)", 0.0, vars, true)
    }
    @Test
    fun testVectorVariable() {
        val r = 12.0
        val vars = HashMap<String, Double>()
        val x =  1.0
        val y =  2.1
        val z = 50.0
        vars["x"] = x
        vars["y"] = y
        vars["z"] = z
        testResultVariableVec("(x,y,z)", Vec(x, y, z),  vars, true)
    }

    private fun testResultVariableVec(
        expr: String,
        expectedResult: Vec,
        map: java.util.HashMap<String, Double>,
        echo: Boolean
    ) {
        var algebricTree: AlgebricTree? = null
        try {
            println("Expression string : $expr")
            algebricTree = AlgebricTree(expr)
            algebricTree.parametersValues = map
            algebricTree.construct()
            if (echo) println(algebricTree)
            try {
                val result :StructureMatrix<Double> = algebricTree.eval()

                println(result)

                var assertion = true
                if(((result == null) || (result.data1d==null))
                    || (expectedResult == null || expectedResult.vecVal!=null)) {
                    if(((result == null) || (result.data1d==null))
                        && (expectedResult == null || expectedResult.vecVal!=null)) {
                        assertion = true
                    } else {
                        assertion = false
                    }
                } else if(result.getData1d().size==expectedResult.size()) {
                    var i = 0
                    result.getData1d().forEach {
                        if (it - DELTA < expectedResult[i] && it + DELTA > expectedResult[i]) {

                        } else {
                            assertion = false;
                        }
                        i++
                    }
                }
                Assert.assertTrue(assertion)
                if (echo) println("Result : $result")
                if (echo) println("Expected : $expectedResult")
                if(result!=null &&expectedResult!=null) {
                    Assert.assertTrue(result.equals(expectedResult))
                }
            } catch (e: TreeNodeEvalException) {
                e.printStackTrace()
                Assert.assertFalse(true)
            }
        } catch (e: AlgebraicFormulaSyntaxException) {
            e.printStackTrace()
            Assert.assertFalse(true)
        } catch (ex: NullPointerException) {
            ex.printStackTrace()
            Assert.assertFalse(true)
        }
    }

    @Test
    fun testForVectorSimple1() {
        val r = 12.0
        val vars = HashMap<String, Double>()
        vars["r"] = r
        testResultVariable("(0+1+0)", 1.0, vars, true)
    }
}
