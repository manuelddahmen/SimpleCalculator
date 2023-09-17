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

import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*__
 * Created by manuel on 30-11-16.
 */
public class ConsoleUtils {
    protected static DateFormat dateFormat;

    static {
        DateFormatter dfer = new DateFormatter();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss-SSSXX");

    }

    public static String currentDate() {

        return dateFormat.format(new Date());
    }
}
