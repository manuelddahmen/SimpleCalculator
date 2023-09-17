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

import one.empty3.library.Representable;
import one.empty3.library.Scene;

import java.io.*;

public class ModeleIO {

    public static boolean sauvergarder(Representable o, File file) {
        boolean r = false;
        ObjectOutputStream dos = null;
        FileOutputStream fos = null;
        try {
            dos = new ObjectOutputStream(fos = new FileOutputStream(file));
            r = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            dos.writeObject(o);
        } catch (Exception ex) {
        } finally {
            try {
                dos.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            try {
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return r;
    }

    public static boolean sauvergarder(Scene sc, File file) {
        boolean r = false;
        ObjectOutputStream dos = null;
        FileOutputStream fos = null;
        try {
            sc.dumpDATA();
            fos = new FileOutputStream(file);
            dos = new ObjectOutputStream(fos);
            r = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            dos.writeObject(sc);
        } catch (Exception ex) {
        } finally {
            try {
                dos.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            try {
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return r;
    }


    public static boolean sauvergarderTXT(Scene sc, File file) {
        String txt = sc.toString();
        boolean r = false;
        FileOutputStream dos = null;
        BufferedOutputStream pw = null;
        try {
            dos = new FileOutputStream(file);
            pw = new BufferedOutputStream(dos);
            pw.write(txt.getBytes(), 0, txt.length());
            pw.close();
            dos.close();
            r = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();

            return r;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public Scene charger(Scene sc, File file) {
        boolean r = false;
        ObjectInputStream dos = null;
        try {
            dos = new ObjectInputStream(new FileInputStream(file));
            r = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            return (Scene) dos.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                dos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }


}
