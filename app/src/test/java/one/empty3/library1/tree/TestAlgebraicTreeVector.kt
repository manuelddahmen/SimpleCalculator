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

package one.empty3.library1.tree

import junit.framework.TestCase.assertTrue
import one.empty3.library.Point2D
import one.empty3.library.Point3D
import one.empty3.library.StructureMatrix
import one.empty3.library1.shader.Vec
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.Array
import kotlin.math.pow

/**
 * Test class for AlgebraicTree.
 */
@RunWith(JUnit4::class)
class TestAlgebraicTreeVector() {

    private fun testResultVariable(
        expr: String,
        expectedResult: Double,
        map: Map<String, Double>,
        echo: Boolean,
    ) {
        var algebraicTree: AlgebraicTree?
        try {
            println("Expression string : $expr")
            algebraicTree = AlgebraicTree(expr)
            algebraicTree.parametersValues = map
            algebraicTree.construct()
            if (echo) println(algebraicTree)
            try {
                var result: Double
                val eval = algebraicTree.eval()
                println("eval dims:" + eval.dim + " ")
                if (eval.dim == 1)
                    println("eval vector size : " + eval.data1d.size)
                if (eval.dim == 1 && eval.data1d.size > 0) {
                    result = algebraicTree.eval().getElem(0)
                } else if (eval.dim == 0 || eval.data0d != null) {
                    result = algebraicTree.eval().getElem()
                } else {
                    throw AlgebraicFormulaSyntaxException("Cannot evaluate")
                }
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
        var algebraicTree: AlgebraicTree?
        try {
            println("Expression string : $expr")
            algebraicTree = AlgebraicTree(expr)
            algebraicTree.construct()
            if (echo) println(algebraicTree)
            try {
                val result: Double
                result = algebraicTree.eval().getElem()
                if (echo) println("Result : $result")
                if (echo) println("Expected : $expectedResult")
                if (result == expectedResult) {
                    Assert.assertTrue(true)
                } else Assert.assertTrue(
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
        echo: Boolean,
    ): Boolean {
        var algebraicTree: AlgebraicTree?
        try {
            println("Expression string : $expr")
            algebraicTree = AlgebraicTree(expr)
            algebraicTree.construct()
            if (echo) println(algebraicTree)
            try {
                val result: Any
                result = algebraicTree.eval()
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
    fun testSimpleNumber1() {
        testResult("1", 1.0, false)
    }

    @Test
    fun testSimpleNumber0() {
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
    fun testVariableAdd() {
        val vars = HashMap<String, Double>()
        vars["u"] = 4.0
        vars["v"] = 13.0
        testResultVariable("u+v", 4.0 + 13.0, vars, true)
    }

    @Test
    fun testVariableSub() {
        val vars = HashMap<String, Double>()
        vars["u"] = 4.0
        vars["v"] = 13.0
        testResultVariable("u-v", 4.0 - 13.0, vars, true)
    }

    @Test
    fun testVariableMul() {
        val vars = HashMap<String, Double>()
        vars["u"] = 4.0
        vars["v"] = 13.0
        testResultVariable("u*v", 4.0 * 13.0, vars, true)
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
        testResultVariable("(-5)/(-5)*3.0", -5 / -5 * 3.0, HashMap<String, Double>(), true)
    }

    @Test
    fun testSimple6_1() {
        testResultVariable("(-5)/(-5)*3.0", -5.0 / (-5) * 3.0, HashMap<String, Double>(), true)
    }

    @Test
    fun testSimple6_2() {
        testResultVariable("(-5)/(-5)*3.0", -5.0 / (-5) * 3.0, HashMap<String, Double>(), true)
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
    fun testSimple13() {
        testResult("6+6-(2*6)", 6.0 + 6 - (2 * 6), true)
    }

    @Test
    fun testSimple14() {
        testResult("6+6-(6+6)", 6.0 + 6 - (6 + 6), true)
    }

    @Test
    fun testSimple15() {
        testResult("3^4^5", Math.pow(Math.pow(3.0, 4.0), 5.0), true)
    }

    @Test
    fun testSimple16() {
        testResult("3^4+2^3", Math.pow(3.0, 4.0) + Math.pow(2.0, 3.0), false)
    }

    @Test
    fun testSimple17() {
        testResult("3^(4^5)", Math.pow(3.0, Math.pow(4.0, 5.0)), true)
    }

    @Test
    fun testSimple18() {
        testResult("(-3)+(4* 5)", (-3) + (4 * 5.0), true)
    }

    @Test
    fun testSimple19() {
        testResult("-(1+10)+9", -(1.0 + 10) + 9, true)
    }

    @Test
    fun testSimpleVarMultVar() {
        val x = -2.0
        val vars = HashMap<String, Double>()
        vars["x"] = x
        testResultVariable("x*x", x * x, vars, true)
    }

    @Test
    fun testSimpleMinusSignAddition() {
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


    @Test
    fun testVectorSimple1() {
        val vars = HashMap<String, Double>()
        testResultVariableVec("(0,1,0)", Vec(0.0, 1.0, 0.0), vars, true)
    }

    @Test
    fun testVectorVariable() {
        val vars = HashMap<String, Double>()
        val x = 1.0
        val y = 2.1
        val z = 50.0
        vars["x"] = x
        vars["y"] = y
        vars["z"] = z
        testResultVariableVec("(x,y,z)", Vec(x, y, z), vars, true)
    }

    @Test
    fun testVectorInVector() {
        val vars = HashMap<String, Double>()
        val x = 1.0
        val y = 2.1
        val z = 50.0
        vars["x"] = x
        vars["y"] = y
        vars["z"] = z
        testResultVariableVec("(x,((1+z),y,(1+z)))", Vec(x, (1 + z), y, 1 + z), vars, true)
    }

    private fun testResultVariableVec(
        expr: String,
        expectedResult: Vec,
        map: HashMap<String, Double>,
        echo: Boolean,
    ) {
        var algebraicTree: AlgebraicTree?
        try {
            println("Expression string : $expr")
            algebraicTree = AlgebraicTree(expr)
            algebraicTree.parametersValues = map
            algebraicTree.construct()
            if (echo) println(algebraicTree)
            try {
                val result: StructureMatrix<Double> = algebraicTree.eval()

                println("Result : $result")
                if (echo) println("Expected : $expectedResult")

                var assertion: Boolean

                try {
                    if (vecEqualsSM(result, expectedResult)) {
                        assertion = true
                    } else {
                        assertion = false
                    }
                } catch (ex: NullPointerException) {
                    assertion = false
                }

                Assert.assertTrue(assertion)

                if (echo) println("Result : $result");

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
        testResultVariableVec("(0,1,0)", Vec(0.0, 1.0, 0.0), vars, true)
    }

    @Test
    fun testForVectorSum() {
        val r = 12.0
        val vars = HashMap<String, Double>()
        vars["r"] = r
        testResultVariableVec("(2,1,2)+(2,2,3)", Vec(4.0, 3.0, 5.0), vars, false)
    }

    @Test
    fun testForVectorSubstract() {
        val r = 12.0
        val vars = HashMap<String, Double>()
        vars["r"] = r
        testResultVariableVec("(2,1,2)-(2,2,3)", Vec(0.0, -1.0, -1.0), vars, true)
    }

    @Test
    fun testForVectorDot() {
        val r = 12.0
        val vars = HashMap<String, Double>()
        vars["r"] = r
        testResultVariableVec("(2,1,2)*(2,2,3)", Vec(4.0, 2.0, 6.0), vars, true)
    }

    @Test
    fun testForVectorSumOfComponents() {
        val r = 12.0
        val vars = HashMap<String, Double>()
        vars["r"] = r
        testResultVariableVec(
            "sum((2,1,2,2,2,3))",
            Vec(12.0), vars, true
        )
    }

    @Test
    fun testForVectorMulVec() {
        val vars = HashMap<String, Double>()
        val a: Point3D = Point3D(2.0, 1.0, 2.0)
        val b: Point3D = Point3D(2.0, 2.0, 3.0)
        val r: Point3D = a.prodVect(b)
        val s: Vec = Vec(r.x, r.y, r.z)
        testResultVariableVec("(2,1,2)^(2,2,3)", s, vars, true)
    }

    @Test
    fun testForVectorMulVecDim2() {
        val vars = HashMap<String, Double>()
        val a: Point2D = Point2D(2.0, 1.0)
        val b: Point2D = Point2D(2.0, 2.0)
        val r: Point2D = a.mult(b)
        val s: Vec = Vec(r.x, r.y)
        testResultVariableVec("(2,1)^(2,2)", s, vars, true)
    }

    @Test
    fun testForVectorDotSum() {
        val r = 12.0
        val vars = HashMap<String, Double>()
        vars["r"] = r
        testResultVariableVec("(9,1,3)+(2,1,2)*(2,2,3)+(1,2,3)", Vec(14.0, 5.0, 12.0), vars, true)
    }

    @Test
    fun testForVectorOfVectorMultiNumber() {
        val r = 12.0
        val vars = HashMap<String, Double>()
        vars["r"] = r
        testResultVariableVec(
            "((1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1),(1,1,2,2,2,2,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1))*10",
            Vec(
                10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0,
                10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0,
                10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0
            ), vars, true
        )
    }

    @Test
    fun testForNumberMultiVectorOfVector() {
        val r = 12.0
        val vars = HashMap<String, Double>()
        vars["r"] = r
        testResultVariableVec(
            "10*((1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1),(1,1,2,2,2,2,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1))",
            Vec(
                10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0,
                10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0,
                10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0
            ), vars, true
        )
    }

    @Test
    fun testForNumberMultiVectorOfVector4() {
        val r = 12.0
        val vars = HashMap<String, Double>()
        vars["r"] = r
        testResultVariableVec(
            "10*(20*(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1),(1,1,2,2,2,2,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1)*40)",
            Vec(
                200.0,
                200.0,
                200.0,
                200.0,
                200.0,
                200.0,
                200.0,
                200.0,
                200.0,
                200.0,
                200.0,
                200.0,
                200.0,
                200.0,
                200.0,
                200.0,
                200.0,
                200.0,
                200.0,
                200.0,
                200.0,
                200.0,
                400.0,
                400.0,
                800.0,
                800.0,
                800.0,
                800.0,
                800.0,
                800.0,
                800.0,
                400.0,
                400.0,
                400.0,
                400.0,
                400.0,
                400.0,
                400.0,
                400.0,
                400.0,
                400.0,
                400.0,
                400.0,
                400.0
            ), vars, true
        )
    }

    @Test
    fun testForVectorOfVector() {
        val r = 12.0
        val vars = HashMap<String, Double>()
        vars["r"] = r
        testResultVariableVec("((2,1,2),(2,2,3),(1,2,3))", Vec(2.0, 1.0, 2.0, 2.0, 2.0, 3.0, 1.0, 2.0, 3.0), vars, true)
    }

    @Test
    fun testForVectorOfVectorWithSpaces() {
        val r = 12.0
        val vars = HashMap<String, Double>()
        vars["r"] = r
        testResultVariableVec(
            " ( ( 2 , 1 , 2 ) , ( 2 , 2 , 3 ) , ( 1, 2 , 3 ) ) ",
            Vec(2.0, 1.0, 2.0, 2.0, 2.0, 3.0, 1.0, 2.0, 3.0),
            vars,
            true
        )
    }

    @Test
    fun testForVectorSumWithSpaces() {
        val r = 12.0
        val vars = HashMap<String, Double>()
        vars["r"] = r
        testResultVariableVec(
            " ( 2 , 1 , 2 ) + ( 2 , 2 , 3 ) - ( 1, 2 , 3 )  ",
            Vec(3.0, 1.0, 2.0),
            vars,
            true
        )
    }

    @Test
    fun testForFunctionWithSpaces() {
        val r = 12.0
        val vars = HashMap<String, Double>()
        vars["r"] = r
        val algebraicTree = AlgebraicTree(
            "func1(a, b, c) {\n" +
                    "d = c+b/a\n" +
                    "\n" +
                    "}\n"
        )
        algebraicTree.construct()
        algebraicTree.eval()
    }

    @Test
    fun testStringAnalyzer0() {
        val r = 12.0
        val vars = HashMap<String, Double>()
        vars["r"] = r
        val stringAnalyzer3 = StringAnalyzerJava1()
        val javaToken5 = TestStringAnalyzer5().getJavaToken5(stringAnalyzer3)

        val input =
            "package one.empty3;\n\n"
        var parse = 0
        try {
            javaToken5.parse(input, 0)
            parse = stringAnalyzer3.mPosition
        } catch (ex: RuntimeException) {
            ex.printStackTrace()
        }
        println("" + stringAnalyzer3.mPosition + "/" + input.length)
        assertTrue(stringAnalyzer3.mPosition + 2 >= input.length)
    }

    @Test
    fun testStringAnalyzer3() {
        val r = 12.0
        val vars = HashMap<String, Double>()
        vars["r"] = r
        val stringAnalyzer3 = StringAnalyzerJava1()
        val javaToken5 = TestStringAnalyzer5().getJavaToken5(stringAnalyzer3)

        val input =
            "package one.empty3;\n\n" +
                    "class Number {\n" +
                    "\tdouble func1(Double a, Double b, Double c) {\n" +
                    "\t\tDouble d = c+b/a;\n" +
                    "\t\treturn d;\n" +
                    "\t}\n" +
                    "}\n"

        var parse = 0
        try {
            parse = javaToken5.parse(input, 0)
            parse = stringAnalyzer3.mPosition
            println("" + parse + "/" + input.length)
            assertTrue(parse + 2 == input.length)
        } catch (ex: RuntimeException) {
            ex.printStackTrace()
        }
    }

    @Test
    fun testStringAnalyzerV1_1() {
        val r = 12.0
        val vars = HashMap<String, Double>()
        vars["r"] = r
        val stringAnalyser: StringAnalyzer3 = StringAnalyzer3()

        val input =
            "package one.empty3;\n\n" +
                    "class Number {\n" +
                    "\tdouble func1(Double a, Double b, Double c) {\n" +
                    "\t\tDouble d = c+b/a;\n" +
                    "\t\tDouble e = d+c/a;\n" +
                    "\t\treturn e;\n" +
                    "\t}\n" +
                    "}\n"
        runCodeParser(input)
    }

    @Test
    fun testStringAnalyzerV1_2() {
        val r = 12.0
        val vars = HashMap<String, Double>()
        vars["r"] = r

        val input =
            "package one.empty3;\n\n" +
                    "class Number {\n" +
                    "\tdouble func1(Double a, Double b, Double c) {\n" +
                    "\t\tDouble d = c+b/a;\n" +
                    "\t\tDouble e = d+c/a;\n" +
                    "\t\treturn e;\n" +
                    "\t}\n" +
                    "\tdouble func2(Double a) {\n" +
                    "\t\tDouble d = a*a;\n" +
                    "\t\ta = d+1;\n" +
                    "\t\treturn a;\n" +
                    "\t}\n" +
                    "}\n"
        runCodeParser(input)
    }

    fun runCodeParser(input: String) {
        val stringAnalyser: StringAnalyzerJava1 = StringAnalyzerJava1()

        var parse: Int = 0
        try {
            TestStringAnalyzer5().getJavaToken5(stringAnalyser).parse(input, 0)
            parse = stringAnalyser.mPosition
        } catch (ex: RuntimeException) {
        }
        println("" + parse + "/" + input.length)
        println(stringAnalyser.construct)
        println(input)
        assertTrue(parse + 2 >= input.length || input.substring(parse).trim().isEmpty())
    }

    @Test
    fun testTextCalculator3() {
        val listInstructions: ListInstructions = ListInstructions()
        listInstructions.addInstructions(
            "x=(1,2,3)\n"
                    + "y=(5,6,7)\n"
                    + "z=x+y\n"
                    + "1=\n"
                    + "2*2\n"
                    + "4*4\n" +
                    "#comment to keep\n" +
                    "## Comment to renew or erase if no instruction generates it\n"
        )
        val list = listInstructions.runInstructions()
        System.out.println()
        System.out.println()
        System.out.println("((((((((((((((((((((((((((((((((((((((((")
        list.forEach(System.out::println)
        System.out.println("))))))))))))))))))))))))))))))))))))))))")
        System.out.println()
        System.out.println()

        println("x: " + listInstructions.getCurrentParamsValuesVecComputed()!!["x"]!!)
        println("y: " + listInstructions.getCurrentParamsValuesVecComputed()!!["y"]!!)
        println("z: " + listInstructions.getCurrentParamsValuesVecComputed()!!["z"]!!)


        var assertion = false
        try {
            if (listInstructions.getCurrentParamsValuesVecComputed() != null &&
                vecEqualsSM(
                    listInstructions.getCurrentParamsValuesVecComputed()!!["z"]!!,
                    Vec(1.0 + 5, 2.0 + 6, 3.0 + 7)
                )
            ) {
                assertion = true
            }
        } catch (ex: RuntimeException) {
            ex.printStackTrace()
        }
        Assert.assertTrue(assertion)
    }

    /***
     * @param get computed value
     * @param vec expected value
     */
    fun vecEqualsSM(get: StructureMatrix<Double>, vec: Vec): Boolean {
        if (get.dim == 0) {
            if ((get.data0d != null && vec.vecVal.data0d != null &&
                        vec.vecVal.data0d.equals(get.data0d))
            ) {
                return true
            } else {
                println("get : StructureMatrix<Double> : invalid StructureMatrix or Vec { $get, $vec }")
                return false
            }
        } else if (get.dim == 1) {
            if ((get.data1d != null && vec.vecVal.data1d != null)) {
                for (i in 0 until get.data1d.size) {
                    println("Computed computed vec : $get")
                    println("Expected computed vec : $vec")
                    if (get.data1d[i] != vec.vecVal.data1d[i]) {
                        println("equals(StructureMatrix<Double>,Vec) : invalid for index $i")
                        return false
                    }
                }
                return true
            } else {
                println("StructureMatrix<Double>,Vec one or two terms has 1-dim null error")
                return false
            }
        } else {
            println("StructureMatrix<Double>,Vec sm==null or sm.dim=invalid")
            return false
        }
    }

    companion object {
        private val DELTA = Double.MIN_VALUE

        @JvmStatic
        fun main(args: Array<String>) {

        }
    }
}

