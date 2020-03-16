package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.napier.mad.components.LocalTransformComponent;
import com.napier.mad.components.ModelComponent;
import com.napier.mad.constants.GameConstants;
import com.napier.mad.types.ModelType;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LandscapeGeneratorAppState extends BaseAppState {

    private EntitySet models;
    private Map<EntityId, Node> entityLandscapeMap = new HashMap<>();
    private Node landscape = new Node("Landscape");
    private ModelLoaderAppState modelLoader;

    private Random random = new Random();

    @Override
    protected void initialize(Application app) {
        EntityData entityData = getState(EntityDataAppState.class).getEntityData();
        this.models = entityData.getEntities(ModelComponent.class, LocalTransformComponent.class);
        getState(SceneAppState.class).getSceneNode().attachChild(landscape);
        this.modelLoader = getState(ModelLoaderAppState.class);
    }

    @Override
    public void update(float tpf) {
        if (models.applyChanges()) {

            for (Entity e : models.getAddedEntities()) {
                addLandscape(e);
            }

            for (Entity e : models.getRemovedEntities()) {
                removeLandscape(e);
            }

        }
    }

    private void addLandscape(Entity e) {
        EntityId entityId = e.getId();
        ModelComponent modelComponent = e.get(ModelComponent.class);
        ModelType modelType = modelComponent.getType();
        LocalTransformComponent localTransform = e.get(LocalTransformComponent.class);

        Node entityLandscape = new Node("Landscape[" + entityId.getId() + "]");
        entityLandscape.setLocalTranslation(localTransform.getLocation());
        entityLandscape.setLocalRotation(localTransform.getRotation());
        entityLandscape.setLocalScale(localTransform.getScale());

        if (modelType == ModelType.Road_Straight || modelType == ModelType.Road_Straight_House_2) {
            // create building left and right

            Spatial leftRoad = getRandomHouseOrGrass();
            Spatial rightRoad = getRandomHouseOrGrass();
            resetTransform(leftRoad, rightRoad);

            leftRoad.setLocalTranslation(GameConstants.TILE_LENGTH, 0, 0);
            rightRoad.setLocalTranslation(-GameConstants.TILE_LENGTH, 0, 0);
            rightRoad.rotate(0, 180 * FastMath.DEG_TO_RAD, 0);

            entityLandscape.attachChild(leftRoad);
            entityLandscape.attachChild(rightRoad);

        } else if (modelType == ModelType.Road_Corner_Right || modelType == ModelType.Road_Corner_Left) {

            float[] angles = (modelType == ModelType.Road_Corner_Right)
                    ? new float[] {0, 90 * FastMath.DEG_TO_RAD, 0}
                    : new float[] {0, 0, 0};

            int factor = (modelType == ModelType.Road_Corner_Right) ? 1 : -1;

            Spatial leftTrans = modelLoader.getModel(ModelType.Hill_Corner_Transition);
            Spatial edge = modelLoader.getModel(ModelType.Hill_Corner_Edge);
            Spatial rightTrans = modelLoader.getModel(ModelType.Hill_Corner_Transition);
            resetTransform(leftTrans, edge, rightTrans);

            // set location
            leftTrans.setLocalTranslation(factor * GameConstants.TILE_LENGTH, 0, 0);
            edge.setLocalTranslation(factor * GameConstants.TILE_LENGTH, 0, GameConstants.TILE_LENGTH);
            rightTrans.setLocalTranslation(0, 0, GameConstants.TILE_LENGTH);

            // set rotation
            rightTrans.setLocalRotation(new Quaternion(angles));
            edge.setLocalRotation(new Quaternion().fromAngles(angles));
            leftTrans.setLocalRotation(new Quaternion().fromAngles(angles));

            entityLandscape.attachChild(leftTrans);
            entityLandscape.attachChild(edge);
            entityLandscape.attachChild(rightTrans);

        }

        // optimization try
        //entityLandscape = (Node) GeometryBatchFactory.optimize(entityLandscape);


        this.landscape.attachChild(entityLandscape);
        this.entityLandscapeMap.put(entityId, entityLandscape);
    }

    private Spatial getRandomHouseOrGrass() {
        double next = random.nextDouble();
        if (next < 0.2) {
            return modelLoader.getModel(ModelType.House_Mid);
        } else if (next < 0.6) {
            return modelLoader.getModel(ModelType.House_Small);
        } else if (next < 0.8) {
            return modelLoader.getModel(ModelType.House_Small_Garage);
        } else {
            return modelLoader.getModel(ModelType.House_Country);
        }
    }

    private void resetTransform(Spatial... spatials) {
        for (Spatial spatial : spatials) {
            spatial.setLocalTranslation(0, 0,0);
            spatial.setLocalRotation(new Quaternion());
            spatial.setLocalScale(1);
        }
    }

    private void removeLandscape(Entity e) {
        EntityId entityId = e.getId();
        Node entityLandscape = entityLandscapeMap.remove(entityId);
        entityLandscape.removeFromParent();
    }



    @Override
    protected void cleanup(Application app) {
        this.landscape.removeFromParent();
        this.models.release();
        this.models.clear();
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
