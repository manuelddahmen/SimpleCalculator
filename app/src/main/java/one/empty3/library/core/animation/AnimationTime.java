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
 * Global license : * CC Attribution
 * <p>
 * author Manuel Dahmen _manuel.dahmen@gmx.com_
 * <p>
 * *
 */
package one.empty3.library.core.animation;

public class AnimationTime {
    protected long fps;
    protected long frame;
    protected double timeCurrentInAnimation;

    public AnimationTime(long fps) {
        this.fps = (fps);
    }

    protected double timeMin;
    protected double timeMax;

    public void avanceUneFrame() {
        timeCurrentInAnimation += 1.0 / fps;
    }

    public long getFps() {
        return fps;
    }

    public void setFps(long fps) {
        this.fps = fps;
    }

    public long getFrame() {
        return frame;
    }

    public void setFrame(long frame) {
        this.frame = frame;
    }

    public double getTimeCurrentInAnimation() {
        return timeCurrentInAnimation;
    }

    public void setTimeCurrentInAnimation(double timeCurrentInAnimation) {
        this.timeCurrentInAnimation = timeCurrentInAnimation;
    }

    public void increase(double time) {
        // TODO
    }

    public void increase(int numberOfFrames) {
        // TODO
    }

    public double getTimeMin() {
        return timeMin;
    }

    public void setTimeMin(double timeMin) {
        this.timeMin = timeMin;
    }

    public double getTimeMax() {
        return timeMax;
    }

    public void setTimeMax(double timeMax) {
        this.timeMax = timeMax;
    }
}
