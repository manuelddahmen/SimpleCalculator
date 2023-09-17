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

package one.empty3.library.core.testing;

import one.empty3.library.Camera;
import one.empty3.library.ITexture;
import one.empty3.library.Scene;
import one.empty3.library.ZBuffer;

import java.io.File;
import java.util.ArrayList;

/*__
 * @author Manuel DAHMEN
 */
public interface Test extends Runnable {

    /*__
     * After the loop extend to add extra info on movie
     */
    void afterRender();

    /*__
     * Prefer use scene().cameraActive()
     *
     * @return
     */
    Camera camera();

    /*__
     * Prefer use scene().cameraActive()
     *
     * @return
     */
    void camera(Camera c);

    /*__
     * Not in use
     *
     * @return
     */
    ArrayList<TestInstance.Parameter> getInitParams();


    /*__
     * boolean for begin loop and making a movie or a image sequence
     *
     * @return isLoop?
     */
    boolean loop();

    /*__
     * boolean for begin loop and making a movie or a image sequence
     *
     * @return isLoop?
     */
    void loop(boolean isLooping);

    /*__
     * Internal use
     *
     * @return
     */
    boolean nextFrame();

    /*__
     * Internal use
     */
    void publishResult();


    /*__
     * Main run test method. Don't call it directly. Called when test starts
     */
    void run();

    /*__
     * Scene to render. Instance Read only
     *
     * @return
     */
    Scene scene();

    /*__
     * Main frame animation method
     *
     * @throws Exception
     */
    void testScene() throws Exception;

    /*__
     * Main frame animation method. Load a file. Deprecated?
     *
     * @throws Exception
     */
    void testScene(File f) throws Exception;

    /*__
     * Use for drawing fast after scene is drawn
     *
     * @return instance of running ZBuffer (ZBufferImpl)
     */
    ZBuffer getZ();


    void onTextureEnds(ITexture texture, int texture_event);

    void onMaxFrame(int maxFramesEvent);

}
