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

/*__
 * *
 * Global license : * Microsoft Public Licence
 * <p>
 * author Manuel Dahmen _manuel.dahmen@gmx.com_
 * <p>
 * *
 */
package one.empty3.library.core.export;
/*
import one.empty3.library.*;
import com.sun.org.apache.xml.internal.utils.DOMBuilder;
import org.jdom.Attribute;
import org.jdom.Comment;
import org.jdom.Element;
import org.jdom.Text;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/*
public class Export3DXML {

    public DOMBuilder domBuilder;

    public static void main(String[] args) {
        Export3DXML e = new Export3DXML();
        try {
            e.save(new File("EMPTYCANVAS.3DXML"), new Scene(), false);
        } catch (IOException ex) {
            Logger.getLogger(Export3DXML.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void save(File file, Scene scene, boolean overwrite) throws IOException {
        Element scnXml = new Element("XML3D");
        scnXml.setAttribute(new Attribute("version", "4.3"));
        scnXml.setAttribute(new Attribute("namespace", "http://www.3ds.com/xsd/3DXML"));
        scnXml.addContent(new Comment("Not implemented yet"));
        Element RepresentationLinkType = new Element("RepresentationLinkType");
        RepresentationLinkType.addContent(new Text("urn:3DXML:Emptycanvas.simple_exemple001"));
        scnXml.addContent(RepresentationLinkType);

        XMLOutputter xmlOutputter = new org.jdom.output.XMLOutputter(Format.getPrettyFormat());

        if (!file.exists() || (file.exists() && overwrite)) {
            xmlOutputter.output(scnXml, new FileOutputStream(file));
        }
    }
}
*/