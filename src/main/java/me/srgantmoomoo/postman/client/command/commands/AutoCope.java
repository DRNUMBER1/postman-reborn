package me.srgantmoomoo.postman.client.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.srgantmoomoo.postman.client.command.Command;
import me.srgantmoomoo.postman.client.command.CommandManager;
import me.srgantmoomoo.postman.client.module.ModuleManager;

public class AutoCope extends Command {
	
    public AutoCope() {
		super("autoCope", "edit the autoCope msg.", "autoCope <msg>", "ac");
	}
    
	@Override
	public void onCommand(String[] args, String command) {
		if(args.length >= 1) {
			StringBuilder msg = new StringBuilder();
            boolean flag = true;
            for (String string : args) {
                if (flag) {
                    flag = false;
                    continue;
                }
                msg.append(string).append(" ");
            }
			
			me.srgantmoomoo.postman.client.module.modules.pvp.AutoCope.setMessage(args[0] + " " + msg);
			ModuleManager.addChatMessage("set autoCope message to " + ChatFormatting.GREEN + args[0] + " " + msg + ChatFormatting.GRAY + ".");
		}else CommandManager.correctUsageMsg(getName(), getSyntax());
	}
}
