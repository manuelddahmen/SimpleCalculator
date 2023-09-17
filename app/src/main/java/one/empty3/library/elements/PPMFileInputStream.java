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

package one.empty3.library.elements;

import one.empty3.library.*;

import java.io.ByteArrayInputStream;

public class PPMFileInputStream extends ByteArrayInputStream {

    private StringBuilder stringBuilder;

    public PPMFileInputStream(StringBuilder sb) {
        super(sb.toString().getBytes());
        this.stringBuilder = sb;
    }

    public byte[] getBytes() {
        return stringBuilder.toString().getBytes();
    }

    public void update() {
        this.reset();
        int read = this.read();
        if (read > 0) {
            ECBufferedImage ppm = ECBufferedImage.ppm(getBytes(), "PPM");
        }
    }
}
