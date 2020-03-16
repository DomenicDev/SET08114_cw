package com.napier.mad.components;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

public class ItemCollectedEventComponent implements EntityComponent {

    private EntityId collectorId;
    private EntityId itemId;

    public ItemCollectedEventComponent(EntityId collectorId, EntityId itemId) {
        this.collectorId = collectorId;
        this.itemId = itemId;
    }

    public EntityId getCollectorId() {
        return collectorId;
    }

    public EntityId getItemId() {
        return itemId;
    }
}
