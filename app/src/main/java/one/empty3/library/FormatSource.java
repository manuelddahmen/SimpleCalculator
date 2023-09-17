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
package one.empty3.library;

import one.empty3.library.core.script.Loader;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/*__
 * @author manuel
 */
public class FormatSource {

    public static void chargerStyles(PrintWriter pw) {
        pw.write("<style type='text/css'>");
        File css = new File("one/empty3/library/styles_menu_et_images.css");
        if (css.exists()) {
            BufferedInputStream bis = null;
            try {
                byte[] buffer = new byte[(int) css.length()];
                bis = new BufferedInputStream(new FileInputStream(css));
                bis.read(buffer);
                pw.write(new String(buffer));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FormatSource.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FormatSource.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    bis.close();
                } catch (IOException ex) {
                    Logger.getLogger(FormatSource.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        pw.write("</style>");
    }

    public static void format(File i, File o, File png) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(o);
            Scene s = new Scene();
            new Loader().loadIF(i, s);
            try {
                Logger.getAnonymousLogger().log(Level.INFO, o.getAbsolutePath());
                //o.mkdirs();
                o.createNewFile();
            } catch (IOException ex) {
                System.err.println("Erreur en créant le fichier");
                Logger.getLogger(FormatSource.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            String dirName = i.getName().substring(0, i.getName().lastIndexOf("."));
            pw.write("<html>\n\t<head>\n\t\t<title>" + dirName + "</title>\n\t");

            chargerStyles(pw);

            pw.write("</head>\n\t<body><img href='ibiiztetra_logo.png'>\n\t<h1>Fichier de rendu: " + png.getName() + "<h2 class='menu_link'><a href=\"../index.html\">Menu</a></h2>\n\t\t<pre>");
            pw.write("<h2 class='code'>Code source</h2>" + s.toString());
            pw.write("\n\t\t</pre>\n\t\t<img src=\"" + png.getName() + "\">\n\t<h2 class='menu_link'><a href=\"../index.html\">Menu</a></h2>\n\t</body>\n</html>");
        } catch (IOException ex) {
            Logger.getLogger(FormatSource.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            pw.close();
        }
    }

    public static void index(ArrayList<File> html, String directory_out) {

        PrintWriter pw = null;
        try {
            File index = new File(directory_out + File.separator + "index.html");
            pw = new PrintWriter(index);
            pw.write("<html>\n\t<head>\n\t\t<title>Modeles</title>\n\t");
            chargerStyles(pw);
            pw.write("\n\t</head>\n\t<body><img href='../ibiiztetra_logo.png'>\n\t<h1>Index des fichiers de rendu</h1><h2 class='menu_link'>Menu</h2><ul>");
            Iterator<File> fichiersIT = html.iterator();
            while (fichiersIT.hasNext()) {
                File f = fichiersIT.next();
                pw.write("<li><a href=\"" + f.getName().substring(0, f.getName().lastIndexOf(".")) + File.separator + f.getName().substring(0, f.getName().lastIndexOf(".")) + ".html"
                        + "\">" + f.getName().substring(0, f.getName().lastIndexOf(".")) + "</a></li>\n\t");
            }
            pw.write("</ul></body></html>");
        } catch (IOException ex) {
            Logger.getLogger(FormatSource.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            pw.close();
        }

        //FileInputStream fis = new FileInputStream(new File("ibiiztetra_logo.png"));
        //fis.
    }
}
