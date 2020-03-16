package com.napier.mad.appstates;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.animation.SkeletonControl;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Node;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;
import com.napier.mad.components.JumpComponent;
import com.napier.mad.components.ModelComponent;
import com.napier.mad.types.ModelType;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;
import com.simsilica.es.filter.FieldFilter;

import java.util.HashMap;
import java.util.Map;

public class HumanAnimationAppState extends BaseAppState {

    private EntitySet animatedHumans;
    private ModelLoaderAppState modelLoaderAppState;
    private Map<EntityId, AnimChannel> entityIdAnimChannelMap = new HashMap<>();

    private static final String ANIM_RUN = "Run";
    private static final String ANIM_JUMP = "Jump";

    @Override
    protected void initialize(Application app) {
        EntityData entityData = getState(EntityDataAppState.class).getEntityData();
        this.animatedHumans = entityData.getEntities(new FieldFilter<>(ModelComponent.class, "type", ModelType.Human), ModelComponent.class, JumpComponent.class);

        this.modelLoaderAppState = getState(ModelLoaderAppState.class);
    }

    @Override
    public void update(float tpf) {

        if (animatedHumans.applyChanges()) {

            for (Entity e : animatedHumans.getAddedEntities()) {
                add(e);
            }

            for (Entity e : animatedHumans.getChangedEntities()) {
                update(e);
            }

        }

    }

    private void add(Entity e) {
        Node humanNode = modelLoaderAppState.getModelNode(e.getId());
        humanNode.depthFirstTraversal(new SceneGraphVisitor() {
            @Override
            public void visit(Spatial spatial) {
                SkeletonControl skeletonControl = spatial.getControl(SkeletonControl.class);
                if (skeletonControl != null) {
                    skeletonControl.setHardwareSkinningPreferred(false);
                }
                AnimControl animControl = spatial.getControl(AnimControl.class);
                if (animControl != null) {
                    AnimChannel root = animControl.createChannel();
                    root.setAnim(ANIM_RUN);
                    entityIdAnimChannelMap.put(e.getId(), root);
                }
            }
        });
    }

    private void update(Entity e) {
        AnimChannel channel = entityIdAnimChannelMap.get(e.getId());

        JumpComponent jumpComponent = e.get(JumpComponent.class);
        if (jumpComponent.isJumping()) {
            channel.setAnim(ANIM_JUMP);
            channel.setLoopMode(LoopMode.DontLoop);
        } else {
            channel.setAnim(ANIM_RUN);
        }
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
