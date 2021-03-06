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
import com.napier.mad.components.ModelComponent;
import com.napier.mad.types.ModelType;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import java.util.HashMap;
import java.util.Map;

public class ModelLoaderAppState extends BaseAppState {

    public static final String ROAD_TILE_FILE = "Models/roads.j3o";
    public static final String SUBURB_TILE_FILE = "Models/buildings.j3o";
    public static final String LANDSCAPE_TITLE_FILE = "Models/landscape.j3o";

    private Map<EntityId, Node> entityModelMap = new HashMap<>();
    private EntitySet modelEntities;

    private Map<ModelType, Spatial> loadedModelMap = new HashMap<>();

    private AssetManager assetManager;

    @Override
    protected void initialize(Application app) {
        this.assetManager = app.getAssetManager();
        EntityData entityData = getState(EntityDataAppState.class).getEntityData();
        this.modelEntities = entityData.getEntities(ModelComponent.class);
        for (Entity e : modelEntities) {
            addModel(e);
        }
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
        Spatial model = getModel(type);
        resetTransform(model);
        Node modelNode = new Node("Entity-Node[" + entityId.getId() + "]");
        modelNode.attachChild(model);
        this.entityModelMap.put(e.getId(), modelNode);
    }

    public Spatial getModel(ModelType type) {
        if (loadedModelMap.containsKey(type)) {
            return loadedModelMap.get(type).clone();
        }

        Spatial spatial = loadFromAssetManager(type);
        if (spatial != null) {
            loadedModelMap.put(type, spatial);
            return spatial.clone();
        }
        return null;
    }

    private Spatial loadFromAssetManager(ModelType type) {
        if (type == ModelType.Road_Straight) {
            return getChildFrom(ROAD_TILE_FILE, "road-straight.low");
        }
        if (type == ModelType.Road_Corner_Left) {
            return getChildFrom(ROAD_TILE_FILE, "road-corner-left");
        }
        if (type == ModelType.Road_Corner_Right) {
            return getChildFrom(ROAD_TILE_FILE, "road-corner-right");
        }
        if (type == ModelType.Road_Straight_House_2) {
            return getChildFrom(SUBURB_TILE_FILE, "road-straight-house-2-low");
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
            return assetManager.loadModel("Models/Coins.j3o");
        }
        if (type == ModelType.Human) {
            return assetManager.loadModel("Models/Human.j3o");
        }
        if (type == ModelType.House_Country) {
            return getChildFrom(SUBURB_TILE_FILE, "house-country");
        }
        if (type == ModelType.House_Modern) {
            return getChildFrom(SUBURB_TILE_FILE, "house-modern");
        }
        if (type == ModelType.House_Mid) {
            return getChildFrom(SUBURB_TILE_FILE, "house-mid");
        }
        if (type == ModelType.House_Small_Garage) {
            return getChildFrom(SUBURB_TILE_FILE, "house-small-garage");
        }
        if (type == ModelType.House_Small) {
            return getChildFrom(SUBURB_TILE_FILE, "house-small");
        }
        if (type == ModelType.Hill_Corner_Edge) {
            return getChildFrom(LANDSCAPE_TITLE_FILE, "hill-corner2");
        }
        if (type == ModelType.Hill_Corner_Transition) {
            return getChildFrom(LANDSCAPE_TITLE_FILE, "hill-corner");
        }
        if (type == ModelType.Hill_Grass) {
            return getChildFrom(LANDSCAPE_TITLE_FILE, "hill-grass");
        }
        if (type == ModelType.Grass_Low) {
            return getChildFrom(LANDSCAPE_TITLE_FILE, "grass-low");
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
        this.assetManager.clearCache();
        this.loadedModelMap.clear();
    }

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}
}
