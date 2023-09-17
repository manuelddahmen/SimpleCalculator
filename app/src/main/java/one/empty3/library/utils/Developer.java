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

package one.empty3.library.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/*__
 *
 * @author manuel
 */
public class Developer {

    public static BufferedImage getImageFromClasspath(Object clazz, String path) {
        try {
            return ImageIO.read(clazz.getClass()
                    .getResourceAsStream(
                            path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BufferedImage getImageFromOuter(String[] path, String name) {
        for (int i = 0; i < path.length; i++) {
            try {
                return ImageIO.read(new File(path + File.separator + name));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
