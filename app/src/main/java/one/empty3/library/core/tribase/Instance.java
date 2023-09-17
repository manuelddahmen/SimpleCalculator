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

/*

 Vous êtes libre de :

 */
package one.empty3.library.core.tribase;

public class Instance {

    private BaseGenerator instance;
    private ThreadInstance thread;

    public Instance(BaseGenerator bg, Params params) {
        instance = bg;
        thread = new ThreadInstance(params);
        thread.start();
    }

    public void kill() {
        thread.setStopped(true);
        thread.setKilled(true);
    }

    public void pauseInstance() {
        thread.setPaused(true);
    }

    public void restartInstance() {
        thread.setStopped(true);
        thread.setStarted(true);
    }

    public void startInstance() {
        thread.setStarted(true);
    }

    public void stopInstance() {
        thread.setStopped(true);
    }

    private class ThreadInstance extends Thread {

        private boolean started = false;
        private boolean stopped = false;
        private boolean paused = false;
        private boolean killed = false;

        public ThreadInstance(Params params) {
            instance.setParams(params);
        }

        public void run() {
            while (!killed) {

                while (!stopped & started & !paused) {
                    instance.initFrame();
                    instance.computeFrame();
                    instance.showFrame();
                    instance.computeFrame();
                }
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void setKilled(boolean killed) {
            this.killed = killed;
        }

        public void setPaused(boolean paused) {
            this.paused = paused;
        }

        public void setStarted(boolean started) {
            this.started = started;
        }

        public void setStopped(boolean stopped) {
            this.stopped = stopped;
        }

    }
}
