package me.srgantmoomoo.postman.api.save;

import me.srgantmoomoo.Main;
import me.srgantmoomoo.Reference;
import me.srgantmoomoo.postman.client.command.CommandManager;
import me.srgantmoomoo.postman.client.module.Module;
import me.srgantmoomoo.postman.client.module.ModuleManager;
import me.srgantmoomoo.postman.client.setting.Setting;
import me.srgantmoomoo.postman.client.setting.settings.BooleanSetting;
import me.srgantmoomoo.postman.client.setting.settings.ColorSetting;
import me.srgantmoomoo.postman.client.setting.settings.ModeSetting;
import me.srgantmoomoo.postman.client.setting.settings.NumberSetting;
import net.minecraft.client.Minecraft;

import java.io.*;
import java.util.ArrayList;

/*
 * Written by @SrgantMooMoo on 11/30/20 with inspiration taken from @SebSb.
 */

public class SaveLoad {

	private final File dataFile;
	   
	public SaveLoad() {
		File dir = new File(Minecraft.getMinecraft().gameDir, Reference.NAME);
		if(!dir.exists()) {
			dir.mkdir();
		}
		dataFile = new File(dir, "config.txt");
		if(!dataFile.exists()) {
			try {
				dataFile.createNewFile();
			} catch (IOException e) {e.printStackTrace();}
		}
		
		this.load();
	}
	
	public void save() {
		ArrayList<String> toSave = new ArrayList <>();
		
		// modules and keybinds
		for(Module mod : ModuleManager.modules) {
			if(!mod.getName().equals("tabGui"))
			toSave.add("MOD:" + mod.getName() + ":" + mod.isToggled() + ":" + mod.getKey());
		}
		
		// settings
		for(Module mod : ModuleManager.modules) {
			for(Setting setting : mod.settings) {
				
				if(setting instanceof BooleanSetting) {
					BooleanSetting bool = (BooleanSetting) setting;
					toSave.add("SET:" + mod.getName() + ":" + setting.name + ":" + bool.isEnabled());
					}
				
				if(setting instanceof NumberSetting) {
					NumberSetting numb = (NumberSetting) setting;
					toSave.add("SET:" + mod.getName() + ":" + setting.name + ":" + numb.getValue());
				}
				
				if(setting instanceof ModeSetting) {
					ModeSetting mode = (ModeSetting) setting;
					toSave.add("SET:" + mod.getName() + ":" + setting.name + ":" + mode.getMode());
				}
				
				if(setting instanceof ColorSetting) {
					ColorSetting color = (ColorSetting) setting;
					toSave.add("SET:" + mod.getName() + ":" + setting.name + ":" + color.toInteger() + ":" + color.getRainbow());
				}
			}
		}
		
		// command prefix
		toSave.add("COMMANDPREFIX:" + CommandManager.prefix);
		
		/* friends
		List<String> friends = FriendManager.getFriendsByName();
		String friendsReplace = friends.toString();
		String friendsReep = friendsReplace.replaceAll("[]", "");
		toSave.add("FRIENDS:" + friendsReep);*/
		
		try {
			PrintWriter pw = new PrintWriter(this.dataFile);
			for(String str : toSave) {
				pw.println(str);
			}
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void load() {
		ArrayList<String> lines = new ArrayList <>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.dataFile));
			String line = reader.readLine();
			while(line != null) {
				lines.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch(Exception e) {
			e.printStackTrace();
			}
		
		for(String s : lines) {
			String[] args = s.split(":");
			if(s.toLowerCase().startsWith("mod:")) {
				Module m = Main.moduleManager.getModule(args[1]);
				if(m != null) {
					// hud modules
					if(m.getName().equals("clickGui")) m.setToggled(false);
					if(m.getName().equals("hudEditor")) m.setToggled(false);
					// normal modules that can cause crashes
					if(m.getName().equals("blink")) m.setToggled(false);
					if(m.getName().equals("autoDisconnect")) m.setToggled(false);
					
					
					if(!m.getName().equals("clickGui") 
							&& !m.getName().equals("hudEditor") 
							&& !m.getName().equals("blink") 
							&& !m.getName().equals("autoDisconnect")) {
						m.setToggled(Boolean.parseBoolean(args[2]));
						m.setKey(Integer.parseInt(args[3]));
					}
				}
			}else if(s.toLowerCase().startsWith("set:")) {
				Module m = Main.moduleManager.getModule(args[1]);
				if(m != null) {
					Setting setting = Main.settingManager.getSettingByName(m,args[2]);
					if(setting != null) {
						if(setting instanceof BooleanSetting) {
							((BooleanSetting)setting).setEnabled(Boolean.parseBoolean(args[3]));
						}
						if(setting instanceof NumberSetting) {
							((NumberSetting)setting).setValue(Double.parseDouble(args[3]));
						}
						if(setting instanceof ModeSetting && ((ModeSetting) setting).modes.toString().contains(args[3])) { // u have to make sure the mode getting loaded actually still exists or else u will have angry mob of ppl telling u ur config is fucking garbage... but actually yes ur config is fucking garbage because u wrote it when u were fucking monke and didn't know wtf u were doing, like seriously come on now, who the fuck writes a config in a normal fucking txt file, r u fucking stupid??????? like just do it in fucking json u fucking dumb cunt.
							((ModeSetting)setting).setMode(args[3]);
						}
						if(setting instanceof ColorSetting) {
							((ColorSetting)setting).fromInteger(Integer.parseInt(args[3]));
							((ColorSetting)setting).setRainbow(Boolean.parseBoolean(args[4]));
						}
					}
				}
			}else if(s.toLowerCase().startsWith("commandprefix:")) {
				CommandManager.setCommandPrefix(args[1]);
			}/*else if(s.toLowerCase().startsWith("friends:")) {
				FriendManager.addFriend(args[1]);
			}*/
		}
	}
}