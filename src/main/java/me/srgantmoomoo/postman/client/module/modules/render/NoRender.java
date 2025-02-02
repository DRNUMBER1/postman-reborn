package me.srgantmoomoo.postman.client.module.modules.render;

import me.srgantmoomoo.postman.api.event.Event.Era;
import me.srgantmoomoo.postman.api.event.events.*;
import me.srgantmoomoo.postman.client.module.Category;
import me.srgantmoomoo.postman.client.module.Module;
import me.srgantmoomoo.postman.client.setting.settings.BooleanSetting;
import me.srgantmoomoo.postman.client.setting.settings.ModeSetting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketSpawnMob;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent.OverlayType;
import org.lwjgl.input.Keyboard;

public class NoRender extends Module {
	public final BooleanSetting rain = new BooleanSetting("rain", this, false);
	public final BooleanSetting skylight = new BooleanSetting("skylightUpdates", this, false);
	public final ModeSetting hurtCam = new ModeSetting("hurtCam", this, "yesHurtCam", "yesHurtCam", "noHurtCam", "penis");
	public final BooleanSetting fire = new BooleanSetting("fire", this, false);
	public final BooleanSetting portalEffect = new BooleanSetting("portalEffect", this, false);
	public final BooleanSetting potionIndicators = new BooleanSetting("potionIndicators", this, false);
	public final BooleanSetting crystals = new BooleanSetting("crystals", this, false);
	public final BooleanSetting totemAnimation = new BooleanSetting("totemAnimation", this, false);
	public final BooleanSetting enchantTables = new BooleanSetting("encahtTables", this, false);
	public final BooleanSetting armor = new BooleanSetting("armor", this, false);
	public final BooleanSetting tnt = new BooleanSetting("tnt", this, false);
	public final BooleanSetting items = new BooleanSetting("items", this, false);
	public final BooleanSetting withers = new BooleanSetting("withers", this, false);
	public final BooleanSetting skulls = new BooleanSetting("skulls", this, false);
	public final BooleanSetting fireworks = new BooleanSetting("fireworks", this, false);
	
	public BooleanSetting particles = new BooleanSetting("particles", this, false);
	public BooleanSetting signs = new BooleanSetting("signs", this, false);
	public BooleanSetting pistons = new BooleanSetting("pistons", this, false);
		
	public NoRender() {
		super("noRender", "stops certain events from rendering.", Keyboard.KEY_NONE, Category.RENDER);
		this.addSettings(rain, skylight, hurtCam, fire, portalEffect, potionIndicators, crystals, totemAnimation, enchantTables, armor, tnt, items, withers, skulls, fireworks);
	}

	@Override
	public void onDisable() {
		GuiIngameForge.renderPortal = true;
	}
	
	@Override
	public void onUpdate() {
		// hurtCam penis mode
		if(hurtCam.is("penis")) {
			mc.player.performHurtAnimation();
		}
		
		// portalEffect
		if(portalEffect.isEnabled()) {
			GuiIngameForge.renderPortal = false;
			mc.player.removeActivePotionEffect(MobEffects.NAUSEA);
		}
	}
	
	// rain
	@EventHandler
	private final Listener<RenderRainEvent> onRain = new Listener<>(event -> {
		if(rain.isEnabled()) {
		    if (mc.world == null)
		        return;
		    event.cancel();
		}
	});
	
	// totem animation
	@EventHandler
    private final Listener<NetworkPacketEvent> PacketEvent = new Listener<>(event -> {
        if (mc.world == null || mc.player == null) return;
        if (event.getPacket() instanceof SPacketEntityStatus) {
            SPacketEntityStatus packet = (SPacketEntityStatus)event.getPacket();
            if (packet.getOpCode() == 35) {
                if (totemAnimation.isEnabled())
                    event.cancel();
            }
        }
    });
	
	// fire
	@EventHandler
    private final Listener<RenderBlockOverlayEvent> OnBlockOverlayEvent = new Listener<>(event -> {
        if (fire.isEnabled() && event.getOverlayType() == OverlayType.FIRE) event.setCanceled(true);
    });
	
	// crystals, tnt, items, withers, skulls, and fireworks
	
	@EventHandler
	private final Listener<PacketEvent.Receive> onReceivePacket = new Listener<>(event -> {
		 if (event.getEra() == Era.PRE) {
	            if (event.getPacket() instanceof SPacketSpawnMob) {
	                final SPacketSpawnMob packet = (SPacketSpawnMob) event.getPacket();

	                if (this.skulls.isEnabled()) {
	                    if (packet.getEntityType() == 19) {
	                        event.cancel();
	                    }
	                }
	            }
		 }
	});
	
	@EventHandler
	private final Listener<RenderEntityEvent> onRenderEntity = new Listener<>(event -> {
			if(crystals.isEnabled()) {
				if (event.getEntity() instanceof EntityEnderCrystal) event.cancel();
			}
			
			if(tnt.isEnabled()) {
				if (event.getEntity() instanceof EntityTNTPrimed) event.cancel();
			}
			
			if(items.isEnabled()) {
				if (event.getEntity() instanceof EntityItem) event.cancel();
			}
			
			if(withers.isEnabled()) {
				if (event.getEntity() instanceof EntityWither) event.cancel();
			}
			
			if(skulls.isEnabled()) {
				if (event.getEntity() instanceof EntityWitherSkull) event.cancel();
			}
			
			if(fireworks.isEnabled()) {
				if (event.getEntity() instanceof EntityFireworkRocket) event.cancel();
			}		
        
	});
	@EventHandler
	private final Listener<SpawnEffectEvent> onSpawnEffectParticle = new Listener<>(event -> {
		if (fireworks.isEnabled()) {
            if (event.getParticleID() == EnumParticleTypes.FIREWORKS_SPARK.getParticleID() || event.getParticleID() == EnumParticleTypes.EXPLOSION_HUGE.getParticleID() ||
            		event.getParticleID() == EnumParticleTypes.EXPLOSION_LARGE.getParticleID() || event.getParticleID() == EnumParticleTypes.EXPLOSION_NORMAL.getParticleID()) {
                event.cancel();
            }
        }
	});
	
	@EventHandler
	private final Listener<AddEntityEvent> onEntityAdd = new Listener<>(event -> {
		if (fireworks.isEnabled()) {
            if (event.getEntity() instanceof EntityFireworkRocket) {
                event.cancel();
            }
        }

        if (skulls.isEnabled()) {
            if (event.getEntity() instanceof EntityWitherSkull) {
                event.cancel();
            }
        }

        if (tnt.isEnabled()) {
            if (event.getEntity() instanceof EntityTNTPrimed) {
                event.cancel();
            }
        }
	});
	
	// hurtCam = MixinEntityRenderer
	// potionEffect = mixin... some sorta overlay idk
	// skylight = MixinWorld
	// armor = MixinLayerBipedArmor
	
}
