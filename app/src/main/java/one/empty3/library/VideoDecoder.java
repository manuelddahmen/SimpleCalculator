/*
 * Copyright (c) 2022-2023. Manuel Daniel Dahmen
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





import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.io.File;

public abstract class VideoDecoder extends Thread {
    protected boolean eof = false;
    protected TextureMov text;
    protected File file;
    protected boolean stop = false;
    protected static final long MAXSIZE = 4;
    protected ArrayList<ECBufferedImage> imgBuf = new ArrayList();


    /***
     * init, start, run, and block on maxsize reached
     * @param file video to draw on surface
     * @param refTextureMov texture to apply
     */
    public VideoDecoder(File file, TextureMov refTextureMov) {
        this.file = file;
        this.text = refTextureMov;
    }

    public int size() {
        return imgBuf.size();
    }

    public boolean isClosed() {
        return eof;
    }

    public ECBufferedImage current() {

        if(!imgBuf.isEmpty()) {
            ECBufferedImage c = imgBuf.get(0);
            imgBuf.remove(0);
            return c;
        } else {
            return null;
        }

    }
}
