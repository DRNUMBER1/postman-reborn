package me.srgantmoomoo.postman.client.setting;

import me.srgantmoomoo.postman.client.module.Module;
import me.srgantmoomoo.postman.client.module.ModuleManager;

import java.util.ArrayList;

/*
 * Written originally by @HeroCode.
 * Edited by @SrgantMooMoo on 11/17/20 with inspiration taken from @Sebsb.
 */

public class SettingManager {
	
	private final ArrayList<Setting> settings;
	
	public SettingManager(){
		this.settings = new ArrayList <>();
	}
	
	public void rSetting(Setting in){
		this.settings.add(in);
	}
	
	public ArrayList<Setting> getSettings() {
		return this.settings;
	}
	
	public ArrayList<Setting> getSettingsByMod(Module mod) {
		ArrayList<Setting> out = new ArrayList <>();
		for(Setting s : getSettings()) {
			if(s.parent.equals(mod)) {
				out.add(s);
			}
		}
		if(out.isEmpty()) {
			return null;
		}
		return out;
	}
	
	public Setting getSettingByName(Module mod, String name) {
		for (Module m : ModuleManager.modules) {
		for (Setting set : m.settings) {
			if (set.name.equalsIgnoreCase(name) && set.parent == mod) {
				return set;
			}
			}
		}
		System.err.println("[postman] Error Setting NOT found: '" + name +"'!");
		return null;
	}
}