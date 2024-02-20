package com.flanks255.simplyutilities.tweaks;

import com.flanks255.simplyutilities.SUTags;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.event.entity.EntityMobGriefingEvent;

public class MobGriefProtection {
    public static void mobGriefingEvent(EntityMobGriefingEvent event) {
        if (event.getEntity() != null && event.getEntity().getType().is(SUTags.NO_GRIEFING))
            event.setResult(Event.Result.DENY);
    }
}
