package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.FastMath;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.napier.mad.components.PositionComponent;
import com.napier.mad.components.TileTypeComponent;
import com.napier.mad.types.TileOrientation;
import com.napier.mad.types.TileType;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;

import java.util.logging.Logger;

public class TileModelLoaderAppState extends BaseAppState {

    private static final Logger LOGGER = Logger.getLogger(TileModelLoaderAppState.class.getName());

    private EntitySet tileEntities;

    private Node roadsScene;

    private Node tileNode;

    @Override
    protected void initialize(Application app) {
        EntityData entityData = getState(EntityDataAppState.class).getEntityData();
        this.tileEntities = entityData.getEntities(TileTypeComponent.class, PositionComponent.class);

        this.roadsScene = (Node) app.getAssetManager().loadModel("Models/roads.j3o");

        tileNode = new Node("TileNode");
        getState(SceneAppState.class).getSceneNode().attachChild(tileNode);
    }

    @Override
    public void update(float tpf) {
        if (tileEntities.applyChanges()) {

            for (Entity e : tileEntities.getAddedEntities()) {
                createTileModel(e);
            }

        }
    }

    private void createTileModel(Entity e) {
        TileTypeComponent tileComponent = e.get(TileTypeComponent.class);
        TileType type = tileComponent.getType();

        // load model for tile type
        Spatial tileModel = getTileModel(type);
        if (tileModel == null) {
            LOGGER.warning("no model created for type: " + type);
            return;
        }
        // add entity id as user data for later identification
        tileModel.setUserData("EntityId", e.getId().getId());

        // rotate model according to orientation
        TileOrientation orientation = tileComponent.getOrientation();
        rotateModel(tileModel, orientation);

        PositionComponent positionComponent = e.get(PositionComponent.class);
        tileModel.setLocalTranslation(positionComponent.getLocation());

        tileNode.attachChild(tileModel);
    }

    private void rotateModel(Spatial spatial, TileOrientation orientation) {
        if (orientation == TileOrientation.North) {
            spatial.rotate(0, FastMath.DEG_TO_RAD * 90, 0);
        } else if (orientation == TileOrientation.East) {
            spatial.rotate(0, FastMath.DEG_TO_RAD * 0, 0);
        } else if (orientation == TileOrientation.South) {
            spatial.rotate(0, FastMath.DEG_TO_RAD * 90, 0);
        } else if (orientation == TileOrientation.West) {
            spatial.rotate(0, FastMath.DEG_TO_RAD * -180, 0);
        }
    }

    private Spatial getTileModel(TileType type) {
        if (type == null) {
            return null;
        }
        String nodeName = null;
        if (type == TileType.Straight) {
            nodeName = "road-straight.low";
        } else if (type == TileType.Curve) {
            nodeName = "road-corner";
        }

        return roadsScene.getChild(nodeName).clone();
    }

    @Override
    protected void cleanup(Application app) {

    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
