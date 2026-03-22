package org.vrglab.azure.azurelib.common.model;

import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Represents a baked 3D model consisting of hierarchical bone structures. This class is immutable and provides
 * read-only access to bones by name or as a list of top-level bones. Bones are uniquely identified by their names.
 */
public class AzBakedModel {

    private static AzBakedModel defaultModel = new AzBakedModel(List.of());

    private final Map<String, AzBone> _bonesByName;

    private final List<AzBone> _topLevelBones;

    public AzBakedModel(List<AzBone> topLevelBones) {
        this._topLevelBones = Collections.unmodifiableList(topLevelBones);
        this._bonesByName = Collections.unmodifiableMap(mapBonesByName(topLevelBones));
    }

    private Map<String, AzBone> mapBonesByName(List<AzBone> bones) {
        var bonesByName = new HashMap<String, AzBone>();
        var nodesToMap = new ArrayDeque<>(bones);

        while (!nodesToMap.isEmpty()) {
            var currentBone = nodesToMap.poll();
            nodesToMap.addAll(currentBone.getChildBones());
            currentBone.saveInitialSnapshot();
            bonesByName.put(currentBone.getName(), currentBone);
        }

        return bonesByName;
    }

    public AzBakedModel deepCopy() {
        List<AzBone> copied = new ArrayList<>(this._topLevelBones.size());
        for (AzBone bone : this._topLevelBones) {
            copied.add(bone.deepCopy()); // each child deepCopy() calls saveInitialSnapshot()
        }
        return new AzBakedModel(copied); // this will rebuild bonesByName internally
    }

    public @Nullable AzBone getBoneOrNull(String name) {
        return _bonesByName.get(name);
    }

    public Optional<AzBone> getBone(String name) {
        return Optional.ofNullable(getBoneOrNull(name));
    }

    public Map<String, AzBone> getBonesByName() {
        return _bonesByName;
    }

    public List<AzBone> getTopLevelBones() {
        return _topLevelBones;
    }

    public static AzBakedModel getDefault() {
        return defaultModel;
    }

    public static void setDefault(AzBakedModel model) {
        defaultModel = model != null ? model : new AzBakedModel(List.of());
    }
}
