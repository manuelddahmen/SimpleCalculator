/*
 * Copyright (c) 2023. Manuel Daniel Dahmen
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

package one.empty3.library.core.script;

import java.util.ArrayList;

public class InterpretesBase {

    public final int BLANK = 0;
    public final int DECIMAL = 1;
    public final int INTEGER = 2;
    public final int LEFTPARENTHESIS = 3;
    public final int RIGHTPARENTHESIS = 4;
    public final int COMA = 5;
    public final int CARACTERE = 6;
    public final int DIESE = 7;
    public final int AROBASE = 8;
    public final int MULTIPLICATION = 9;
    public final int PERCENT = 10;
    int size = 0;
    boolean elementParsed = false;
    private int pos;
    private ArrayList<Integer> pattern;
    private ArrayList<Object> objects = new ArrayList<Object>();

    public InterpretesBase() {
        objects = new ArrayList<Object>();
        pos = 0;
    }

    public void compile(ArrayList<Integer> pattern) {
        this.pattern = pattern;
    }

    public ArrayList<Object> get() {
        return objects;
    }

    public int getPosition() {
        return pos;
    }

    public void setPosition(int pos) {
        this.pos = pos;
    }

    private Object read(Integer integer2, String substring) {
        int pos = 0;
        size = 0;
        elementParsed = false;
        if (substring.length() == 0 && integer2 != BLANK) {
            return null;
        } else if (substring.length() == 0 && integer2 == BLANK) {
            elementParsed = true;
            return " ";
        }
        switch (integer2) {
            case BLANK:

                char c = substring.charAt(0);
                while (c == ' ' | c == '\n' | c == '\t' | c == '\r') {
                    pos++;
                    if (pos < substring.length()) {
                        c = substring.charAt(pos);
                    } else {
                        break;
                    }

                }
                size = pos;
                elementParsed = true;
                return " ";
            case DECIMAL:
                pos = 0;
                c = substring.charAt(0);
                while (pos < substring.length() && (c >= '1' & c <= '9' | c == '0' | c == '.' | c == '-')) {
                    pos++;
                    size = pos;
                    if (pos < substring.length()) {
                        c = substring.charAt(pos);
                    }
                }
                if (pos <= substring.length() - 1) {
                    c = substring.charAt(pos);
                    if (c == 'E' || c == 'e') {
                        pos++;
                        size = pos;
                        c = substring.charAt(pos);
                        while (c >= '1' & c <= '9' | c == '0' | c == '-') {
                            pos++;
                            size = pos;
                            if (pos < substring.length()) {
                                c = substring.charAt(pos);
                            }
                            elementParsed = true;
                        }

                    }
                }
                if (pos > 0) {
                    elementParsed = true;
                    return Double.parseDouble(substring.substring(0, pos));
                } else {
                    return null;
                }

            case INTEGER:
                pos = 0;
                c = substring.charAt(0);
                while (pos < substring.length() & ((c >= '1' & c <= '9') | c == '0' | c == '-')) {
                    pos++;
                    size = pos;
                    if (pos < substring.length()) {
                        c = substring.charAt(pos);
                    } else {
                        break;
                    }
                    elementParsed = true;
                }
                if (elementParsed) {
                    return Integer.parseInt(substring.substring(0, pos));
                } else {
                    return null;
                }
            case COMA:
                pos = 0;
                if (substring.charAt(0) == ',') {
                    size = 1;
                    elementParsed = true;
                    return new CODE(COMA);
                } else {
                    size = 0;
                    elementParsed = false;
                    return new CODE(COMA);
                }
            case LEFTPARENTHESIS:
                pos = 0;
                if (substring.charAt(0) == '(') {
                    size = 1;
                    elementParsed = true;
                    return new CODE(LEFTPARENTHESIS);
                }
                return null;
            case RIGHTPARENTHESIS:
                pos = 0;
                if (substring.charAt(0) == ')') {
                    size = 1;
                    elementParsed = true;
                    return new CODE(RIGHTPARENTHESIS);
                }
                return null;
            case DIESE:
                pos = 0;
                if (substring.charAt(0) == '#') {
                    size = 1;
                    elementParsed = true;
                    return new CODE(DIESE);
                }
                return null;
            case AROBASE:
                pos = 0;
                if (substring.charAt(0) == '@') {
                    size = 1;
                    elementParsed = true;
                    return new CODE(AROBASE);
                }
                return null;
            case MULTIPLICATION:
                pos = 0;
                if (substring.charAt(0) == '*') {
                    size = 1;
                    elementParsed = true;
                    return new CODE(MULTIPLICATION);
                }
                return null;
            case PERCENT:
                pos = 0;
                if (substring.charAt(0) == '%') {
                    size = 1;
                    elementParsed = true;
                    return new CODE(PERCENT);
                }
                return null;
            case CARACTERE:
                pos = 0;
                elementParsed = true;
                return substring.charAt(0);
        }
        return substring;

    }

    public ArrayList<Object> read(String chaine, int pos) throws InterpreteException {
        int ppos = 0;
        while (ppos < pattern.size()) {
            Object o = read(pattern.get(ppos), chaine.substring(pos));
            if (elementParsed && o != null) {
                objects.add(o);
                ppos++;
                pos += size;
            } else {
                if (o != null) {
                    throw new InterpreteException("Parser Error : " + o.toString());
                } else {
                    throw new InterpreteException("Parser Error : code char" + pattern.get(ppos) + " code pos: " + pos + "string" +
                            "parser: " + chaine + " charAt pos='" + chaine.charAt(pos) + "'");
                }
            }

        }
        this.pos = pos;
        return objects;
    }

    public class CODE {

        private int CODE;

        public CODE(int cODE) {
            super();
            CODE = cODE;
        }

        public int getCODE() {
            return CODE;
        }

        public void setCODE(int cODE) {
            CODE = cODE;
        }
    }

}
