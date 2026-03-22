/**
 * This class is a fork of the matching class found in the Geckolib repository. Original source:
 * https://github.com/bernie-g/geckolib Copyright © 2024 Bernie-G. Licensed under the MIT License.
 * https://github.com/bernie-g/geckolib/blob/main/LICENSE
 */

package org.vrglab.azure.azurelib.common.model;

import org.joml.Vector3f;

/**
 * A state monitoring class for a given {@link AzBone}.<br>
 */
public class AzBoneSnapshot {

    private final AzBone _bone;

    private final Vector3f _offsetPosition;

    private final Vector3f _rotation;

    private final Vector3f _scale;

    private double _lastResetRotationTick = 0;

    private double _lastResetPositionTick = 0;

    private double _lastResetScaleTick = 0;

    private boolean _rotAnimInProgress = true;

    private boolean _posAnimInProgress = true;

    private boolean _scaleAnimInProgress = true;

    public AzBoneSnapshot(AzBone bone) {
        this._bone = bone;
        this._offsetPosition = new Vector3f(bone.getPosX(), bone.getPosY(), bone.getPosZ());
        this._rotation = new Vector3f(bone.getRotX(), bone.getRotY(), bone.getRotZ());
        this._scale = new Vector3f(bone.getScaleX(), bone.getScaleY(), bone.getScaleZ());
    }

    public static AzBoneSnapshot copy(AzBoneSnapshot snapshot) {
        AzBoneSnapshot newSnapshot = new AzBoneSnapshot(snapshot._bone);

        newSnapshot._offsetPosition.set(snapshot._offsetPosition);
        newSnapshot._rotation.set(snapshot._rotation);
        newSnapshot._scale.set(snapshot._scale);

        return newSnapshot;
    }

    public AzBone getBone() {
        return this._bone;
    }

    public float getScaleX() {
        return this._scale.x;
    }

    public float getScaleY() {
        return this._scale.y;
    }

    public float getScaleZ() {
        return this._scale.z;
    }

    public float getOffsetX() {
        return this._offsetPosition.x;
    }

    public float getOffsetY() {
        return this._offsetPosition.y;
    }

    public float getOffsetZ() {
        return this._offsetPosition.z;
    }

    public float getRotX() {
        return this._rotation.x;
    }

    public float getRotY() {
        return this._rotation.y;
    }

    public float getRotZ() {
        return this._rotation.z;
    }

    public double getLastResetRotationTick() {
        return this._lastResetRotationTick;
    }

    public double getLastResetPositionTick() {
        return this._lastResetPositionTick;
    }

    public double getLastResetScaleTick() {
        return this._lastResetScaleTick;
    }

    public boolean isRotAnimInProgress() {
        return this._rotAnimInProgress;
    }

    public boolean isPosAnimInProgress() {
        return this._posAnimInProgress;
    }

    public boolean isScaleAnimInProgress() {
        return this._scaleAnimInProgress;
    }

    /**
     * Update the scale state of this snapshot
     */
    public void updateScale(float scaleX, float scaleY, float scaleZ) {
        _scale.set(scaleX, scaleY, scaleZ);
    }

    /**
     * Update the offset state of this snapshot
     */
    public void updateOffset(float offsetX, float offsetY, float offsetZ) {
        _offsetPosition.set(offsetX, offsetY, offsetZ);
    }

    /**
     * Update the rotation state of this snapshot
     */
    public void updateRotation(float rotX, float rotY, float rotZ) {
        _rotation.set(rotX, rotY, rotZ);
    }

    public void startPosAnim() {
        this._posAnimInProgress = true;
    }

    public void stopPosAnim(double tick) {
        this._posAnimInProgress = false;
        this._lastResetPositionTick = tick;
    }

    public void startRotAnim() {
        this._rotAnimInProgress = true;
    }

    public void stopRotAnim(double tick) {
        this._rotAnimInProgress = false;
        this._lastResetRotationTick = tick;
    }

    public void startScaleAnim() {
        this._scaleAnimInProgress = true;
    }

    public void stopScaleAnim(double tick) {
        this._scaleAnimInProgress = false;
        this._lastResetScaleTick = tick;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        return hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        return this._bone.getName().hashCode();
    }
}
