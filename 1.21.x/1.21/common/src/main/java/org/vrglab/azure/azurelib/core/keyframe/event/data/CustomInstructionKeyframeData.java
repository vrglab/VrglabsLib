/**
 * This class is a fork of the matching class found in the Geckolib repository. Original source:
 * https://github.com/bernie-g/geckolib Copyright © 2024 Bernie-G. Licensed under the MIT License.
 * https://github.com/bernie-g/geckolib/blob/main/LICENSE
 */
package org.vrglab.azure.azurelib.core.keyframe.event.data;

import java.util.Objects;

import org.vrglab.azure.azurelib.common.animation.controller.keyframe.AzKeyframe;

/**
 * Custom instruction {@link AzKeyframe} instruction holder
 */
public class CustomInstructionKeyframeData extends KeyFrameData {

    private final String _instructions;

    public CustomInstructionKeyframeData(double startTick, String instructions) {
        super(startTick);

        this._instructions = instructions;
    }

    /**
     * Gets the instructions string given by the {@link AzKeyframe} instruction from the {@code animation.json}
     */
    public String getInstructions() {
        return this._instructions;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartTick(), _instructions);
    }


    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
