package me.srgantmoomoo.postman.client.module.modules.hud;

import com.lukflug.panelstudio.hud.HUDList;
import com.lukflug.panelstudio.hud.ListComponent;
import com.lukflug.panelstudio.theme.Theme;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.srgantmoomoo.Reference;
import me.srgantmoomoo.postman.api.util.render.JColor;
import me.srgantmoomoo.postman.client.module.Category;
import me.srgantmoomoo.postman.client.module.HudModule;
import me.srgantmoomoo.postman.client.setting.settings.ColorSetting;

import java.awt.*;


public class Welcomer extends HudModule {
	public final ColorSetting color = new ColorSetting("color", this, new JColor(Reference.POSTMAN_COLOR, 255));

	public Welcomer() {
		super("welcomer", "welcomes u to postman.", new Point(75, 70), Category.HUD);
		this.addSettings(color);
	}
	
	@Override
	public void populate (Theme theme) {
		component = new ListComponent(getName(), theme.getPanelRenderer(), position, new WelcomerList());
	}
	
	private class WelcomerList implements HUDList {

		@Override
		public int getSize() {
			return 1;
		}

		@Override
		public String getItem(int index) {
			return "welcome to postman, " + ChatFormatting.WHITE + mc.player.getName() + ChatFormatting.RESET + " :)";
		}

		@Override
		public Color getItemColor(int index) {
			return color.getValue();
		}

		@Override
		public boolean sortUp() {
			return false;
		}

		@Override
		public boolean sortRight() {
			return false;

//stuff to add
//christmas welcomer
		}
	}
}
