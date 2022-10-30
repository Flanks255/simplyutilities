package com.flanks255.simplyutilities.tweaks;

import com.flanks255.simplyutilities.SUTags;
import net.minecraftforge.event.entity.EntityMobGriefingEvent;
import net.minecraftforge.eventbus.api.Event;

public class MobGriefProtection {
    public static void mobGriefingEvent(EntityMobGriefingEvent event) {
        if (event.getEntity() != null && SUTags.NO_GRIEFING.contains(event.getEntity().getType()))
            event.setResult(Event.Result.DENY);
    }
}
