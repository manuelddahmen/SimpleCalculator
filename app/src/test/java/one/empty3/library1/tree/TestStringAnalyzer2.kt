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

import one.empty3.library1.tree.StringAnalyzer3.Token
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
class TestStringAnalyzer2 {
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
        val stringAnalyzer3 = StringAnalyzer3()
        val token = stringAnalyzer3.TokenPackage()
        val input = "package"
        var parse = -1
        try {
            parse = stringAnalyzer3.parse(token, input)
//            parse = stringAnalyzer3.mPosition
        } catch (ex: RuntimeException) {
            if (token.isSuccessful) {
                if (stringAnalyzer3.mPosition >= input.length) {
                    Assert.assertTrue(true)

                    println(stringAnalyzer3.construct)
                    return
                }
            }

            Assert.assertTrue(false)
        }

        println("parse = " + stringAnalyzer3.mPosition + "/" + input.length)
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
        val stringAnalyzer3 = StringAnalyzer3()
        val tokenPackage = stringAnalyzer3.TokenPackage()
        val tokenPackageName = stringAnalyzer3.TokenQualifiedName()
        val tokenPackageSemicolon = stringAnalyzer3.TokenSemiColon()

        tokenPackage.addToken(tokenPackageName)
        tokenPackageName.addToken(tokenPackageSemicolon)

        val token = stringAnalyzer3.SingleTokenOptional(tokenPackage)


        val input = "package com.example;"
        var parse = -1
        try {
            parse = stringAnalyzer3.parse(token, input)
//            parse = stringAnalyzer3.mPosition
        } catch (ex: RuntimeException) {
            if (token.isSuccessful) {
                if (parse >= input.length) {
                    Assert.assertTrue(true)

                    println(stringAnalyzer3.construct)
                    return
                }
            }

            Assert.assertTrue(false)
        }

        stringAnalyzer3.construct.packageName = tokenPackageName.name


        println("TestStringAnalyzer3.testStringAnalyzerPackage")
        println("parse = " + parse + "/" + input.length)
        println("isSuccessful = " + token.isSuccessful + " tokenSemicolon : " + tokenPackageSemicolon.isSuccessful)
        println("Name : " + tokenPackageName.name)

        if (token.isSuccessful) {
            if (parse >= input.length) {
                Assert.assertTrue(true)

                println(stringAnalyzer3.construct)
                return
            }
        }

        Assert.assertTrue(false)


    }

    @Test
    fun testStringAnalyzerPackageAndClassDeclaration() {
        val stringAnalyzer3 = StringAnalyzer3()
        val tokenPackage = stringAnalyzer3.TokenPackage()
        val tokenPackageName = stringAnalyzer3.TokenQualifiedName()
        val tokenPackageSemicolon = stringAnalyzer3.TokenSemiColon()
        val tokenClass = stringAnalyzer3.TokenClassKeyword()
        val tokenClassName = stringAnalyzer3.TokenName()
        val tokenOpenBracket = stringAnalyzer3.TokenOpenBracket()
        val tokenCloseBracket = stringAnalyzer3.TokenCloseBracket()


        tokenPackage.addToken(tokenPackageName)
        tokenPackageName.addToken(tokenPackageSemicolon)
        tokenClass.addToken(tokenClassName)
        tokenClassName.addToken(tokenOpenBracket)
        tokenOpenBracket.addToken(tokenCloseBracket)

        val token = stringAnalyzer3.SingleTokenOptional(tokenPackage)

        token.addToken(tokenClass)

        val input = "package com.example;\nclass Graph {\n}"
        var parse = -1
        try {
            parse = stringAnalyzer3.parse(token, input)
//            parse = stringAnalyzer3.mPosition
        } catch (ex: RuntimeException) {
            if (token.isSuccessful) {
                if (parse >= input.length) {
                    Assert.assertTrue(true)

                    println(stringAnalyzer3.construct)
                    return
                }
            }

            Assert.assertTrue(false)
        }

        stringAnalyzer3.construct.packageName = tokenPackageName.name
        stringAnalyzer3.construct.currentClass.name = tokenClassName.name


        println("TestStringAnalyzer3.testStringAnalyzerPackage")
        println("parse = " + parse + "/" + input.length)
        println("isSuccessful = " + token.isSuccessful + " tokenSemicolon : " + tokenPackageSemicolon.isSuccessful)
        println("Name : " + tokenPackageName.name)

        if (token.isSuccessful) {
            if (parse >= input.length) {
                Assert.assertTrue(true)

                println(stringAnalyzer3.construct)
                return
            }
        }

        Assert.assertTrue(false)


    }

    fun getJavaToken(stringAnalyzer3: StringAnalyzer3): StringAnalyzer3.Token {
        val tokenPackage = stringAnalyzer3.TokenPackage()
        val tokenPackageName = stringAnalyzer3.TokenQualifiedName()
        val tokenPackageSemicolon = stringAnalyzer3.TokenSemiColon()
        val tokenClass: StringAnalyzer3.Token = stringAnalyzer3.TokenClassKeyword()


        class ActionPackageName : Action(tokenPackageName) {
            public override fun action(): Boolean {
                if (!tokenPackageName.name.isEmpty()) {
                    stringAnalyzer3.construct.currentClass.setPackageName(tokenPackageName.name)
                    stringAnalyzer3.construct.packageName = tokenPackageName.name
                }
                return true
            }
        }
        tokenPackageName.setAction(ActionPackageName())

        val tokenImport = stringAnalyzer3.TokenString("import")
        val tokenImportName = stringAnalyzer3.TokenQualifiedName()
        val tokenImportSemicolon = stringAnalyzer3.TokenSemiColon()


        class ActionClassKeyword : Action(tokenClass) {
            public override fun action(): Boolean {
                stringAnalyzer3.construct.classes.add(stringAnalyzer3.construct.currentClass)
                stringAnalyzer3.construct.currentClass.setPackageName(stringAnalyzer3.construct.packageName)
                return true
            }
        }
        tokenClass.setAction(ActionClassKeyword())

        val tokenClassName = stringAnalyzer3.TokenName()

        class ActionClassname : Action(tokenClassName) {
            override fun action(): Boolean {
                if (tokenClassName.name != null) {
                    stringAnalyzer3.construct.currentClass.name = tokenClassName.name
                }
                return true
            }
        }
        tokenClass.setAction(ActionClassname())
        val tokenOpenBracket = stringAnalyzer3.TokenOpenBracket()
        val tokenCloseBracketClass = stringAnalyzer3.TokenCloseBracket()

        // Variables members declarations
        val tokenMemberVarType1 = stringAnalyzer3.TokenQualifiedName()
        val tokenMemberVarName1 = stringAnalyzer3.TokenName()
        val tokenMemberVarEquals1 = stringAnalyzer3.TokenEquals()
        val tokenMemberExpression1 = stringAnalyzer3.TokenExpression1()
        val tokenMemberVarSemiColon1 = stringAnalyzer3.TokenSemiColon()

        val tokenMemberVarType2 = stringAnalyzer3.TokenQualifiedName()
        val tokenMemberVarName2 = stringAnalyzer3.TokenName()
        val tokenMemberVarSemiColon2 = stringAnalyzer3.TokenSemiColon()

        tokenMemberVarType1.addToken(tokenMemberVarName1)
        tokenMemberVarName1.addToken(tokenMemberVarEquals1)
        tokenMemberVarEquals1.addToken(tokenMemberExpression1)
        tokenMemberExpression1.addToken(tokenMemberVarSemiColon1)

        tokenMemberVarType2.addToken(tokenMemberVarName2)
        tokenMemberVarName2.addToken(tokenMemberVarSemiColon2)

        // Method's instructions
        val tokenMemberMethodVarType1 = stringAnalyzer3.TokenQualifiedName()
        val tokenMemberMethodVarName1 = stringAnalyzer3.TokenName()
        val tokenMethodSemiColonVar1 = stringAnalyzer3.TokenSemiColon()

        val tokenMemberMethodVarType2 = stringAnalyzer3.TokenQualifiedName()
        val tokenMemberMethodVarName2 = stringAnalyzer3.TokenName()
        val tokenMemberMethodVarEquals2 = stringAnalyzer3.TokenEquals()
        val tokenMemberMethodExpression2 = stringAnalyzer3.TokenExpression1()
        val tokenMethodSemiColonVar2 = stringAnalyzer3.TokenSemiColon()

        val tokenMemberMethodExpression3 = stringAnalyzer3.TokenExpression1()
        val tokenMethodSemiColonVar3 = stringAnalyzer3.TokenSemiColon()

        val tokenMemberMethodVarName4 = stringAnalyzer3.TokenName()
        val tokenMemberMethodVarEquals4 = stringAnalyzer3.TokenEquals()
        val tokenMemberMethodExpression4 = stringAnalyzer3.TokenExpression1()
        val tokenMethodSemiColonVar4 = stringAnalyzer3.TokenSemiColon()
        /*
                class ActionTokenSemiColonInstruction(token: StringAnalyzer3.Token?) : Action(token) {
                    override fun action(): Boolean {
                        val instructions = stringAnalyzer3.construct.currentInstructions
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

                }
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

        val tokenMemberVar = stringAnalyzer3.SingleTokenExclusiveXor(
            tokenMemberVarType1, tokenMemberVarType2
        )

        val tokenMemberMethodType = stringAnalyzer3.TokenQualifiedName()
        val tokenMemberMethodName = stringAnalyzer3.TokenName()

        // Arguments' list
        val tokenOpenParenthesizedMethodParameter = stringAnalyzer3.TokenOpenParenthesized()
        val tokenComaMethodParameter1 = stringAnalyzer3.TokenComa()
        val tokenQualifiedNameMethodParameter1 = stringAnalyzer3.TokenQualifiedName()
        val tokenNameMethodParameter1 = stringAnalyzer3.TokenName()

        //val tokenComaMethodParameter2 = stringAnalyzer3.TokenComa()
        val tokenQualifiedNameMethodParameter2 = stringAnalyzer3.TokenQualifiedName()
        val tokenNameMethodParameter2 = stringAnalyzer3.TokenName()

        val tokenCloseParenthesizedMethodParameter = stringAnalyzer3.TokenCloseParenthesized()

        val multiTokenOptionalMethodParameter2 =
            stringAnalyzer3.MultiTokenMandatory(
                tokenComaMethodParameter1, tokenQualifiedNameMethodParameter1, tokenNameMethodParameter1
            )
        val multiTokenOptionalMethodParameter1 =
            stringAnalyzer3.MultiTokenMandatory(
                tokenQualifiedNameMethodParameter2, tokenNameMethodParameter2
            )

        val multiTokenOptionalMethodParameter = stringAnalyzer3.MultiTokenExclusiveXor(
            multiTokenOptionalMethodParameter1, multiTokenOptionalMethodParameter2
        )

        tokenOpenParenthesizedMethodParameter.addToken(multiTokenOptionalMethodParameter)
        multiTokenOptionalMethodParameter.addToken(tokenCloseParenthesizedMethodParameter)
        //multiTokenOptionalMethodParameter2.addToken(tokenCloseParenthesizedMethodParameter)//???
        val tokenOpenBracketMethod = stringAnalyzer3.TokenOpenBracket()
        tokenCloseParenthesizedMethodParameter.addToken(tokenOpenBracketMethod)


        class ActionTokenOpenParenthesizedMethodParameter(token: StringAnalyzer3.Token?) : Action(token) {
            init {
                this.setOn(Action.ON_NEXT_TOKEN_CALL)
            }

            override fun action(): Boolean {
                println("ActionTokenOpenParenthesizedMethodParameter")
                if (tokenMemberMethodType.name != null && tokenMemberMethodName.name != null) {
                    stringAnalyzer3.construct.currentMethod.ofClass = Variable()
                    stringAnalyzer3.construct.currentMethod.ofClass.classStr = tokenMemberMethodType.name
                    stringAnalyzer3.construct.currentMethod.name = tokenMemberMethodName.name

                    if (stringAnalyzer3.construct.currentMethod.instructions.instructionList == null) {
                        stringAnalyzer3.construct.currentMethod.instructions = InstructionBlock()
                    }
                    stringAnalyzer3.construct.pushInstructions(stringAnalyzer3.construct.currentMethod.instructions)
                }
                return true
            }
        }

        class ActionParamType(token: StringAnalyzer3.Token?) : Action(token) {
            override fun action(): Boolean {
                if (token.isSuccessful) {
                    println("ActionParamType1: " + (token as TokenQualifiedName).name)
                    val name = (token as TokenQualifiedName).name
                    if (name != null) {
                        val parameterList = stringAnalyzer3.construct.currentMethod.parameterList
                        parameterList.add(Variable())
                        if (parameterList.size > 0) {
                            parameterList[parameterList.size - 1].classStr = name
                            (token as TokenQualifiedName).name = null
                        }
                    }
                }
                return true
            }
        }

        class ActionParamName(token: StringAnalyzer3.Token?) : Action(token) {
            override fun action(): Boolean {
                if (token.isSuccessful) {
                    println("ActionParamName1: " + (token as TokenName).name)
                    val name = (token as TokenName).name
                    if (name != null) {
                        val parameterList = stringAnalyzer3.construct.currentMethod.parameterList
                        parameterList[parameterList.size - 1].name = name
                        (token as TokenName).name = null
                    }
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

                    var name: String? = null
                    if (token == tokenMemberVarSemiColon1) {
                        name = tokenMemberVarType1.name
                    } else if (token == tokenMemberVarSemiColon2) {
                        name = tokenMemberVarType2.name
                    }

                    println("tokenMemberVarType: $name")
                    if (name != null) {
                        val parameterList = stringAnalyzer3.construct.currentClass.variableList
                        val variable = Variable()
                        parameterList.add(variable)
                        variable.classStr = name
                    }
                    name = null
                    if (token == tokenMemberVarSemiColon1) {
                        name = tokenMemberVarName1.name
                    } else if (token == tokenMemberVarSemiColon2) {
                        name = tokenMemberVarName2.name
                    }
                    println("tokenMemberVarName: $name")
                    if (name != null) {
                        val parameterList = stringAnalyzer3.construct.currentClass.variableList
                        val variable = parameterList.get(parameterList.size - 1)// Variable()
                        variable.name = name
                    }
                }
                return true
            }
        }

        ActionVarType(tokenMemberVarSemiColon1)
//        ActionVarName(tokenMemberVarSemiColon1)
        ActionVarType(tokenMemberVarSemiColon2)
//        ActionVarName(tokenMemberVarSemiColon2)

        tokenMemberMethodType.addToken(tokenMemberMethodName)
        tokenMemberMethodName.addToken(tokenOpenParenthesizedMethodParameter)

        val tokenIf = stringAnalyzer3.TokenString("if")
        val tokenElse = stringAnalyzer3.TokenString("else")

        val tokenWhile = stringAnalyzer3.TokenString("while")
        val tokenDo = stringAnalyzer3.TokenString("do")

        val instruction = stringAnalyzer3.SingleTokenExclusiveXor(
            tokenIf, // Test keywords first.
            tokenDo,
            tokenWhile,
            tokenMemberMethodVarType1,
            tokenMemberMethodVarType2,
            tokenMemberMethodExpression3,
            tokenMemberMethodVarName4
        )
        // Instructions
        val tokenMultiMembersInstructions = stringAnalyzer3.MultiTokenExclusiveXor(instruction)
        val tokenSingleInstructionIf = instruction//.copy(instruction)
        val tokenSingleInstructionElse = instruction//.copy(instruction)
        val tokenSingleInstructionDo = instruction//.copy(instruction)
        val tokenSingleInstructionWhile = instruction//.copy(instruction)


        // End of Instructions

        val tokenCloseBracketMethod = stringAnalyzer3.TokenCloseBracket()
        tokenOpenBracketMethod.addToken(tokenMultiMembersInstructions)
        tokenMultiMembersInstructions.addToken(tokenCloseBracketMethod)

        ActionTokenOpenParenthesizedMethodParameter(tokenOpenBracketMethod)

        // Instructions' flow controls (if-else, while, do while, for-i, for-:, switch)

        // Block without controls
        val instructionBlockOpenBracket = stringAnalyzer3.TokenString("{")
        val instructionBlockCloseBracket = stringAnalyzer3.TokenString("}")
        val instructionBlock = stringAnalyzer3.SingleTokenMandatory(
            instructionBlockOpenBracket,
            tokenMultiMembersInstructions,
            instructionBlockCloseBracket
        )
        // Logical expression
        val logicalExpressionExpression = stringAnalyzer3.TokenLogicalExpression()
        val logicalExpression = stringAnalyzer3.SingleTokenMandatory(logicalExpressionExpression)
        val logicalExpressionWhile = stringAnalyzer3.SingleTokenMandatory(logicalExpressionExpression)
        val logicalExpressionDo = stringAnalyzer3.SingleTokenMandatory(logicalExpressionExpression)

        // if flow control

        val instructionsIf = stringAnalyzer3.SingleTokenExclusiveXor(
            tokenSingleInstructionIf, instructionBlock
        )
        val instructionsElse = stringAnalyzer3.SingleTokenExclusiveXor(
            tokenSingleInstructionElse, instructionBlock
        )
        val instructionsWhile = stringAnalyzer3.SingleTokenExclusiveXor(
            tokenSingleInstructionWhile, instructionBlock
        )
        val instructionsDo = stringAnalyzer3.SingleTokenExclusiveXor(
            tokenSingleInstructionDo, instructionBlock
        )

        tokenIf.addToken(logicalExpression)
        logicalExpression.addToken(instructionsIf)
        instructionsIf.addToken(
            stringAnalyzer3.SingleTokenOptional(
                stringAnalyzer3.SingleTokenMandatory(
                    tokenElse
                )
            )
        )
        tokenWhile.addToken(logicalExpressionWhile)
        logicalExpressionWhile.addToken(instructionsWhile)
        tokenDo.addToken(logicalExpressionDo)
        logicalExpressionDo.addToken(instructionsWhile)

        class ActionExpressionType(token: StringAnalyzer3.Token) : Action(token) {
            override fun action(): Boolean {
                if (token.isSuccessful) {
                    if (stringAnalyzer3.construct.currentInstructions != null) {
                        val instructions = stringAnalyzer3.construct.currentInstructions.instructionList
                        if (token == tokenMethodSemiColonVar1) {
                            if (tokenMemberMethodVarType1.name != null) {
                                val name = tokenMemberMethodVarType1.name
                                //tokenMemberMethodVarType1.name = null
                                instructions.add(Instruction())
                                val get = instructions.get(instructions.size - 1)
                                (get as Instruction).setType(name)
                                tokenMemberMethodVarType1.name = null
                            }
                            if (tokenMemberMethodVarName1.name != null) {
                                val name = tokenMemberMethodVarName1.name
                                val get = instructions.get(instructions.size - 1) as Instruction
                                get.setName(name)
                                get.expression.leftHand = name
                                tokenMemberMethodVarName1.name = null
                            }
                        }
                        if (token == tokenMethodSemiColonVar2) {
                            if (tokenMemberMethodVarType2.name != null) {
                                val name = tokenMemberMethodVarType2.name
                                instructions.add(Instruction())
                                val get = instructions.get(instructions.size - 1)
                                (get as Instruction).setType(name)
                                tokenMemberMethodVarType2.name = null
                            }
                            if (tokenMemberMethodVarName2.name != null) {
                                var name = tokenMemberMethodVarName2.name
                                //instructions.add(Instruction())
                                val get = instructions.get(instructions.size - 1)
                                (get as Instruction).setName(name)
                                get.expression.leftHand = name
                                tokenMemberMethodVarName2.name = null
                            }
                        }
                        if (token == tokenMethodSemiColonVar4) {
                            if (tokenMemberMethodVarName4.name != null) {
                                var name = tokenMemberMethodVarName4.name
                                //tokenMemberMethodVarName2.name = null
                                instructions.add(Instruction())
                                val get = instructions.get(instructions.size - 1)
                                (get as Instruction).setName(name)
                                get.expression.leftHand = name
                                tokenMemberMethodVarName4.name = null
                            }
                        }
                        if (token == tokenMethodSemiColonVar2) {
                            if (tokenMemberMethodExpression2.expression != null) {
                                var name = tokenMemberMethodExpression2.expression
                                val get = instructions.get(instructions.size - 1)
                                (get as Instruction).getExpression().expression = name
                                tokenMemberMethodExpression2.expression = null
                            }
                        }
                        if (token == tokenMethodSemiColonVar3) {
                            if (tokenMemberMethodExpression3.expression != null) {
                                var name = tokenMemberMethodExpression3.expression
                                instructions.add(Instruction())
                                val get = instructions.get(instructions.size - 1)
                                (get as Instruction).getExpression().expression = name
                                tokenMemberMethodExpression3.expression = null
                            }
                        }
                        if (token == tokenMethodSemiColonVar4) {
                            if (tokenMemberMethodExpression4.expression != null) {
                                var name = tokenMemberMethodExpression4.expression
                                val get = instructions.get(instructions.size - 1)
                                (get as Instruction).getExpression().expression = name
                                tokenMemberMethodExpression4.expression = null
                            }
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
        tokenElse.addToken(instructionsElse)///??? Imbriquées ?
        class ActionPushMethod(token: StringAnalyzer3.Token?) : Action(token) {
            override fun action(): Boolean {
                if (tokenMemberMethodName.name != null && !tokenMemberMethodName.name.isEmpty() &&
                    tokenMemberMethodType.name != null && !tokenMemberMethodType.name.isEmpty()
                ) {
                    val methodList = stringAnalyzer3.construct.currentClass.methodList
                    methodList.add(stringAnalyzer3.construct.currentMethod)
                    stringAnalyzer3.construct.currentMethod.name = tokenMemberMethodName.name
                    stringAnalyzer3.construct.currentMethod.ofClass.classStr = tokenMemberMethodType.name
                    stringAnalyzer3.construct.currentMethod.ofClass.name = tokenMemberMethodType.name
                    stringAnalyzer3.construct.popInstructions()
                    stringAnalyzer3.construct.currentMethod = Method()
                }
                return true
            }
        }
        ActionPushMethod(tokenCloseBracketMethod)

        class ActionPopContext(token: StringAnalyzer3.Token) : Action(token) {
            override fun action(): Boolean {
                //val popInstructions = stringAnalyzer3.construct.popInstructions()
                //stringAnalyzer3.construct.currentInstructions.instructionList.add(popInstructions)
                return true
            }
        }

        ActionPopContext(instructionsIf)
        ActionPopContext(instructionsElse)
        //ActionPopContext(instructionBlock)
        //ActionPopContext(instructionBlockCloseBracket)

        tokenPackage.addToken(tokenPackageName)
        tokenPackageName.addToken(tokenPackageSemicolon)
        val tokenPackageOptional =
            stringAnalyzer3.SingleTokenOptional(
                tokenPackage
            )
        tokenImport.addToken(tokenImportName)
        tokenImportName.addToken(tokenImportSemicolon)

        val multiTokenMandatoryImport =
            stringAnalyzer3.MultiTokenOptional(
                tokenImport
            )

        tokenPackageOptional.addToken(multiTokenMandatoryImport)
        multiTokenMandatoryImport.addToken(tokenClass)
        tokenClass.addToken(tokenClassName)
        tokenClassName.addToken(tokenOpenBracket)
        val multiTokenOptional = stringAnalyzer3.MultiTokenExclusiveXor(
            tokenMemberMethodType, tokenMemberVar//, tokenCloseBracketClass
        )
        tokenOpenBracket.addToken(multiTokenOptional)
        multiTokenOptional.addToken(tokenCloseBracketClass)
        class ActionCloseBracketClass : Action(tokenCloseBracketClass) {
            override fun action(): Boolean {
                if (tokenCloseBracketClass.isSuccessful) {
                    stringAnalyzer3.construct.classes.add(stringAnalyzer3.construct.currentClass)
                    stringAnalyzer3.construct.currentClass = Class()
                }
                return true
            }
        }

        class ActionIf(token: StringAnalyzer3.Token) : Action(token) {
            init {
                //on = ON_RETURNS_TRUE_NEXT_TOKEN
            }

            override fun action(): Boolean {
                //if (this.token == tokenIf) {
                try {
                    //if (token.isSuccessful) {
                    val value: ControlledInstructions.If =
                        ControlledInstructions.If(logicalExpressionExpression.expression)
                    stringAnalyzer3.construct.currentInstructions.instructionList.add(value)
                    stringAnalyzer3.construct.pushInstructions(value)
                    tokenIf.isSuccessful = false
                    //}
                } catch (ex: IndexOutOfBoundsException) {
                    ex.printStackTrace()
                }
                println("TokenIf detected")
                //}
                return true
            }
        }

        class ActionElseInstructions(token: StringAnalyzer3.Token) : Action(token) {
            override fun action(): Boolean {
                try {
                    if (stringAnalyzer3.construct.currentInstructions != null &&
                        stringAnalyzer3.construct.currentInstructions.instructionList.size > 0
                    ) {
                        stringAnalyzer3.construct.popInstructions()
                    }
                } catch (ex: IndexOutOfBoundsException) {
                    ex.printStackTrace()
                }
                println("TokenElse detected")
//                }
                //}
                return true
            }
        }


        class ActionElse(token: StringAnalyzer3.Token) : Action(token) {
            override fun action(): Boolean {
                var instructionList: MutableList<InstructionBlock> =
                    stringAnalyzer3.construct.currentInstructions.instructionList
                stringAnalyzer3.construct.popInstructions()
                instructionList = stringAnalyzer3.construct.currentInstructions.instructionList
                if (instructionList.size > 0) {
                    val instructionIf = instructionList.get(instructionList.size - 1)
                    if (instructionIf is ControlledInstructions.If) {
//                        stringAnalyzer3.construct.popInstructions()
                        stringAnalyzer3.construct.pushInstructions(instructionIf.instructionsElse)
                        println("TokenElse detected -> ${instructionIf.instructionsElse != null}")
                    }
                }
                return true
            }
        }

        // REFACTOR
        class ActionIfInstructions(token: StringAnalyzer3.Token) : Action(token) {
            override fun action(): Boolean {
                var instructionList: MutableList<InstructionBlock> =
                    stringAnalyzer3.construct.currentInstructions.instructionList
                stringAnalyzer3.construct.popInstructions()
                instructionList = stringAnalyzer3.construct.currentInstructions.instructionList
                if (instructionList.size > 0) {
                    val instructionIf = instructionList.get(instructionList.size - 1)
                    if (instructionIf is ControlledInstructions.If) {
//                        stringAnalyzer3.construct.popInstructions()
                        stringAnalyzer3.construct.pushInstructions(instructionIf.instructionsElse)
                        println("TokenElse detected -> ${instructionIf.instructionsElse != null}")
                    }
                }
                return true
            }
        }

        ActionIf(logicalExpressionExpression)
        ActionElse(tokenElse)
        ActionElseInstructions(instructionsElse)

        ActionIfInstructions(
            instructionsIf
        )

        class ActionWhile(token: Token) : Action(token) {
            override fun action(): Boolean {
                //if (this.token == tokenIf) {
                try {
                    //if (token.isSuccessful) {
                    val value: ControlledInstructions.While =
                        ControlledInstructions.While(logicalExpressionExpression.expression)
                    stringAnalyzer3.construct.currentInstructions.instructionList.add(value)
                    stringAnalyzer3.construct.pushInstructions(value)
                    tokenIf.isSuccessful = false
                    //}
                } catch (ex: IndexOutOfBoundsException) {
                    ex.printStackTrace()
                }
                println("TokenWhile detected")
                //}
                return true
            }

        }

        class ActionWhile_End(token: Token) : Action(token) {
            override fun action(): Boolean {
                stringAnalyzer3.construct.popInstructions()
                return true
            }
        }

        class ActionDoWhile_Start
            (token: Token) : Action(token) {
            override fun action(): Boolean {
                //if (this.token == tokenIf) {
                try {
                    //if (token.isSuccessful) {
                    val value: ControlledInstructions.DoWhile =
                        ControlledInstructions.DoWhile(null)
                    stringAnalyzer3.construct.currentInstructions.instructionList.add(value)
                    stringAnalyzer3.construct.pushInstructions(value)
                    tokenIf.isSuccessful = false
                    //}
                } catch (ex: IndexOutOfBoundsException) {
                    ex.printStackTrace()
                }
                println("TokenIf detected")
                //}
                return true
            }
        }

        class ActionDoWhile_End(token: Token) : Action(token) {
            override fun action(): Boolean {
                return super.action()
            }
        }

        //ActionIf(tokenIf)
        //ActionElse(tokenElse)
        //ActionEndIf(instructionsElse)
        //ActionEndElse(instructionsElse)
        println("TestStringAnalyzer3")
        //println("parse = " + parse + "/" + input.length)
        //println("isSuccessful = " + token.isSuccessful + " tokenClass : " + tokenClassName.isSuccessful)
        println("Name : " + tokenPackageName.name)
        println("Method name : " + tokenMemberMethodName.name)
        println("if token : " + tokenIf.isSuccessful)
        println("if token expression logique : " + logicalExpressionExpression.expression)
        println(stringAnalyzer3.construct.toLangStringJava(isDebug))

        return tokenPackageOptional
    }

    @Test
    fun testJavaClass1() {
        val stringAnalyzer3 = StringAnalyzer3()
        val javaToken = getJavaToken(stringAnalyzer3)
        val token = javaToken
        val input = readString("resources/text_parser/Number1.java.java_code")
        println(input)
        var parse = -1
        try {
            parse = stringAnalyzer3.parse(token, input)
//     parse = stringAnalyzer3.mPosition
        } catch (ex: RuntimeException) {
            ex.printStackTrace()
            if (parse >= input.length) {
                Assert.assertTrue(true)
            } else {
                ex.printStackTrace()
                Assert.assertTrue(false)
            }
        }
        println(stringAnalyzer3.construct.toLangStringJava(isDebug))
        if (parse >= input.length || input.substring(parse).isBlank())
            println("")
        else
            println(input.substring(stringAnalyzer3.mPosition))

        if (parse >= input.length || input.substring(parse).isBlank()) {
            Assert.assertTrue(true)
            return
        } else {
            Assert.assertTrue(false)
        }

    }

    @Test
    fun testJavaClass2() {
        val stringAnalyzer3 = StringAnalyzer3()
        val javaToken = getJavaToken(stringAnalyzer3)
        val token = javaToken
        val input = readString("resources/text_parser/Number2.java.java_code")
        println(input)
        var parse = -1
        try {
            parse = stringAnalyzer3.parse(token, input)
//     parse = stringAnalyzer3.mPosition
        } catch (ex: RuntimeException) {
            ex.printStackTrace()
            if (parse >= input.length) {
                Assert.assertTrue(true)
            } else {
                ex.printStackTrace()
                Assert.assertTrue(false)
            }
        }
        println(stringAnalyzer3.construct.toLangStringJava(isDebug))
        if (parse >= input.length || input.substring(parse).isBlank())
            println("")
        else
            println(input.substring(stringAnalyzer3.mPosition))

        if (parse >= input.length || input.substring(parse).isBlank()) {
            Assert.assertTrue(true)
            return
        } else {
            Assert.assertTrue(false)
        }

    }

    @Test
    fun testLogicalExpression() {
        val stringAnalyzer3 = StringAnalyzer3()
        val tokenIf = stringAnalyzer3.TokenString("if")
        val tokenLogicalExpression1 = stringAnalyzer3.TokenLogicalExpression()
        val tokenLogicalExpression2 = stringAnalyzer3.TokenLogicalExpression()
        val tokenInstructionSemiColon1 = stringAnalyzer3.TokenString(";")
        val tokenInstructionSemiColon2 = stringAnalyzer3.TokenString(";")
        val tokenExpression1 = stringAnalyzer3.TokenExpression1()
        val tokenExpression2 = stringAnalyzer3.TokenExpression1()
        val tokenELse = stringAnalyzer3.TokenString("else")

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
        println(stringAnalyzer3.mPosition)

        val result = tokenIf.isSuccessful && (parse >= input.length || input.substring(parse).trim().isBlank())
        if (result)
            Assert.assertTrue(true)
        else
            Assert.assertFalse(false)
    }
}