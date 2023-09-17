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

package one.empty3.library;

import javax.swing.*;
import java.awt.*;

/*__
 * Created by manuel on 25-01-17.
 */
public class Main2 extends JFrame {
    public Main2() {
        super("Application 2D/3D");
        JTextArea jTextArea = new JTextArea("# Variables: \n" +
                "# t : temps en secondes d'animation dans la vidéo\n" +
                "# xRes : résolution de la vidéo en coordArr\n" +
                "# yRes : résolution de la vidéo en y\n" +
                "# coordArr, y : coordonnées du point en (coordArr, y)\n" +
                "# tMax : fin de la vidéo en secondes\n");
        //jTextArea.setCursor(new Cursor(jTextArea.getLineCount()));
        JRootPane jRootPane = new JRootPane();
        jRootPane.setContentPane(jTextArea);
        add(jRootPane);
        this.setRootPane(jRootPane);
        this.setBounds(new Rectangle(0, 0, 800, 400));
    }

    public static void main(String[] args) {
        new Main2().setVisible(true);
    }
}
