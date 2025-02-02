package me.srgantmoomoo.postman.client.module;

import com.lukflug.panelstudio.settings.Toggleable;
import me.srgantmoomoo.Main;
import me.srgantmoomoo.postman.api.event.events.RenderEvent;
import me.srgantmoomoo.postman.client.setting.Setting;
import me.srgantmoomoo.postman.client.setting.settings.KeybindSetting;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/*
 * Written by @SrgantMooMoo on 11/17/20.
 */

public abstract class Module implements Toggleable {
	
	protected static final Minecraft mc = Minecraft.getMinecraft();
	public static ArrayList<Module> modules;
	
	public final String name;
    public String description;
	public final KeybindSetting keyCode = new KeybindSetting(0);
	public final Category category;
	public boolean toggled;
	public boolean expanded;
	public int index;
	public final List<Setting> settings = new ArrayList <>();
	
	public Module(String name, String description, int key, Category category) {
		super();
		this.name = name;
		this.description = description;
		keyCode.code = key;
		this.addSettings(keyCode);
		this.category = category;
		this.toggled = false;
	}
	public void addSettings(Setting... settings) {
		this.settings.addAll(Arrays.asList(settings));
		this.settings.sort(Comparator.comparingInt(s -> s == keyCode ? 1 : 0));
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getKey() {
		return keyCode.code;
	}
	
	public void setKey(int key) {
		this.keyCode.code = key;
		
		 if(Main.saveLoad != null) {
				Main.saveLoad.save();
			}
	} 
	
	public String getName() {
		return this.name;
	}
	
	public Category getCategory() {
		return this.category;
	}
	
	public final boolean isOn() {
		return toggled;
	}
	
	public void toggle() {
		toggled = !toggled;
		if(toggled) {
			enable();
		}else {
			disable();
		}
		
		if(Main.saveLoad != null) {
			Main.saveLoad.save();
		}
	}
	
	public boolean isToggled() {
		return toggled;
	}
	
	public void setToggled(boolean toggled) {
		this.toggled = toggled;
		if(toggled) {
			Main.EVENT_BUS.subscribe(this);
		}else {
			Main.EVENT_BUS.unsubscribe(this);
		}
		
		if(Main.saveLoad != null) {
			Main.saveLoad.save();
		}
	}
	
	protected void enable() {
		onEnable();
		setToggled(true);
	}
	
	protected void disable() {
		onDisable();
		setToggled(false);
	}
	
	protected void onEnable() {
		
	}
	
	protected void onDisable() {
		
	}
	
	public void onWorldRender(RenderEvent event) {
		
	}
	
	public void onUpdate() {
		
	}
	
	public void onRender() {
		
	}
}
