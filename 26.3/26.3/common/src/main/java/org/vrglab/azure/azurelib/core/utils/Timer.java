/**
 * This class is a fork of the matching class found in the Geckolib repository. Original source:
 * https://github.com/bernie-g/geckolib Copyright © 2024 Bernie-G. Licensed under the MIT License.
 * https://github.com/bernie-g/geckolib/blob/main/LICENSE
 */
package org.vrglab.azure.azurelib.core.utils;

public class Timer {

    private final long _duration;

    private boolean _enabled;

    private long _time;

    public Timer(long duration) {
        this._duration = duration;
    }

    public long getRemaining() {
        return this._time - System.currentTimeMillis();
    }

    public void mark() {
        this.mark(this._duration);
    }

    public void mark(long duration) {
        this._enabled = true;
        this._time = System.currentTimeMillis() + duration;
    }

    public void reset() {
        this._enabled = false;
    }

    public boolean checkReset() {
        boolean isEnabled = this.check();

        if (isEnabled) {
            this.reset();
        }

        return isEnabled;
    }

    public boolean check() {
        return this._enabled && this.isTime();
    }

    public boolean isTime() {
        return System.currentTimeMillis() >= this._time;
    }

    public boolean checkRepeat() {
        if (!this._enabled) {
            this.mark();
        }

        return this.checkReset();
    }
}
