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
package one.empty3.library;

import javax.swing.*;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/*__
 * @author manue_001
 */
public class RegisterOutput {

    private Logger logger;
    private OutputStream output;
    private JTextPane pane;

    public void addOutput(Logger l) {
        this.logger = l;
    }

    public void addOutput(OutputStream o) {
        this.output = o;
    }

    public void addOutput(JTextPane p) {
        this.pane = p;
    }

    public Logger getLogger() {
        return logger;
    }

    public OutputStream getOutput() {
        return output;
    }

    public JTextPane getPane() {
        return pane;
    }

    public void println(String s) {
        if (logger != null)
            logger.log(Level.INFO, s);
        if (output != null)
            new PrintWriter(output).println(s);
        if (pane != null)
            pane.setText(pane.getText() + "\n" + s);


    }
}
