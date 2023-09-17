/*
 * Copyright (c) 2023.
 *
 *
 *  Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package javaAnd.awt.image.imageio;

//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javaAnd.awt.image.BufferedImage;

public class ImageIO {
    public static BufferedImage read(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedImage bitmap2 = ImageIO.read(fileInputStream);
            fileInputStream.close();
            return bitmap2;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static BufferedImage read(FileInputStream fileInputStream) {
        return new BufferedImage(BitmapFactory.decodeStream(fileInputStream));
    }

    public static boolean write(@NotNull BufferedImage imageOut, String jpg, File out, boolean shouldOverwrite) throws IOException {
        if ((!out.exists() || shouldOverwrite)&&imageOut.bitmap!=null) {
            FileOutputStream fileOutputStream = new FileOutputStream(out);
            boolean ret = imageOut.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.close();
            return ret;
        } else {
            return false;
        }

    }

    public static boolean write(BufferedImage imageOut, String jpg, File out) throws IOException {
        if (!out.exists()) {
            FileOutputStream fileOutputStream = new FileOutputStream(out);
            imageOut.getBitmap().compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            fileOutputStream.close();
            return true;
        } else {
            return false;
        }

    }

    public static boolean write(Bitmap image, String jpg, File out) {
        FileOutputStream fileOutputStream = null;
        try {
                fileOutputStream = new FileOutputStream(out);
                return image.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
        } catch (FileNotFoundException e) {
            System.out.println("ImageIO write");
            e.printStackTrace();
        }
        return false;
    }

}
