/**
 * This class is a fork of the matching class found in the Geckolib repository. Original source:
 * https://github.com/bernie-g/geckolib Copyright © 2024 Bernie-G. Licensed under the MIT License.
 * https://github.com/bernie-g/geckolib/blob/main/LICENSE
 */
package org.vrglab.azure.azurelib.core.keyframe.event.data;

import java.util.Objects;

import org.vrglab.azure.azurelib.common.animation.controller.keyframe.AzKeyframe;

/**
 * Sound {@link AzKeyframe} instruction holder
 */
public class SoundKeyframeData extends KeyFrameData {

    private final String _sound;

    public SoundKeyframeData(Double startTick, String sound) {
        super(startTick);

        this._sound = sound;
    }

    /**
     * Gets the sound id given by the {@link AzKeyframe} instruction from the {@code animation.json}
     */
    public String getSound() {
        return this._sound;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartTick(), this._sound);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
