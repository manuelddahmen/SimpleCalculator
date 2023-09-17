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

import java.io.File;
import java.util.*;

public class ParseCode {
    int i = 0;
    String brut;
    String uncommented = "";
    int start, end;
    List<Token> tokens = new ArrayList();
    List<Node> nodes = new ArrayList();
    String special = "!%*&()+\\|/[]{}<>=.:,;?'\"";
    String[] keywords = new String[]{
            ""
    };
    String braces = "[](){}";
    String bracesCurrentQueue = "";
    String code;
    Tree tree;

    public ParseCode() {
        tree = new Tree();
        //removeComments();
        // separer en tokens typés
    }
    /* public boolean addToken() {
        Token t = new Token();
        return true;
    }
    */

    public void setBrut(String brut) {
        this.brut = brut;
    }

    public void removeComments() {
        StringBuilder sb = new StringBuilder();
        // Caractère par caractère
        int comm = 0;
        // Si /* sauter jusqu'à */
        // Si // sauter jusqu'à ¶

        for (i = 0; i < brut.length(); i++) {
            if (comm == 0 && brut.charAt(i) == '"') {
                comm = 3;
                i++;
            }
            if (comm == 3 && brut.charAt(i) == '"') {
                comm = 0;
                i++;
            }
            if (comm == 3 && brut.charAt(i) == '\\') {
                i += 2;
            }
            if (comm == 0 && brut.charAt(i) == '\'') {
                comm = 4;
                i++;
            }
            if (comm == 4 && brut.charAt(i) == '\'') {
                comm = 0;
                i++;
            }
            if (comm == 4 && brut.charAt(i) == '\\') {
                i += 2;
            }
            if (comm == 0 && brut.charAt(i) == '/'
                    && i < brut.length() - 1
                    && brut.charAt(i + 1) == '*')
                comm = 1;
            if (comm == 0 && brut.charAt(i) == '/'
                    && i < brut.length() - 1
                    && brut.charAt(i + 1) == '/') {

                comm = 2;
                while (i < brut.length()) {
                    if (brut.charAt(i) == '\n') {
                        comm = 0;
                        i++;
                    }
                }
            }
            if (comm == 1 && brut.charAt(i) == '*'
                    && i < brut.length() - 1
                    && brut.charAt(i + 1) == '/') {
                comm = 0;
                i += 2;
            }
            if (comm == 0)
                for (int brace = 0; brace < braces.length() / 2; brace++) {
                    if (braces.charAt(brace * 2) == brut.charAt(i)) {
                        //! lastBrace
                        bracesCurrentQueue
                                = bracesCurrentQueue + brut.charAt(i);
                    }
                    if (braces.charAt(brace * 2 + 1) == brut.charAt(i)) {
                        //! lastBrace
                        if (bracesCurrentQueue.length() <= 1) {
                            bracesCurrentQueue
                                    = "";
                        } else {
                            bracesCurrentQueue
                                    = bracesCurrentQueue.substring
                                    (0, bracesCurrentQueue.length() - 1);
                        }

                    }
                }


            if (comm > 0)

                end++;

            if (comm == 0) {

                start = i;
                end = i;
            }
            if (comm == 0) {


                // String forbidden = "\"\'\n\r/";
                uncommented = brut.substring
                        (start, end);
                boolean passed = false;
                try {
                    passed = parseSpace() ||
                            parseSpecialChar() ||
                            parseKeyword() ||
                            parseName() ||
                            parseLiteral();

                }catch (Exception ex) {
                    ex.printStackTrace();
                }

                sb.append(brut.charAt(i));

            }


            i++;
        }

        uncommented = sb.toString();
    }

    public boolean parseSpace() {
        int pos = i;
        boolean b = false;
        while (!b && i < uncommented.length()) {
            char a = uncommented.charAt(pos);
            if (a == ' ' || a == '\n' || a == '\t' || a == '\r') {
                pos++;
                b = true;
            }
        }
        if (b) {
            tokens.add(new Token("space",
                    uncommented.substring(i, pos), Token.TokenTypeTxt.Space));
            pos = i;
            return true;
        }
        return false;
    }

    public boolean parseSpecialChar() {
        boolean b = false;
        while (i< uncommented.length() &&isSpecialChar(uncommented, i)) {
            char special = uncommented.charAt(i);
            i++;
            b = true;
        }
        if (b)
            return true;
        return false;
    }

    public boolean parseKeyword() {
        char a = uncommented.charAt(i);
        int j = 0;
        while (Character.isLetter(a)) {
            j++;
            a = uncommented.charAt(i + j);
        }
        if (isSpecialChar(uncommented, i + j) ||
                isWhitespace(uncommented, i + j)) {
            List<String> list = Arrays.asList(keywords);

            String k = uncommented.substring(i, i + j);
            if (k.length() > 0 && list.contains(k))
                tokens.add(new Token("keyword",
                        k, Token.TokenTypeTxt.Keyword));
            return true;
        }
        return false;
    }

    public boolean parseName() {
        char a = uncommented.charAt(i);
        int j = 0;
        while (Character.isLetter(a) || (j > 0
                && (Character.isLetterOrDigit(a) ||
                a == '_'))) {
            j++;
            a = uncommented.charAt(i + j);
        }
        List<String> list = Arrays.asList(keywords);

        String k = uncommented.substring(i, i + j);
        if (k.length() > 0 && !list.contains(k)) {
            tokens.add(new Token("name",
                    k, Token.TokenTypeTxt.Name));
            i = i + j;
            return true;
        }
        return false;
    }

    public boolean isSpecialChar(String uncomm,
                                 int pos) {

        return special.indexOf(uncomm.charAt(pos)) >= 0;

    }

    public boolean isWhitespace(String uncomm,
                                int pos) {
        char a = uncomm.charAt(pos);
        if (a == ' ' || a == '\n' || a == '\t' || a == '\r') {
            // i=i+j;
            //  i=i+pos;

            return true;
        }
        return false;
    }

    public int nextWhitespace(String uncomm,
                              int pos) {
        boolean b = false;
        do {
            char a = uncomm.charAt(pos);
            if (a == ' ' || a == '\n' || a == '\t' || a == '\r') {
                pos++;
                b = true;
            }
        } while (!b && pos < uncomm.length());
        return pos;
    }

    public int nextChar(String uncomm,
                        int pos) {

        char a = uncomm.charAt(pos);
        while ((a == ' ' || a == '\n' || a == '\t' || a == '\r')) {
            pos++;
        }

        return pos;
    }

    public boolean parseLiteral() {
        // bool
        if (uncommented.substring(i, i + "false".length()).equals("false")) {
            tokens.add(new Token("boolean:false",
                    uncommented.substring(i, i + "false".length()), Token.TokenTypeTxt.Literal));

            return true;
        }
        if (uncommented.substring(i, i + "true".length()).equals("true")) {
            tokens.add(new Token("boolean:true",
                    uncommented.substring(i, i + "true".length()), Token.TokenTypeTxt.Literal));
            return true;
        }

        //tokens.add(new Token("float|double|string|int|char|boolean|long",
        // k));
        return false;
    }
    public void parseTokensToTree() {
        List<Token> tokens = this.tokens;

        tokens.forEach(token -> {
            System.out.printf("Token : %s\n", token);
        });
    }

    public void block() {
    }

    public void tree() {
    }

    public void reduce() {
    }

    public void map() {
    }

    public void result() {
    }

    public static void main(String[] args) {
        ParseCode parseCode = new ParseCode();
        parseCode.setBrut("/* */ ");
        parseCode.removeComments();
        System.out.println(parseCode.code);
        parseCode.parseTokensToTree();
    }

    public List<Token> parseFile(File txt) {
        return tokens;
    }
}
