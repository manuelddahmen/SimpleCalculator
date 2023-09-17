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

public class Token {
    TokenTypeTxt typeTxt;
    String literal;
    String tt;
    public Token(String tt, String literal, TokenTypeTxt typeTxt) {
        this.typeTxt = typeTxt;
        literal = literal;
        this.tt = tt;
    }
    public enum TokenTypeTxt {
        Space,
        SpecialChar,
        Keyword,
        Name,
        Literal
    }

    public TokenTypeTxt getTypeTxt() {
        return typeTxt;
    }

    public void setTypeTxt(TokenTypeTxt typeTxt) {
        this.typeTxt = typeTxt;
    }

    public String getLiteral() {
        return literal;
    }

    public void setLiteral(String literal) {
        this.literal = literal;
    }

    public String getTt() {
        return tt;
    }

    public void setTt(String tt) {
        this.tt = tt;
    }

    @Override
    public String toString() {
        return "Token{" +
                "literal='" + literal + '\'' +
                ", tt='" + tt + '\'' +
                ", typeTxt='" + typeTxt + '\'' +
                '}';
    }
}
