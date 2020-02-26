package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import com.napier.mad.components.ModelComponent;
import com.napier.mad.components.PositionComponent;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class VisualisationAppState extends BaseAppState {

    private static final Logger LOGGER = Logger.getLogger(VisualisationAppState.class.getName());

    private static final int DEFAULT_MODEL_LOCATION_Z = 0;

    private EntitySet entities;
    private Map<EntityId, Node> models = new HashMap<>();
    private AssetManager assetManager;
    private Node modelsNode;


    @Override
    protected void initialize(Application app) {
        this.assetManager = app.getAssetManager();

        // get entity set
        AppStateManager stateManager = app.getStateManager();
        EntityData entityData = stateManager.getState(EntityDataAppState.class).getEntityData();
        this.entities = entityData.getEntities(PositionComponent.class, ModelComponent.class);

        // attach our models node to root node
         this.modelsNode = new Node("ModelsNode");
        ((SimpleApplication) app).getRootNode().attachChild(modelsNode);
    }

    @Override
    public void update(float tpf) {
        if (entities.applyChanges()) {
            for (Entity e : entities.getAddedEntities()) {
                addEntityModel(e);
            }
            for (Entity e : entities.getChangedEntities()) {
                updateEntityModel(e);
            }
            for (Entity e: entities.getRemovedEntities()) {
                removeEntityModel(e);
            }
        }
    }

    public Node getModel(EntityId entityId) {
        return this.models.get(entityId);
    }

    private void addEntityModel(Entity entity) {
        // look for type
        ModelComponent.ModelType type = entity.get(ModelComponent.class).getModelType();

        if (type == null) {
            LOGGER.warning("null defined as model type for " + entity.getId());
            return;
        }
        Node entityNode = new Node("EntityNode[" + entity.getId() + "]");
        Spatial model = null;
        if (type.equals(ModelComponent.ModelType.Ball)) {
            model = createBall();
        } else if (type.equals(ModelComponent.ModelType.Platform)) {
          model = createPlatform();
        } else {
            LOGGER.warning("no model created for entity with model type '" + type.name() + "'");
        }

        if (model == null) {
            LOGGER.warning("model is null, returning...");
        }

        entityNode.setUserData("EntityId", entity.getId().getId());
        entityNode.attachChild(model);

        this.modelsNode.attachChild(entityNode);
        this.models.put(entity.getId(), entityNode);

        // call update to apply initial position
        applyPositionToModel(entity);
    }

    private void updateEntityModel(Entity e) {
        applyPositionToModel(e);
    }

    private void removeEntityModel(Entity e) {
        EntityId entityId = e.getId();
        Spatial model = models.remove(entityId);
        if (model != null) {
            model.removeFromParent();
        }
    }


    private Spatial createBall() {
        Geometry ballGeometry = new Geometry("Ball", new Sphere(32, 32, 1f, true, false));
        ballGeometry.setMaterial(createSimpleUnshadedMaterial());
        return ballGeometry;
    }

    private Spatial createPlatform() {
        Geometry platformGeometry = new Geometry("Platform", new Box(2f, 0.1f, 2));
        platformGeometry.setMaterial(createSimpleUnshadedMaterial());
        return platformGeometry;
    }

    private Material createSimpleUnshadedMaterial() {
        // create material instance
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color", ColorRGBA.White);

        // apply texture sample
        Texture texture = assetManager.loadTexture("Interface/cat.jpg");
        material.setTexture("ColorMap", texture);
        return material;
    }

    private void applyPositionToModel(Entity entity) {
        // get scene model
        EntityId entityId = entity.getId();
        Spatial model = this.models.get(entityId);
        // apply position vector
        PositionComponent position = entity.get(PositionComponent.class);
        model.setLocalTranslation(position.getLocation());
    }

    @Override
    protected void cleanup(Application app) {
        models.clear();
        entities.release();
        entities.clear();

        this.modelsNode.removeFromParent();
        this.modelsNode = null;
        this.assetManager = null;
    }

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}
}
