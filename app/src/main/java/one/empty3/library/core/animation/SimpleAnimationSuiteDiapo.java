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
package one.empty3.library.core.animation;

import one.empty3.library.Representable;
import one.empty3.tests.Animation;
import one.empty3.tests.MoveCollection;

public class SimpleAnimationSuiteDiapo extends Animation {
    public SimpleAnimationSuiteDiapo(Class<? extends Representable> anime, MoveCollection moveCollection) {
        super(anime, moveCollection);
    }
/*
    public SimpleAnimationSuiteDiapo(Scene s, ECDim dim) {
        super(s, dim);
    }

    public class MediaSuperType {

        public static final int MEDIA_TYPE_IMAGE = 0;
        public static final int MEDIA_TYPE_MOVIE = 1;
        public static final int MEDIA_TYPE_ANIMATION = 2;
        public static final int MEDIA_TYPE_COLOR = 4;

        private URL loadMediaFromUrl;
        private File loadMediaFromFIle;
        private BufferedImage image;
        private Movie movie;
    }
-**/
}
