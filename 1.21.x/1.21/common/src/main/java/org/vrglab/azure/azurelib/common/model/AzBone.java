package org.vrglab.azure.azurelib.common.model;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;
import java.util.Objects;

import org.vrglab.azure.azurelib.common.cache.object.GeoCube;

/**
 * Mutable bone object representing a set of cubes, as well as child bones.<br>
 * This is the object that is directly modified by animations to handle movement
 */
public class AzBone {

    private final AzBoneMetadata _metadata;

    private final List<AzBone> _children = new ObjectArrayList<>();

    private final List<GeoCube> _cubes = new ObjectArrayList<>();

    private final Matrix4f _modelSpaceMatrix = new Matrix4f();

    private final Matrix4f _localSpaceMatrix = new Matrix4f();

    private final Matrix4f _worldSpaceMatrix = new Matrix4f();

    private AzBoneSnapshot _initialSnapshot;

    private boolean _hidden;

    private boolean _childrenHidden = false;

    private final Vector3f _pivot;

    private final Vector3f _position;

    private final Vector3f _rotation;

    private final Vector3f _scale;

    private boolean _positionChanged = false;

    private boolean _rotationChanged = false;

    private boolean _scaleChanged = false;

    private Matrix3f _worldSpaceNormal = new Matrix3f();

    private boolean _trackingMatrices;

    public AzBone(AzBoneMetadata metadata) {
        this._metadata = metadata;
        this._trackingMatrices = false;
        this._hidden = metadata.dontRender() == Boolean.TRUE;

        this._position = new Vector3f();
        this._pivot = new Vector3f();
        this._rotation = new Vector3f();
        this._scale = new Vector3f(1, 1, 1);

        this._worldSpaceNormal.identity();
        this._worldSpaceMatrix.identity();
        this._localSpaceMatrix.identity();
        this._modelSpaceMatrix.identity();
    }

    public String getName() {
        return _metadata.name();
    }

    public AzBone getParent() {
        return _metadata.parent();
    }

    public float getRotX() {
        return this._rotation.x;
    }

    public void setRotX(float value) {
        this._rotation.x = value;

        markRotationAsChanged();
    }

    public float getRotY() {
        return this._rotation.y;
    }

    public void setRotY(float value) {
        this._rotation.y = value;

        markRotationAsChanged();
    }

    public float getRotZ() {
        return this._rotation.z;
    }

    public void setRotZ(float value) {
        this._rotation.z = value;

        markRotationAsChanged();
    }

    public float getPosX() {
        return this._position.x;
    }

    public void setPosX(float value) {
        this._position.x = value;

        markPositionAsChanged();
    }

    public float getPosY() {
        return this._position.y;
    }

    public void setPosY(float value) {
        this._position.y = value;

        markPositionAsChanged();
    }

    public float getPosZ() {
        return this._position.z;
    }

    public void setPosZ(float value) {
        this._position.z = value;

        markPositionAsChanged();
    }

    public float getScaleX() {
        return this._scale.x;
    }

    public void setScaleX(float value) {
        this._scale.x = value;

        markScaleAsChanged();
    }

    public float getScaleY() {
        return this._scale.y;
    }

    public void setScaleY(float value) {
        this._scale.y = value;

        markScaleAsChanged();
    }

    public float getScaleZ() {
        return this._scale.z;
    }

    public void setScaleZ(float value) {
        this._scale.z = value;

        markScaleAsChanged();
    }

    public boolean isHidden() {
        return this._hidden;
    }

    public void setHidden(boolean hidden) {
        this._hidden = hidden;

        setChildrenHidden(hidden);
    }

    public void setChildrenHidden(boolean hideChildren) {
        this._childrenHidden = hideChildren;
    }

    public float getPivotX() {
        return this._pivot.x;
    }

    public void setPivotX(float value) {
        this._pivot.x = value;
    }

    public float getPivotY() {
        return this._pivot.y;
    }

    public void setPivotY(float value) {
        this._pivot.y = value;
    }

    public float getPivotZ() {
        return this._pivot.z;
    }

    public void setPivotZ(float value) {
        this._pivot.z = value;
    }

    public boolean isHidingChildren() {
        return this._childrenHidden;
    }

    public void markScaleAsChanged() {
        this._scaleChanged = true;
    }

    public void markRotationAsChanged() {
        this._rotationChanged = true;
    }

    public void markPositionAsChanged() {
        this._positionChanged = true;
    }

    public boolean hasScaleChanged() {
        return this._scaleChanged;
    }

    public boolean hasRotationChanged() {
        return this._rotationChanged;
    }

    public boolean hasPositionChanged() {
        return this._positionChanged;
    }

    public void resetStateChanges() {
        this._scaleChanged = false;
        this._rotationChanged = false;
        this._positionChanged = false;
    }

    public AzBoneSnapshot getInitialAzSnapshot() {
        return this._initialSnapshot;
    }

    public List<AzBone> getChildBones() {
        return this._children;
    }

    public void saveInitialSnapshot() {
        if (this._initialSnapshot == null) {
            this._initialSnapshot = new AzBoneSnapshot(this);
        }
    }

    public Boolean getMirror() {
        return _metadata.mirror();
    }

    public Double getInflate() {
        return _metadata.inflate();
    }

    public Boolean shouldNeverRender() {
        return _metadata.dontRender();
    }

    public Boolean getReset() {
        return _metadata.reset();
    }

    public List<GeoCube> getCubes() {
        return this._cubes;
    }

    public boolean isTrackingMatrices() {
        return _trackingMatrices;
    }

    public void setTrackingMatrices(boolean trackingMatrices) {
        this._trackingMatrices = trackingMatrices;
    }

    public Matrix4f getModelSpaceMatrix() {
        setTrackingMatrices(true);

        return this._modelSpaceMatrix;
    }

    public void setModelSpaceMatrix(Matrix4f matrix) {
        this._modelSpaceMatrix.set(matrix);
    }

    public Matrix4f getLocalSpaceMatrix() {
        setTrackingMatrices(true);

        return this._localSpaceMatrix;
    }

    public void setLocalSpaceMatrix(Matrix4f matrix) {
        this._localSpaceMatrix.set(matrix);
    }

    public Matrix4f getWorldSpaceMatrix() {
        setTrackingMatrices(true);

        return this._worldSpaceMatrix;
    }

    public void setWorldSpaceMatrix(Matrix4f matrix) {
        this._worldSpaceMatrix.set(matrix);
    }

    public Matrix3f getWorldSpaceNormal() {
        return _worldSpaceNormal;
    }

    public void setWorldSpaceNormal(Matrix3f matrix) {
        this._worldSpaceNormal = matrix;
    }

    /**
     * Get the position of the bone relative to its owner
     */
    public Vector3d getLocalPosition() {
        Vector4f vec = getLocalSpaceMatrix().transform(new Vector4f(0, 0, 0, 1));

        return new Vector3d(vec.x(), vec.y(), vec.z());
    }

    /**
     * Get the position of the bone relative to the model it belongs to
     */
    public Vector3d getModelPosition() {
        Vector4f vec = getModelSpaceMatrix().transform(new Vector4f(0, 0, 0, 1));

        return new Vector3d(-vec.x() * 16f, vec.y() * 16f, vec.z() * 16f);
    }

    public void setModelPosition(Vector3d pos) {
        // Doesn't work on bones with parent transforms
        AzBone parent = _metadata.parent();
        Matrix4f matrix = (parent == null ? new Matrix4f().identity() : new Matrix4f(parent.getModelSpaceMatrix())).
                invert();
        Vector4f vec = matrix.transform(
            new Vector4f(-(float) pos.x / 16f, (float) pos.y / 16f, (float) pos.z / 16f, 1)
        );

        updatePosition(-vec.x() * 16f, vec.y() * 16f, vec.z() * 16f);
    }

    /**
     * Get the position of the bone relative to the world
     */
    public Vector3d getWorldPosition() {
        Vector4f vec = getWorldSpaceMatrix().transform(new Vector4f(0, 0, 0, 1));

        return new Vector3d(vec.x(), vec.y(), vec.z());
    }

    public Matrix4f getModelRotationMatrix() {
        Matrix4f matrix = new Matrix4f(getModelSpaceMatrix());
        matrix.m03(0);
        matrix.m13(0);
        matrix.m23(0);

        return matrix;
    }

    public Vector3d getPositionVector() {
        return new Vector3d(getPosX(), getPosY(), getPosZ());
    }

    public Vector3d getRotationVector() {
        return new Vector3d(getRotX(), getRotY(), getRotZ());
    }

    public Vector3d getScaleVector() {
        return new Vector3d(getScaleX(), getScaleY(), getScaleZ());
    }

    public void addRotationOffsetFromBone(AzBone source) {
        setRotX(getRotX() + source.getRotX() - source.getInitialAzSnapshot().getRotX());
        setRotY(getRotY() + source.getRotY() - source.getInitialAzSnapshot().getRotY());
        setRotZ(getRotZ() + source.getRotZ() - source.getInitialAzSnapshot().getRotZ());
    }

    public void updateRotation(float xRot, float yRot, float zRot) {
        setRotX(xRot);
        setRotY(yRot);
        setRotZ(zRot);
    }

    public void updatePosition(float posX, float posY, float posZ) {
        setPosX(posX);
        setPosY(posY);
        setPosZ(posZ);
    }

    public void updateScale(float scaleX, float scaleY, float scaleZ) {
        setScaleX(scaleX);
        setScaleY(scaleY);
        setScaleZ(scaleZ);
    }

    public void updatePivot(float pivotX, float pivotY, float pivotZ) {
        setPivotX(pivotX);
        setPivotY(pivotY);
        setPivotZ(pivotZ);
    }

    public AzBone deepCopy() {
        AzBone copy = new AzBone(this._metadata);

        // Copy basic flags
        copy._hidden = this._hidden;
        copy._childrenHidden = this._childrenHidden;

        // Copy transforms
        copy._pivot.set(this._pivot);
        copy._position.set(this._position);
        copy._rotation.set(this._rotation);
        copy._scale.set(this._scale);

        // matrices
        copy._modelSpaceMatrix.set(this._modelSpaceMatrix);
        copy._localSpaceMatrix.set(this._localSpaceMatrix);
        copy._worldSpaceMatrix.set(this._worldSpaceMatrix);

        // Copy cubes (geometry)
        copy._cubes.addAll(this._cubes); // shallow copy OK if cubes are immutable

        // Copy children recursively
        for (AzBone child : this._children) {
            copy._children.add(child.deepCopy());
        }

        // Finally, initialize a snapshot for this bone
        copy.saveInitialSnapshot();

        return copy;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        return hashCode() == obj.hashCode();
    }

    public int hashCode() {
        return Objects.hash(
            getName(),
            (getParent() != null ? getParent().getName() : 0),
            getCubes().size(),
            getChildBones().size()
        );
    }
}
