package org.vrglab.azure.azurelib.common.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

/**
 * A utility class for analyzing the movement of an {@link Entity} in the game world.
 * <p>
 * This class tracks the movement of an entity by calculating its position deltas over time. It provides methods to
 * check if the entity is moving horizontally, vertically, or overall in any direction.
 * </p>
 */
public class MoveAnalysis {

    /**
     * The {@link Entity} whose movement is being analyzed.
     */
    private final Entity _entity;

    /**
     * The tick count of the last update. Used to avoid redundant updates.
     */
    private int _lastTick;

    /**
     * The position of the entity at the last update.
     */
    private Vec3 _lastPosition;

    /**
     * The change in the entity’s X-coordinate since the last update.
     */
    private double _deltaX;

    /**
     * The change in the entity’s Y-coordinate since the last update.
     */
    private double _deltaY;

    /**
     * The change in the entity’s Z-coordinate since the last update.
     */
    private double _deltaZ;

    /**
     * Constructs a new {@code MoveAnalysis} instance for the specified entity.
     *
     * @param entity The {@link Entity} to analyze.
     */
    public MoveAnalysis(Entity entity) {
        this._entity = entity;
        this._lastPosition = entity.position();
    }

    /**
     * Updates the movement analysis with the entity's current position.
     * <p>
     * This method calculates the change in position (delta) for each axis (X, Y, Z) since the last update. The update
     * only occurs if the entity's tick count has increased since the last recorded tick.
     * </p>
     */
    public void update() {
        if (_entity.tickCount == _lastTick) {
            // Only update on tick differences.
            return;
        }

        var prevPos = _lastPosition;
        var prevPosX = prevPos.x;
        var prevPosY = prevPos.y;
        var prevPosZ = prevPos.z;

        var pos = _entity.position();
        var posX = pos.x;
        var posY = pos.y;
        var posZ = pos.z;

        this._deltaX = posX - prevPosX;
        this._deltaY = posY - prevPosY;
        this._deltaZ = posZ - prevPosZ;

        this._lastPosition = _entity.position();
        this._lastTick = _entity.tickCount;
    }

    /**
     * Checks if the entity is moving horizontally.
     * <p>
     * Horizontal movement is defined as any change in the X or Z coordinates.
     * </p>
     *
     * @return {@code true} if the entity is moving horizontally; {@code false} otherwise.
     */
    public boolean isMovingHorizontally() {
        return _deltaX != 0 || _deltaZ != 0;
    }

    /**
     * Checks if the entity is moving vertically.
     * <p>
     * Vertical movement is defined as any change in the Y coordinate.
     * </p>
     *
     * @return {@code true} if the entity is moving vertically; {@code false} otherwise.
     */
    public boolean isMovingVertically() {
        return _deltaY != 0;
    }

    /**
     * Checks if the entity is moving in any direction.
     * <p>
     * This includes both horizontal and vertical movement.
     * </p>
     *
     * @return {@code true} if the entity is moving; {@code false} otherwise.
     */
    public boolean isMoving() {
        return isMovingHorizontally() || isMovingVertically();
    }
}
