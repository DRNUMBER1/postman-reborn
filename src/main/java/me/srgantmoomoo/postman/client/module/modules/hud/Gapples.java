package me.srgantmoomoo.postman.client.module.modules.hud;

import com.lukflug.panelstudio.hud.HUDList;
import com.lukflug.panelstudio.hud.ListComponent;
import com.lukflug.panelstudio.theme.Theme;
import me.srgantmoomoo.Reference;
import me.srgantmoomoo.postman.api.util.render.JColor;
import me.srgantmoomoo.postman.client.module.Category;
import me.srgantmoomoo.postman.client.module.HudModule;
import me.srgantmoomoo.postman.client.setting.settings.BooleanSetting;
import me.srgantmoomoo.postman.client.setting.settings.ColorSetting;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.awt.*;


public class Gapples extends HudModule {
	private final GapplesCounterList list = new GapplesCounterList();
	
	public final ColorSetting color = new ColorSetting("color", this, new JColor(Reference.POSTMAN_COLOR, 255));
	public final BooleanSetting sort = new BooleanSetting("sortRight", this, false);

	public Gapples() {
		super("gapples", "shows how many gapples u have in ur inventory.", new Point(134, 82), Category.HUD);
		this.addSettings(sort, color);
	}
	
	   public void onRender() {
	    	list.gapples = mc.player.inventory.mainInventory.stream()
	    			.filter(itemStack -> itemStack.getItem() == Items.GOLDEN_APPLE)
	    			.mapToInt(ItemStack::getCount).sum();
	    	if (mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE)
	    		list.gapples++;
	    }
	
	@Override
	public void populate (Theme theme) {
		component = new ListComponent(getName(), theme.getPanelRenderer(), position, list);
	}
	
	private class GapplesCounterList implements HUDList {
		public int gapples = 0;
		
		@Override
		public int getSize() {
			return 1;
		}

		@Override
		public String getItem(int index) {
			return "gapples " + gapples;
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
			return sort.isEnabled();
		}
	}
}