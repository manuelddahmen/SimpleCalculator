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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package one.empty3.library.core.testing;

/*__
 * @author Se7en
 */
/*
*
* @(#) TextAreaOutputStream.java
*
*/

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

/*__
 * An output stream that writes its output to a javax.swing.JTextArea
 * control.
 *
 * @author Ranganath Kini
 * @see javax.swing.JTextArea
 */
public class TextAreaOutputStream extends OutputStream {
    private JTextArea textControl;

    /*__
     * Creates a new instance of TextAreaOutputStream which writes
     * to the specified instance of javax.swing.JTextArea control.
     *
     * @param control A reference to the javax.swing.JTextArea
     *                control to which the output must be redirected
     *                to.
     */
    public TextAreaOutputStream(JTextArea control) {
        textControl = control;
    }

    /*__
     * Writes the specified byte as a character to the
     * javax.swing.JTextArea.
     *
     * @param b The byte to be written as character to the
     *          JTextArea.
     */
    public void write(int b) throws IOException {
        // append the data as characters to the JTextArea control
        textControl.append(String.valueOf((char) b));
    }
}