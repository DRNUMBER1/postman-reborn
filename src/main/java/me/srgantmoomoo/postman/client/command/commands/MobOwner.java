package me.srgantmoomoo.postman.client.command.commands;

import me.srgantmoomoo.postman.api.util.world.EntityUtil;
import me.srgantmoomoo.postman.client.command.Command;
import me.srgantmoomoo.postman.client.command.CommandManager;
import me.srgantmoomoo.postman.client.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.util.text.TextFormatting;

import java.util.Objects;

public class MobOwner extends Command {
	
    public MobOwner() {
		super("mobOwner", "check the owner of a ridden mob.", "mobOwner", "mo");
	}

	@Override
	public void onCommand(String[] args, String command) {
		if(args.length == 0) {
			 if (Minecraft.getMinecraft().player.getRidingEntity() != null && Minecraft.getMinecraft().player.getRidingEntity() instanceof AbstractHorse) {
				 AbstractHorse horse = (AbstractHorse) Minecraft.getMinecraft().player.getRidingEntity();
				 
				 String ownerUUID = horse.getOwnerUniqueId() == null ? "entity has no owner" : horse.getOwnerUniqueId().toString();
				 
				 try {
					 String ownerReplace = Objects.requireNonNull(EntityUtil.getNameFromUUID(ownerUUID)).replace("\"", "");
					 ModuleManager.addChatMessage("mob owner is " + TextFormatting.GREEN + ownerReplace);
					}catch (Exception e) {
						ModuleManager.addChatMessage("something went wrong, this entity may not have a real owner.");
					}
		        }else {
		        	ModuleManager.addChatMessage("ridden entity is not compatible with this command");
		        }
		}else CommandManager.correctUsageMsg(getName(), getSyntax());
	}
}