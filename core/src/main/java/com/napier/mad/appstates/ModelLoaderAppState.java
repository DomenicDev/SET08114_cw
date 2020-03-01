package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.napier.mad.components.ModelComponent;
import com.napier.mad.types.ModelType;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import java.util.HashMap;
import java.util.Map;

public class ModelLoaderAppState extends BaseAppState {

    private static final String ROAD_TILE_FILE = "Models/roads.j3o";

    private Map<EntityId, Node> entityModelMap = new HashMap<>();
    private EntitySet modelEntities;

    private AssetManager assetManager;

    @Override
    protected void initialize(Application app) {
        this.assetManager = app.getAssetManager();
        EntityData entityData = getState(EntityDataAppState.class).getEntityData();
        this.modelEntities = entityData.getEntities(ModelComponent.class);
    }

    @Override
    public void update(float tpf) {
        if (modelEntities.applyChanges()) {
            for (Entity e : modelEntities.getAddedEntities()) {
                addModel(e);
            }

            for (Entity e : modelEntities.getChangedEntities()) {
                removeModel(e);
            }
        }
    }

    private void addModel(Entity e) {
        EntityId entityId = e.getId();
        ModelComponent modelComp = e.get(ModelComponent.class);
        ModelType type = modelComp.getType();
        Spatial model = loadActualModel(type);
        resetTransform(model);
        Node modelNode = new Node("Entity-Node[" + entityId.getId() + "]");
        modelNode.attachChild(model);
        this.entityModelMap.put(e.getId(), modelNode);
    }

    private Spatial loadActualModel(ModelType type) {
        if (type == ModelType.Road_Straight) {
            return getChildFrom(ROAD_TILE_FILE, "road-straight.low");
        }
        if (type == ModelType.Road_Corner_Left) {
            return getChildFrom(ROAD_TILE_FILE, "road-corner-left");
        }
        if (type == ModelType.Road_Corner_Right) {
            return getChildFrom(ROAD_TILE_FILE, "road-corner-right");
        }
        if (type == ModelType.Player) {
            // for now lets create a player as cube
            Geometry playerBox = new Geometry("PlayerGeom", new Box(0.5f, 2f, 0.7f));
            Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            mat.setColor("Color", ColorRGBA.Blue);
            playerBox.setMaterial(mat);
            return playerBox;
        }
        if (type == ModelType.Car) {
            return assetManager.loadModel("Models/car.j3o");
        }
        if (type == ModelType.Empty) {
            return assetManager.loadModel("Models/Empty.j3o");
        }
        if (type == ModelType.Coin) {
            // for now lets create a player as cube
            Geometry coin = new Geometry("PlayerGeom", new Sphere(32, 32, 0.5f));
            coin.setLocalTranslation(0, 1f, 0);
            Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            mat.setColor("Color", ColorRGBA.Blue);
            coin.setMaterial(mat);
            return coin;
        }

        return null;
    }

    /**
     * Reset models transform that might be modified when loaded within a scene
     * @param model
     */
    private void resetTransform(Spatial model) {
        if (model == null) {
            return;
        }
        model.setLocalTranslation(0, 0,0 );
        model.setLocalRotation(new Quaternion(new float[]{0,0,0}));
        model.setLocalScale(Vector3f.UNIT_XYZ);
    }

    private Spatial getChildFrom(String fileName, String childName) {
        Node node = (Node) assetManager.loadModel(fileName);
        return node.getChild(childName);
    }

    public Node getModelNode(EntityId entityId) {
        return this.entityModelMap.get(entityId);
    }

    private void removeModel(Entity e) {
        this.entityModelMap.remove(e.getId());
    }

    @Override
    protected void cleanup(Application app) {
        this.modelEntities.release();
        this.modelEntities.clear();
        this.modelEntities = null;

        this.entityModelMap.clear();
        this.entityModelMap = null;
        this.assetManager.clearCache();
    }

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}
}
