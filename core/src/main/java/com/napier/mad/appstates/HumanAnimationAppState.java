package com.napier.mad.appstates;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Node;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;
import com.napier.mad.components.ModelComponent;
import com.napier.mad.types.ModelType;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;
import com.simsilica.es.filter.FieldFilter;

public class HumanAnimationAppState extends BaseAppState {

    private EntitySet animatedHumans;
    private ModelLoaderAppState modelLoaderAppState;

    @Override
    protected void initialize(Application app) {
        EntityData entityData = getState(EntityDataAppState.class).getEntityData();
        this.animatedHumans = entityData.getEntities(new FieldFilter<>(ModelComponent.class, "type", ModelType.Human), ModelComponent.class);

        this.modelLoaderAppState = getState(ModelLoaderAppState.class);
    }

    @Override
    public void update(float tpf) {

        if (animatedHumans.applyChanges()) {

            for (Entity e : animatedHumans.getAddedEntities()) {
                add(e);
            }

        }

    }

    private void add(Entity e) {
        Node humanNode = modelLoaderAppState.getModelNode(e.getId());
        humanNode.depthFirstTraversal(new SceneGraphVisitor() {
            @Override
            public void visit(Spatial spatial) {
                AnimControl animControl = spatial.getControl(AnimControl.class);
                if (animControl != null) {
                    AnimChannel root = animControl.createChannel();
                    root.setAnim("Run");
                }
            }
        });
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
