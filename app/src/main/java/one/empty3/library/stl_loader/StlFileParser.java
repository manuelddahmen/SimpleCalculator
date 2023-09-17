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

package one.empty3.library.stl_loader;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;

public class StlFileParser extends StreamTokenizer {

    /*__
     * Constructor: object creation and setup
     *
     * @param r The Reader instance
     */
    public StlFileParser(Reader r) {
        super(r);
        setup();
    }

    /*__
     * Gets a number from the stream. Note that we don't recognize numbers in
     * the tokenizer automatically because numbers might be in scientific
     * notation, which isn't processed correctly by StreamTokenizer. The number
     * is returned in nval.
     *
     * @return boolean.
     */
    boolean getNumber() {
        int t;

        try {
            nextToken();
            if (ttype != TT_WORD) {
                throw new IOException("Expected number on line " + lineno());
            }
            nval = (Double.valueOf(sval)).doubleValue();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return false;
        } catch (NumberFormatException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    } // end of getNumber

    /*__
     * Method that sets some params of the Tokenizer for reading the file
     * correctly
     */
    public void setup() {
        resetSyntax();
        eolIsSignificant(true);   // The End Of Line is important
        lowerCaseMode(true);

        // All printable ascii characters
        wordChars('!', '~');

        whitespaceChars(' ', ' ');
        whitespaceChars('\n', '\n');
        whitespaceChars('\r', '\r');
        whitespaceChars('\t', '\t');
    }// End setup

}// End of StlFileParser
