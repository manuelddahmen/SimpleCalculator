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

package one.empty3.library.svgtexture;


import one.empty3.library.*;

import java.io.File;

/*__
 * Created by manue on 24-09-15.
 */
public class SVGTexture extends ITexture {

    public SVGTexture(File file) {
        // String parser = XMLResourceDescriptor.getXMLParserClassName();
        //SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
        String uri = file.toURI().toString();

    }

    @Override
    public void iterate() throws EOFVideoException {

    }

    @Override
    public int getColorAt(double x, double y) {
        return 0;
    }



    @Override
    public void timeNext() {
        // NOTHING TO DO HERE
    }

    @Override
    public void timeNext(long milli) {
        // NOTHING TO DO HERE
    }

    @Override
    public StructureMatrix getDeclaredProperty(String name) {
        return null;

    }

    @Override
    public MatrixPropertiesObject copy() throws CopyRepresentableError, IllegalAccessException, InstantiationException {
        return null;
    }
}
