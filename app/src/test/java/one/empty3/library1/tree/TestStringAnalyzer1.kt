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

import one.empty3.library1.tree.StringAnalyzer3.TokenName
import one.empty3.library1.tree.StringAnalyzer3.TokenQualifiedName
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.Boolean
import kotlin.RuntimeException

/**
 * Test class for AlgebraicTree.
 */
@RunWith(JUnit4::class)
class TestStringAnalyzer1 {
    private val isDebug: Boolean = true

    fun readString(file_path: String): String {
        try {
            val allLines = Files.readAllLines(Paths.get(file_path))
            // Convert the List of strings to a single string
            val sb: StringBuilder = StringBuilder()
            allLines.forEach { s -> sb.append(s).append("\n") }
            return sb.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        throw RuntimeException("Not found or read fails")
    }

    @Test
    fun testStringAnalyzerPackage() {
        val stringAnalyzer1 = StringAnalyzer3()
        val token = stringAnalyzer1.TokenPackage()
        val input = "package"
        var parse = -1
        try {
            parse = stringAnalyzer1.parse(token, input)
//            parse = stringAnalyzer1.mPosition
        } catch (ex: RuntimeException) {
            if (token.isSuccessful) {
                if (stringAnalyzer1.mPosition >= input.length) {
                    Assert.assertTrue(true)

                    println(stringAnalyzer1.construct)
                    return
                }
            }

            Assert.assertTrue(false)
        }

        println("parse = " + stringAnalyzer1.mPosition + "/" + input.length)
        println("isSuccessful = " + token.isSuccessful)


        if (token.isSuccessful) {
            if (parse >= input.length) {
                Assert.assertTrue(true)
                return

            }
        }
        Assert.assertTrue(false)

    }

    @Test
    fun testStringAnalyzerPackageDeclaration() {
        val stringAnalyzer1 = StringAnalyzer3()
        val tokenPackage = stringAnalyzer1.TokenPackage()
        val tokenPackageName = stringAnalyzer1.TokenQualifiedName()
        val tokenPackageSemicolon = stringAnalyzer1.TokenSemiColon()

        tokenPackage.addToken(tokenPackageName)
        tokenPackageName.addToken(tokenPackageSemicolon)

        val token = stringAnalyzer1.SingleTokenOptional(tokenPackage)


        val input = "package com.example;"
        var parse = -1
        try {
            parse = stringAnalyzer1.parse(token, input)
//            parse = stringAnalyzer1.mPosition
        } catch (ex: RuntimeException) {
            if (token.isSuccessful) {
                if (parse >= input.length) {
                    Assert.assertTrue(true)

                    println(stringAnalyzer1.construct)
                    return
                }
            }

            Assert.assertTrue(false)
        }

        stringAnalyzer1.construct.packageName = tokenPackageName.name


        println("TestStringAnalyzer3.testStringAnalyzerPackage")
        println("parse = " + parse + "/" + input.length)
        println("isSuccessful = " + token.isSuccessful + " tokenSemicolon : " + tokenPackageSemicolon.isSuccessful)
        println("Name : " + tokenPackageName.name)

        if (token.isSuccessful) {
            if (parse >= input.length) {
                Assert.assertTrue(true)

                println(stringAnalyzer1.construct)
                return
            }
        }

        Assert.assertTrue(false)


    }

    @Test
    fun testStringAnalyzerPackageAndClassDeclaration() {
        val stringAnalyzer1 = StringAnalyzer3()
        val tokenPackage = stringAnalyzer1.TokenPackage()
        val tokenPackageName = stringAnalyzer1.TokenQualifiedName()
        val tokenPackageSemicolon = stringAnalyzer1.TokenSemiColon()
        val tokenClass = stringAnalyzer1.TokenClassKeyword()
        val tokenClassName = stringAnalyzer1.TokenName()
        val tokenOpenBracket = stringAnalyzer1.TokenOpenBracket()
        val tokenCloseBracket = stringAnalyzer1.TokenCloseBracket()


        tokenPackage.addToken(tokenPackageName)
        tokenPackageName.addToken(tokenPackageSemicolon)
        tokenClass.addToken(tokenClassName)
        tokenClassName.addToken(tokenOpenBracket)
        tokenOpenBracket.addToken(tokenCloseBracket)

        val token = stringAnalyzer1.SingleTokenOptional(tokenPackage)

        token.addToken(tokenClass)

        val input = "package com.example;\nclass Graph {\n}"
        var parse = -1
        try {
            parse = stringAnalyzer1.parse(token, input)
//            parse = stringAnalyzer1.mPosition
        } catch (ex: RuntimeException) {
            if (token.isSuccessful) {
                if (parse >= input.length) {
                    Assert.assertTrue(true)

                    println(stringAnalyzer1.construct)
                    return
                }
            }

            Assert.assertTrue(false)
        }

        stringAnalyzer1.construct.packageName = tokenPackageName.name
        stringAnalyzer1.construct.currentClass.name = tokenClassName.name


        println("TestStringAnalyzer3.testStringAnalyzerPackage")
        println("parse = " + parse + "/" + input.length)
        println("isSuccessful = " + token.isSuccessful + " tokenSemicolon : " + tokenPackageSemicolon.isSuccessful)
        println("Name : " + tokenPackageName.name)

        if (token.isSuccessful) {
            if (parse >= input.length) {
                Assert.assertTrue(true)

                println(stringAnalyzer1.construct)
                return
            }
        }

        Assert.assertTrue(false)


    }

    /*
        private fun buildJavaClassTokenList(stringAnalyzer1: StringAnalyzer3): StringAnalyzer3.SingleTokenOptional {
            val tokenPackage = stringAnalyzer1.TokenPackage()
            val tokenPackageName = stringAnalyzer1.TokenQualifiedName()
            val tokenPackageSemicolon = stringAnalyzer1.TokenSemiColon()
            val tokenClass = stringAnalyzer1.TokenClassKeyword()
            val tokenClassName = stringAnalyzer1.TokenName()
            val tokenOpenBracket = stringAnalyzer1.TokenOpenBracket()
            val tokenCloseBracket = stringAnalyzer1.TokenCloseBracket()

            val tokenMemberVarType1 = stringAnalyzer1.TokenQualifiedName()
            val tokenMemberVarName1 = stringAnalyzer1.TokenName()
            val tokenMemberVarEquals1 = stringAnalyzer1.TokenEquals()
            val tokenMemberExpression1 = stringAnalyzer1.TokenExpression1()
            val tokenSemiColonVar1 = stringAnalyzer1.TokenSemiColon()

            val tokenMemberVarType2 = stringAnalyzer1.TokenQualifiedName()
            val tokenMemberVarName2 = stringAnalyzer1.TokenName()
            val tokenSemiColonVar2 = stringAnalyzer1.TokenSemiColon()

            tokenMemberVarType1.addToken(tokenMemberVarName1)
            tokenMemberVarEquals1.addToken(tokenMemberVarEquals1)
            tokenMemberVarEquals1.addToken(tokenMemberExpression1)
            tokenMemberExpression1.addToken(tokenSemiColonVar1)

            tokenMemberVarType2.addToken(tokenMemberVarName2)
            tokenMemberVarName2.addToken(tokenSemiColonVar2)


            val tokenMemberMethodVarType1 = stringAnalyzer1.TokenQualifiedName()
            val tokenMemberMethodVarName1 = stringAnalyzer1.TokenName()
            val tokenSemiMethodColonVar1 = stringAnalyzer1.TokenSemiColon()

            val tokenMemberMethodVarType2 = stringAnalyzer1.TokenQualifiedName()
            val tokenMemberMethodVarName2 = stringAnalyzer1.TokenName()
            val tokenMemberMethodVarEquals2 = stringAnalyzer1.TokenEquals()
            val tokenMemberMethodExpression2 = stringAnalyzer1.TokenExpression1()
            val tokenMethodSemiColonVar2 = stringAnalyzer1.TokenSemiColon()


            tokenMemberMethodVarType2.addToken(tokenMemberMethodVarName2)
            tokenMemberMethodVarName2.addToken(tokenMemberMethodVarEquals2)
            tokenMemberMethodVarEquals2.addToken(tokenMethodSemiColonVar2)
            tokenMethodSemiColonVar2.addToken(tokenMemberMethodExpression2)

            tokenMemberMethodVarType1.addToken(tokenMemberMethodVarName1)
            tokenMemberMethodVarName1.addToken(tokenSemiMethodColonVar1)

            val tokenMemberVar = stringAnalyzer1.SingleTokenExclusiveXor(
                tokenMemberVarType1, tokenMemberVarType2
            )

            val tokenMemberMethodType = stringAnalyzer1.TokenQualifiedName()
            val tokenMemberMethodName = stringAnalyzer1.TokenName()
            val tokenOpenBracketMethod = stringAnalyzer1.TokenOpenBracket()

            tokenMemberMethodType.addToken(tokenMemberMethodName)
            tokenMemberMethodName.addToken(tokenOpenBracketMethod)

            // Instructions
            val singleTokenExclusiveXorMethodInstruction =
                stringAnalyzer1.SingleTokenExclusiveXor(
                    tokenMemberMethodVarType1,
                    tokenMemberMethodVarType2
                )

            val tokenMemberMethodVarType = stringAnalyzer1.TokenQualifiedName()
            val tokenMemberMethodVarName = stringAnalyzer1.TokenName()
            val tokenMemberMethodEquals = stringAnalyzer1.TokenEquals()
            val tokenMemberMethodExpression = stringAnalyzer1.TokenExpression1()


            // End of Instructions

            val tokenMultiMembersInstructions = stringAnalyzer1.MultiTokenOptional(
                tokenMemberMethodVarType1, tokenMemberMethodVarType2
            )

            tokenMemberMethodType.addToken(tokenMultiMembersInstructions)

            tokenPackage.addToken(tokenPackageName)
            tokenPackageName.addToken(tokenPackageSemicolon)
            tokenClass.addToken(tokenClassName)
            tokenClassName.addToken(tokenOpenBracket)
            val multiTokenOptional = stringAnalyzer1.MultiTokenOptional(
                tokenMemberVar, tokenMemberMethodType
            )
            tokenOpenBracket.addToken(multiTokenOptional)
            multiTokenOptional.addToken(tokenCloseBracket)

            val token = stringAnalyzer1.SingleTokenOptional(tokenPackage)

            token.addToken(tokenClass)

            return token

        }
    */
    fun getJavaToken(stringAnalyzer1: StringAnalyzer3): StringAnalyzer3.Token {
        val tokenPackage = stringAnalyzer1.TokenPackage()
        val tokenPackageName = stringAnalyzer1.TokenQualifiedName()
        val tokenPackageSemicolon = stringAnalyzer1.TokenSemiColon()
        val tokenClass: StringAnalyzer3.Token = stringAnalyzer1.TokenClassKeyword()


        class ActionPackageName : Action(tokenPackageName) {
            public override fun action(): Boolean {
                if (!tokenPackageName.name.isEmpty()) {
                    stringAnalyzer1.construct.currentClass.setPackageName(tokenPackageName.name)
                    stringAnalyzer1.construct.packageName = tokenPackageName.name
                }
                return true
            }
        }
        tokenPackageName.setAction(ActionPackageName())

        val tokenImport = stringAnalyzer1.TokenString("import")
        val tokenImportName = stringAnalyzer1.TokenQualifiedName()
        val tokenImportSemicolon = stringAnalyzer1.TokenSemiColon()


        class ActionClassKeyword : Action(tokenClass) {
            public override fun action(): Boolean {
                stringAnalyzer1.construct.classes.add(stringAnalyzer1.construct.currentClass)
                stringAnalyzer1.construct.currentClass.setPackageName(stringAnalyzer1.construct.packageName)
                return true
            }
        }
        tokenClass.setAction(ActionClassKeyword())

        val tokenClassName = stringAnalyzer1.TokenName()

        class ActionClassname : Action(tokenClassName) {
            override fun action(): Boolean {
                if (tokenClassName.name != null) {
                    stringAnalyzer1.construct.currentClass.name = tokenClassName.name
                }
                return true
            }
        }
        tokenClass.setAction(ActionClassname())
        val tokenOpenBracket = stringAnalyzer1.TokenOpenBracket()
        val tokenCloseBracketClass = stringAnalyzer1.TokenCloseBracket()

        // Variables members declarations
        val tokenMemberVarType1 = stringAnalyzer1.TokenQualifiedName()
        val tokenMemberVarName1 = stringAnalyzer1.TokenName()
        val tokenMemberVarEquals1 = stringAnalyzer1.TokenEquals()
        val tokenMemberExpression1 = stringAnalyzer1.TokenExpression1()
        val tokenMemberVarSemiColon1 = stringAnalyzer1.TokenSemiColon()

        val tokenMemberVarType2 = stringAnalyzer1.TokenQualifiedName()
        val tokenMemberVarName2 = stringAnalyzer1.TokenName()
        val tokenMemberVarSemiColon2 = stringAnalyzer1.TokenSemiColon()

        tokenMemberVarType1.addToken(tokenMemberVarName1)
        tokenMemberVarName1.addToken(tokenMemberVarEquals1)
        tokenMemberVarEquals1.addToken(tokenMemberExpression1)
        tokenMemberExpression1.addToken(tokenMemberVarSemiColon1)

        tokenMemberVarType2.addToken(tokenMemberVarName2)
        tokenMemberVarName2.addToken(tokenMemberVarSemiColon2)

        // Method's instructions
        val tokenMemberMethodVarType1 = stringAnalyzer1.TokenQualifiedName()
        val tokenMemberMethodVarName1 = stringAnalyzer1.TokenName()
        val tokenMethodSemiColonVar1 = stringAnalyzer1.TokenSemiColon()

        val tokenMemberMethodVarType2 = stringAnalyzer1.TokenQualifiedName()
        val tokenMemberMethodVarName2 = stringAnalyzer1.TokenName()
        val tokenMemberMethodVarEquals2 = stringAnalyzer1.TokenEquals()
        val tokenMemberMethodExpression2 = stringAnalyzer1.TokenExpression1()
        val tokenMethodSemiColonVar2 = stringAnalyzer1.TokenSemiColon()

        val tokenMemberMethodExpression3 = stringAnalyzer1.TokenExpression1()
        val tokenMethodSemiColonVar3 = stringAnalyzer1.TokenSemiColon()

        val tokenMemberMethodVarName4 = stringAnalyzer1.TokenName()
        val tokenMemberMethodVarEquals4 = stringAnalyzer1.TokenEquals()
        val tokenMemberMethodExpression4 = stringAnalyzer1.TokenExpression1()
        val tokenMethodSemiColonVar4 = stringAnalyzer1.TokenSemiColon()

        class ActionTokenSemiColonInstruction(token: StringAnalyzer3.Token?) : Action(token) {
            override fun action(): Boolean {
                val instructions = stringAnalyzer1.construct.currentMethod.instructions
                val instruction = Instruction()
                if (tokenMemberMethodVarType1.name != null) {
                    instruction.type = tokenMemberMethodVarType1.name
                }
                if (tokenMemberMethodVarType2.name != null) {
                    instruction.type = tokenMemberMethodVarType2.name
                }
                if (tokenMemberMethodVarName1.name != null) {
                    instruction.name = tokenMemberMethodVarName1.name
                }
                if (tokenMemberMethodVarName2.name != null) {
                    instruction.name = tokenMemberMethodVarName2.name
                    instruction.expression.leftHand = tokenMemberMethodVarName2.name
                }
                if (tokenMemberMethodVarName4.name != null) {
                    instruction.name = tokenMemberMethodVarName4.name
                    instruction.expression.leftHand = tokenMemberMethodVarName2.name
                }
                if (tokenMemberMethodExpression2.expression != null) {
                    instruction.expression.expression = tokenMemberMethodExpression2.expression
                }
                if (tokenMemberMethodExpression4.expression != null) {
                    instruction.expression.expression = tokenMemberMethodExpression4.expression
                }
                instructions.instructionList.add(instruction)
                return true
            }

        }/*
        ActionTokenSemiColonInstruction(tokenSemiColonVarSemiColon1)
        ActionTokenSemiColonInstruction(tokenSemiColonVarSemiColon2)
        ActionTokenSemiColonInstruction(tokenSemiColonVarSemiColon3)
*/

        tokenMemberMethodVarType2.addToken(tokenMemberMethodVarName2)
        tokenMemberMethodVarName2.addToken(tokenMemberMethodVarEquals2)
        tokenMemberMethodVarEquals2.addToken(tokenMemberMethodExpression2)
        tokenMemberMethodExpression2.addToken(tokenMethodSemiColonVar2)

        tokenMemberMethodVarType1.addToken(tokenMemberMethodVarName1)
        tokenMemberMethodVarName1.addToken(tokenMethodSemiColonVar1)

        tokenMemberMethodExpression3.addToken(tokenMethodSemiColonVar3)

        tokenMemberMethodVarName4.addToken(tokenMemberMethodVarEquals4)
        tokenMemberMethodVarEquals4.addToken(tokenMemberMethodExpression4)
        tokenMemberMethodExpression4.addToken(tokenMethodSemiColonVar4)

        val tokenMemberVar = stringAnalyzer1.SingleTokenExclusiveXor(
            tokenMemberVarType1, tokenMemberVarType2
        )

        val tokenMemberMethodType = stringAnalyzer1.TokenQualifiedName()
        val tokenMemberMethodName = stringAnalyzer1.TokenName()

        // Arguments' list
        val tokenOpenParenthesizedMethodParameter = stringAnalyzer1.TokenOpenParenthesized()
        val tokenComaMethodParameter1 = stringAnalyzer1.TokenComa()
        val tokenQualifiedNameMethodParameter1 = stringAnalyzer1.TokenQualifiedName()
        val tokenNameMethodParameter1 = stringAnalyzer1.TokenName()

        //val tokenComaMethodParameter2 = stringAnalyzer1.TokenComa()
        val tokenQualifiedNameMethodParameter2 = stringAnalyzer1.TokenQualifiedName()
        val tokenNameMethodParameter2 = stringAnalyzer1.TokenName()

        val tokenCloseParenthesizedMethodParameter = stringAnalyzer1.TokenCloseParenthesized()

        val multiTokenOptionalMethodParameter2 =
            stringAnalyzer1.MultiTokenMandatory(
                tokenComaMethodParameter1, tokenQualifiedNameMethodParameter1, tokenNameMethodParameter1
            )
        val multiTokenOptionalMethodParameter1 =
            stringAnalyzer1.MultiTokenMandatory(
                tokenQualifiedNameMethodParameter2, tokenNameMethodParameter2
            )

        val multiTokenOptionalMethodParameter = stringAnalyzer1.MultiTokenExclusiveXor(
            multiTokenOptionalMethodParameter1, multiTokenOptionalMethodParameter2
        )

        tokenOpenParenthesizedMethodParameter.addToken(multiTokenOptionalMethodParameter)
        multiTokenOptionalMethodParameter.addToken(tokenCloseParenthesizedMethodParameter)
        //multiTokenOptionalMethodParameter2.addToken(tokenCloseParenthesizedMethodParameter)//???
        val tokenOpenBracketMethod = stringAnalyzer1.TokenOpenBracket()
        tokenCloseParenthesizedMethodParameter.addToken(tokenOpenBracketMethod)


        class ActionTokenOpenParenthesizedMethodParameter(token: StringAnalyzer3.Token?) : Action(token) {
            override fun action(): Boolean {
                if (tokenMemberMethodType.name != null && tokenMemberMethodName.name != null) {
                    stringAnalyzer1.construct.currentMethod.ofClass = Variable()
                    stringAnalyzer1.construct.currentMethod.ofClass.classStr = tokenMemberMethodType.name
                    stringAnalyzer1.construct.currentMethod.name = tokenMemberMethodName.name
                }
                return true
            }
        }
        ActionTokenOpenParenthesizedMethodParameter(tokenOpenParenthesizedMethodParameter)
        class ActionParamType(token: StringAnalyzer3.Token?) : Action(token) {
            override fun action(): Boolean {
                println("ActionParamType1: " + (token as TokenQualifiedName).name)
                val name = (token as TokenQualifiedName).name
                if (name != null) {
                    val parameterList = stringAnalyzer1.construct.currentMethod.parameterList
                    parameterList.add(Variable())
                    if (parameterList.size > 0) {
                        parameterList[parameterList.size - 1].classStr = name
                    }
                }
                return true
            }
        }

        class ActionParamName(token: StringAnalyzer3.Token?) : Action(token) {
            override fun action(): Boolean {
                println("ActionParamName1: " + (token as TokenName).name)
                val name = (token as TokenName).name
                if (name != null) {
                    val parameterList = stringAnalyzer1.construct.currentMethod.parameterList
                    parameterList[parameterList.size - 1].name = name
                }
                return true
            }
        }

        tokenQualifiedNameMethodParameter1.setAction(ActionParamType(tokenQualifiedNameMethodParameter1))
        tokenQualifiedNameMethodParameter2.setAction(ActionParamType(tokenQualifiedNameMethodParameter2))
        tokenNameMethodParameter1.setAction(ActionParamName(tokenNameMethodParameter1))
        tokenNameMethodParameter2.setAction(ActionParamName(tokenNameMethodParameter2))

        class ActionVarType(token: StringAnalyzer3.Token?) : Action(token) {
            override fun action(): Boolean {
                if (token.isSuccessful) {
                    val name = (token as StringAnalyzer3.TokenQualifiedName).name
                    println("tokenMemberVarType: $name")
                    if (name != null) {
                        val parameterList = stringAnalyzer1.construct.currentClass.variableList
                        val variable = parameterList.get(parameterList.size - 1)// Variable()
                        //parameterList.add(variable)
                        variable.classStr = name
                    }
                }
                return true
            }
        }

        class ActionVarName(token: StringAnalyzer3.Token?) : Action(token) {
            override fun action(): Boolean {
                if (token.isSuccessful) {
                    val name = (token as StringAnalyzer3.TokenName).name
                    println("tokenMemberVarName: $name")
                    if (name != null) {
                        val parameterList = stringAnalyzer1.construct.currentClass.variableList
                        val variable = Variable()
                        parameterList.add(variable)
                        variable.name = name

                    }
                }
                return true
            }
        }
        ActionVarType(tokenMemberVarType1)
        ActionVarType(tokenMemberVarType2)
        ActionVarName(tokenMemberVarName1)
        ActionVarName(tokenMemberVarName2)

        tokenMemberMethodType.addToken(tokenMemberMethodName)
        tokenMemberMethodName.addToken(tokenOpenParenthesizedMethodParameter)

        val tokenIf = stringAnalyzer1.TokenString("if")
        val tokenElse = stringAnalyzer1.TokenString("else")

        val tokenWhile = stringAnalyzer1.TokenString("while")
        val tokenDo = stringAnalyzer1.TokenString("do")

        val instruction = stringAnalyzer1.SingleTokenExclusiveXor(
            tokenIf, // Test keywords first.
            tokenDo,
            tokenWhile,
            tokenMemberMethodVarType1,
            tokenMemberMethodVarType2,
            tokenMemberMethodExpression3,
            tokenMemberMethodVarName4
        )
        // Instructions
        val tokenMultiMembersInstructions = stringAnalyzer1.MultiTokenExclusiveXor(instruction)
        val tokenSingleInstructionIf = instruction//.copy(instruction)
        val tokenSingleInstructionElse = instruction//.copy(instruction)


        // End of Instructions

        val tokenCloseBracketMethod = stringAnalyzer1.TokenCloseBracket()
        tokenOpenBracketMethod.addToken(tokenMultiMembersInstructions)
        tokenMultiMembersInstructions.addToken(tokenCloseBracketMethod)

        // Instructions' flow controls (if-else, while, do while, for-i, for-:, switch)

        // Block without controls
        val instructionBlockOpenBracket = stringAnalyzer1.TokenString("{")
        val instructionBlockCloseBracket = stringAnalyzer1.TokenString("}")
        val instructionBlock = stringAnalyzer1.SingleTokenMandatory(
            instructionBlockOpenBracket,
            tokenMultiMembersInstructions,
            instructionBlockCloseBracket
        )
        // Logical expression
        val logicalExpressionExpression = stringAnalyzer1.TokenLogicalExpression()
        val logicalExpression = stringAnalyzer1.SingleTokenMandatory(logicalExpressionExpression)
        val logicalExpressionExpressionElse = stringAnalyzer1.TokenLogicalExpression()

        // if flow control

        val instructionsIf = stringAnalyzer1.SingleTokenExclusiveXor(
            tokenSingleInstructionIf, instructionBlock
        )
        val instructionsElse = stringAnalyzer1.SingleTokenExclusiveXor(
            tokenSingleInstructionElse, instructionBlock
        )

        tokenIf.addToken(logicalExpression)
        logicalExpression.addToken(instructionsIf)
        instructionsIf.addToken(
            stringAnalyzer1.SingleTokenOptional(
                stringAnalyzer1.SingleTokenMandatory(
                    tokenElse
                )
            )
        )

        class ActionExpressionType(token: StringAnalyzer3.Token) : Action(token) {
            override fun action(): Boolean {
                if (token.isSuccessful) {
                    if (token == tokenMethodSemiColonVar1) {
                        if (tokenMemberMethodVarType1.name != null) {
                            val name = tokenMemberMethodVarType1.name
                            //tokenMemberMethodVarType1.name = null
                            val instructions = stringAnalyzer1.construct.currentMethod.instructions
                            instructions.instructionList.add(Instruction())
                            val get = instructions.instructionList.get(instructions.instructionList.size - 1)
                            (get as Instruction).setType(name)
                            tokenMemberMethodVarType1.name = null
                        }
                        if (tokenMemberMethodVarName1.name != null) {
                            val name = tokenMemberMethodVarName1.name
                            //tokenMemberMethodVarName1.name = null
                            val instructions = stringAnalyzer1.construct.currentMethod.instructions
                            //instructions.add(Instruction())
                            val get =
                                instructions.instructionList.get(instructions.instructionList.size - 1) as Instruction
                            get.setName(name)
                            get.expression.leftHand = name
                            tokenMemberMethodVarName1.name = null
                        }
                    }
                    if (token == tokenMethodSemiColonVar2) {
                        if (tokenMemberMethodVarType2.name != null) {
                            val name = tokenMemberMethodVarType2.name
                            val instructions = stringAnalyzer1.construct.currentMethod.instructions
                            instructions.instructionList.add(Instruction())
                            val get = instructions.instructionList.get(instructions.instructionList.size - 1)
                            (get as Instruction).setType(name)
                            tokenMemberMethodVarType2.name = null
                        }
                        if (tokenMemberMethodVarName2.name != null) {
                            var name = tokenMemberMethodVarName2.name
                            val instructions = stringAnalyzer1.construct.currentMethod.instructions
                            //instructions.add(Instruction())
                            val get = instructions.instructionList.get(instructions.instructionList.size - 1)
                            (get as Instruction).setName(name)
                            get.expression.leftHand = name
                            tokenMemberMethodVarName2.name = null
                        }
                    }
                    if (token == tokenMethodSemiColonVar4) {
                        if (tokenMemberMethodVarName4.name != null) {
                            var name = tokenMemberMethodVarName4.name
                            //tokenMemberMethodVarName2.name = null
                            val instructions = stringAnalyzer1.construct.currentMethod.instructions
                            instructions.instructionList.add(Instruction())
                            val get = instructions.instructionList.get(instructions.instructionList.size - 1)
                            (get as Instruction).setName(name)
                            get.expression.leftHand = name
                            tokenMemberMethodVarName4.name = null
                        }
                    }
                    if (token == tokenMethodSemiColonVar2) {
                        if (tokenMemberMethodExpression2.expression != null) {
                            var name = tokenMemberMethodExpression2.expression
                            //tokenMemberMethodVarName2.name = null
                            val instructions = stringAnalyzer1.construct.currentMethod.instructions
                            //instructions.add(Instruction())
                            val get = instructions.instructionList.get(instructions.instructionList.size - 1)
                            (get as Instruction).getExpression().expression = name
                            tokenMemberMethodExpression2.expression = null
                        }
                    }
                    if (token == tokenMethodSemiColonVar3) {
                        if (tokenMemberMethodExpression3.expression != null) {
                            var name = tokenMemberMethodExpression3.expression
                            //tokenMemberMethodVarName2.name = null
                            val instructions = stringAnalyzer1.construct.currentMethod.instructions
                            instructions.instructionList.add(Instruction())
                            val get = instructions.instructionList.get(instructions.instructionList.size - 1)
                            (get as Instruction).getExpression().expression = name
                            tokenMemberMethodExpression3.expression = null
                        }
                    }
                    if (token == tokenMethodSemiColonVar4) {
                        if (tokenMemberMethodExpression4.expression != null) {
                            var name = tokenMemberMethodExpression4.expression
                            val instructions = stringAnalyzer1.construct.currentMethod.instructions
                            instructions.instructionList.add(Instruction())
                            val get = instructions.instructionList.get(instructions.instructionList.size - 1)
                            (get as Instruction).getExpression().expression = name
                            tokenMemberMethodExpression4.expression = null
                        }
                    }
                }
                return true
            }
        }

        ActionExpressionType(tokenMethodSemiColonVar1)
        ActionExpressionType(tokenMethodSemiColonVar2)
        ActionExpressionType(tokenMethodSemiColonVar3)
        ActionExpressionType(tokenMethodSemiColonVar4)

        tokenElse//.addToken(logicalExpressionElse)
        tokenElse.addToken(instructionsElse)///??? Imbriquées ?
        class ActionPushMethod(token: StringAnalyzer3.Token?) : Action(token) {
            override fun action(): Boolean {
                if (tokenMemberMethodName.name != null && !tokenMemberMethodName.name.isEmpty() &&
                    tokenMemberMethodType.name != null && !tokenMemberMethodType.name.isEmpty()
                ) {
                    val methodList = stringAnalyzer1.construct.currentClass.methodList
                    methodList.add(stringAnalyzer1.construct.currentMethod)
                    stringAnalyzer1.construct.currentMethod.name = tokenMemberMethodName.name
                    stringAnalyzer1.construct.currentMethod.ofClass.classStr = tokenMemberMethodType.name
                    stringAnalyzer1.construct.currentMethod.ofClass.name = tokenMemberMethodType.name

                    stringAnalyzer1.construct.currentMethod = Method()
                }
                return true
            }
        }
        ActionPushMethod(tokenCloseBracketMethod)

        tokenPackage.addToken(tokenPackageName)
        tokenPackageName.addToken(tokenPackageSemicolon)
        val tokenPackageOptional =
            stringAnalyzer1.SingleTokenOptional(
                tokenPackage
            )
        tokenImport.addToken(tokenImportName)
        tokenImportName.addToken(tokenImportSemicolon)

        val multiTokenMandatoryImport =
            stringAnalyzer1.MultiTokenOptional(
                tokenImport
            )

        tokenPackageOptional.addToken(multiTokenMandatoryImport)
        multiTokenMandatoryImport.addToken(tokenClass)
        tokenClass.addToken(tokenClassName)
        tokenClassName.addToken(tokenOpenBracket)
        val multiTokenOptional = stringAnalyzer1.MultiTokenExclusiveXor(
            tokenMemberMethodType, tokenMemberVar//, tokenCloseBracketClass
        )
        tokenOpenBracket.addToken(multiTokenOptional)
        multiTokenOptional.addToken(tokenCloseBracketClass)
        class ActionCloseBracketClass : Action(tokenCloseBracketClass) {
            override fun action(): Boolean {
                if (tokenCloseBracketClass.isSuccessful) {
                    stringAnalyzer1.construct.classes.add(stringAnalyzer1.construct.currentClass)
                    stringAnalyzer1.construct.currentClass = Class()
                }
                return true
            }
        }

        class ActionIf(token: StringAnalyzer3.Token) : Action(token) {
            override fun action(): Boolean {
                if (this.token == tokenIf) {
                    if (token.isSuccessful) {
                        val value: ControlledInstructions.If =
                            ControlledInstructions.If(logicalExpressionExpression.expression)
                        stringAnalyzer1.construct.pushInstructions(value)
                    }
                }
                return true
            }
        }

        class ActionElse(token: StringAnalyzer3.Token) : Action(token) {
            override fun action(): Boolean {
                if (this.token == tokenElse) {
                    if (token.isSuccessful) {
                        val popInstructionsElse = stringAnalyzer1.construct.currentInstructions
                        if (popInstructionsElse is ControlledInstructions.If) {
                            popInstructionsElse.instructionsElse.instructionList.add(
                                (popInstructionsElse as ControlledInstructions.If).instructionsElse
                            )
                        }
                    }
                }
                return true
            }
        }
        ActionIf(tokenIf)
        ActionElse(tokenElse)
        println("TestStringAnalyzer3")
        //println("parse = " + parse + "/" + input.length)
        //println("isSuccessful = " + token.isSuccessful + " tokenClass : " + tokenClassName.isSuccessful)
        println("Name : " + tokenPackageName.name)
        println("Method name : " + tokenMemberMethodName.name)
        println("if token : " + tokenIf.isSuccessful)
        println("if token expression logique : " + logicalExpressionExpression.expression)
        println(stringAnalyzer1.construct.toLangStringJava(isDebug))

        return tokenPackageOptional
    }

    @Test
    fun testJavaClass1() {
        val stringAnalyzer1 = StringAnalyzer3()
        val javaToken = getJavaToken(stringAnalyzer1)
        val token = javaToken
        val input = readString("resources/text_parser/Number1.java.java_code")
        println(input)
        var parse = -1
        try {
            parse = stringAnalyzer1.parse(token, input)
//     parse = stringAnalyzer1.mPosition
        } catch (ex: RuntimeException) {
            ex.printStackTrace()
            if (parse >= input.length) {
                Assert.assertTrue(true)
            } else {
                ex.printStackTrace()
                Assert.assertTrue(false)
            }
        }
        println(stringAnalyzer1.construct.toLangStringJava(isDebug))
        if (parse >= input.length || input.substring(parse).isBlank())
            println("")
        else
            println(input.substring(stringAnalyzer1.mPosition))

        if (parse >= input.length || input.substring(parse).isBlank()) {
            Assert.assertTrue(true)
            return
        } else {
            Assert.assertTrue(false)
        }

    }

    @Test
    fun testJavaClass2() {
        val stringAnalyzer1 = StringAnalyzer3()
        val javaToken = getJavaToken(stringAnalyzer1)
        val token = javaToken
        val input = readString("resources/text_parser/Number2.java.java_code")
        println(input)
        var parse = -1
        try {
            parse = stringAnalyzer1.parse(token, input)
//     parse = stringAnalyzer1.mPosition
        } catch (ex: RuntimeException) {
            ex.printStackTrace()
            if (parse >= input.length) {
                Assert.assertTrue(true)
            } else {
                ex.printStackTrace()
                Assert.assertTrue(false)
            }
        }
        println(stringAnalyzer1.construct.toLangStringJava(isDebug))
        if (parse >= input.length || input.substring(parse).isBlank())
            println("")
        else
            println(input.substring(stringAnalyzer1.mPosition))

        if (parse >= input.length || input.substring(parse).isBlank()) {
            Assert.assertTrue(true)
            return
        } else {
            Assert.assertTrue(false)
        }

    }

    @Test
    fun testLogicalExpression() {
        val stringAnalyzer1 = StringAnalyzer3()
        val tokenIf = stringAnalyzer1.TokenString("if")
        val tokenLogicalExpression1 = stringAnalyzer1.TokenLogicalExpression()
        val tokenLogicalExpression2 = stringAnalyzer1.TokenLogicalExpression()
        val tokenInstructionSemiColon1 = stringAnalyzer1.TokenString(";")
        val tokenInstructionSemiColon2 = stringAnalyzer1.TokenString(";")
        val tokenExpression1 = stringAnalyzer1.TokenExpression1()
        val tokenExpression2 = stringAnalyzer1.TokenExpression1()
        val tokenELse = stringAnalyzer1.TokenString("else")

        tokenIf.addToken(tokenLogicalExpression1)
        tokenLogicalExpression1.addToken(tokenExpression1)
        tokenExpression1.addToken(tokenInstructionSemiColon1)
        tokenInstructionSemiColon1.addToken(tokenELse)
        tokenELse.addToken(tokenExpression2)
        tokenExpression2.addToken(tokenInstructionSemiColon2)

        println(tokenIf)
        println(tokenLogicalExpression1)
        println(tokenLogicalExpression2)
        println(tokenExpression1)
        println(tokenExpression2)
        println(tokenELse)

        val input = "if(a) b=4; else b=2;"

        val parse = tokenIf.parse(input, 0)

        println(parse)
        println(stringAnalyzer1.mPosition)

        val result = tokenIf.isSuccessful && (parse >= input.length || input.substring(parse).trim().isBlank())
        if (result)
            Assert.assertTrue(true)
        else
            Assert.assertFalse(false)
    }
}