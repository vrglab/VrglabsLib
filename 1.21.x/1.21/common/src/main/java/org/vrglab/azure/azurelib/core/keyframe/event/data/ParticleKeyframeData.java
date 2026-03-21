/**
 * This class is a fork of the matching class found in the Geckolib repository. Original source:
 * https://github.com/bernie-g/geckolib Copyright © 2024 Bernie-G. Licensed under the MIT License.
 * https://github.com/bernie-g/geckolib/blob/main/LICENSE
 */
package org.vrglab.azure.azurelib.core.keyframe.event.data;

import java.util.Objects;

import org.vrglab.azure.azurelib.common.animation.controller.keyframe.AzKeyframe;

/**
 * Particle {@link AzKeyframe} instruction holder
 */
public class ParticleKeyframeData extends KeyFrameData {

    private final String _effect;

    private final String _locator;

    private final String _script;

    public ParticleKeyframeData(double startTick, String effect, String locator, String script) {
        super(startTick);

        this._script = script;
        this._locator = locator;
        this._effect = effect;
    }

    /**
     * Gets the effect id given by the {@link AzKeyframe} instruction from the {@code animation.json}
     */
    public String getEffect() {
        return this._effect;
    }

    /**
     * Gets the locator string given by the {@link AzKeyframe} instruction from the {@code animation.json}
     */
    public String getLocator() {
        return this._locator;
    }

    /**
     * Gets the script string given by the {@link AzKeyframe} instruction from the {@code animation.json}
     */
    public String script() {
        return this._script;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartTick(), _effect, _locator, _script);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
