package me.srgantmoomoo.postman.client.module.modules.hud;

import com.lukflug.panelstudio.Context;
import com.lukflug.panelstudio.Interface;
import com.lukflug.panelstudio.hud.HUDComponent;
import com.lukflug.panelstudio.theme.Theme;
import me.srgantmoomoo.Reference;
import me.srgantmoomoo.postman.api.util.render.JColor;
import me.srgantmoomoo.postman.client.module.Category;
import me.srgantmoomoo.postman.client.module.HudModule;
import me.srgantmoomoo.postman.client.setting.settings.BooleanSetting;
import me.srgantmoomoo.postman.client.setting.settings.ColorSetting;
import me.srgantmoomoo.postman.client.setting.settings.NumberSetting;
import me.srgantmoomoo.postman.client.ui.clickgui.back.ClickGui;

import java.awt.*;

public class PlayerModel extends HudModule {
	public final BooleanSetting rect = new BooleanSetting("rect", this, true);
	public final NumberSetting size = new NumberSetting("size", this, 28, 10, 100, 1);
	public final ColorSetting color = new ColorSetting("rectColor", this, new JColor(Reference.POSTMAN_COLOR, 100));
	
	public PlayerModel() {
    	super("playerModel","shows ur player model on ur hud.", new Point(75, 2), Category.HUD);
    	this.addSettings(size, rect, color);
    }
	
	@Override
    public void populate (Theme theme) {
    	component = new PlayerModelComponent(theme);
    }
	
	private class PlayerModelComponent extends HUDComponent {

		public PlayerModelComponent (Theme theme) {
			super(getName(), theme.getPanelRenderer(), PlayerModel.this.position);
		}
		
		@Override
		public void render (Context context) {
			super.render(context);
			if(rect.isEnabled()) {
			Color bgcolor = new JColor(color.getValue());
			context.getInterface().fillRect(context.getRect(), bgcolor, bgcolor, bgcolor, bgcolor);
			}
			ClickGui.renderEntity(mc.player, new Point(context.getPos().x + 22, context.getPos().y + 58 - (mc.player.isSneaking() ? 10 : 0)), (int) size.getValue());
		}

		@Override
		public int getWidth (Interface inter) {
			return 44;
		}

		@Override
		public void getHeight (Context context) {
			context.setHeight(64);
		}
	}

}