/*
 * Copyright (c) 2022-2023. Manuel Daniel Dahmen
 *
 *
 *    Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package one.empty3.library.lang;

import java.util.*;
public class Scope {
    
    public static final int declaration = 1;
    public static final int instruction = 2;
    public static final int assignement = 4;
    public HashMap<TokenType, List<Object>> follows = new HashMap<>();
    public HashMap<TokenType, List<Object>> followsIn = new HashMap<>();
    public HashMap<TokenType, List<Object>> followsOut = new HashMap<>();


    {
        follows.put(TokenType.Name, new ArrayList<>());
        follows.get(TokenType.Name).add(TokenType.ParamsDeclaration);
        follows.get(TokenType.Name).add(TokenType.Params);
        follows.get(TokenType.Name).add(TokenType.MethodBody);
        follows.get(TokenType.Name).add(TokenType.EndOfInstruction);
        follows.get(TokenType.Name).add(TokenType.Operator);
        follows.get(TokenType.Name).add(TokenType.Coma);
        follows.get(TokenType.Name).add(TokenType.CallMethodDot);
        followsIn.put(TokenType.ParamsDeclaration, new ArrayList<>());

    }
    public enum TokenType {
        Name, Keyword,  Comment,
        JavadocComment,StringLiteral,
        FloatLiteral, DoubleLiteral, CharLiteral,
        Unnamed, For, While, Do, Method,
        Package, Imports, Classes, Interfaces,
        MethodMember, VarMember, Variable, ParamsDeclaration,
        Params,
        EndOfInstruction, Operator, MethodBody, Coma,
        CallMethodDot, ParamDeclaration, Param};
    
    Scope parentScope;
    List<Node> nodes;
    String modifier; // static
    String privacy; // private public protected package
    boolean iDo;
    boolean iWhile;
    boolean iUnnamed;
    boolean iFor;
    boolean iForEach;

    Tree params;
    Tree cond;
    Tree instructions;
    
}
