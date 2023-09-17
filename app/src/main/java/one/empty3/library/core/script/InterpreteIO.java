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

 Vous êtes libre de :

 */
package one.empty3.library.core.script;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/*__
 * @author Manuel DAHMEN
 * @date
 */
public class InterpreteIO {

    public static File getFile(String filename, String repertoire) throws FileNotFoundException {
        File f = new File(filename);
        if (f.exists()) {
            return f;
        }

        Properties config = new Properties();
        try {
            config.load(new FileInputStream(System.getProperty("user.home") + File.separator + java.util.ResourceBundle.getBundle("info/emptycanvas/one.empty3.library/gui/Bundle").getString("NOM DU FICHIER DE CONFIGURATION PROPERTIES")));
            f = new File(config.getProperty("folder.textures") + File.separator + filename);
        } catch (IOException ex) {
            Logger.getLogger(InterpreteIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (f != null && f.exists()) {
            return f;
        }

        f = new File(repertoire + File.separator + filename);
        if (f != null && f.exists()) {
            return f;
        }
        throw new FileNotFoundException(
                "Default Folders: \n\t" + repertoire
                        + (config == null ? "\n\t" : "\n\t" + File.pathSeparator
                        + config.getProperty("folder.textures"))
                        + "\nFile: \n\t" + filename);
    }
}
