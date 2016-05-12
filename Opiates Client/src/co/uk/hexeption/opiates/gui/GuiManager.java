/*
 * Copyright © 2016 | Hexeption | All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package co.uk.hexeption.opiates.gui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.darkstorm.minecraft.gui.AbstractGuiManager;
import org.darkstorm.minecraft.gui.component.Button;
import org.darkstorm.minecraft.gui.component.ComboBox;
import org.darkstorm.minecraft.gui.component.Component;
import org.darkstorm.minecraft.gui.component.Frame;
import org.darkstorm.minecraft.gui.component.Slider;
import org.darkstorm.minecraft.gui.component.basic.BasicButton;
import org.darkstorm.minecraft.gui.component.basic.BasicComboBox;
import org.darkstorm.minecraft.gui.component.basic.BasicFrame;
import org.darkstorm.minecraft.gui.component.basic.BasicSlider;
import org.darkstorm.minecraft.gui.layout.GridLayoutManager;
import org.darkstorm.minecraft.gui.layout.GridLayoutManager.HorizontalGridConstraint;
import org.darkstorm.minecraft.gui.listener.ButtonListener;
import org.darkstorm.minecraft.gui.listener.ComboBoxListener;
import org.darkstorm.minecraft.gui.listener.SliderListener;
import org.darkstorm.minecraft.gui.theme.Theme;
import org.darkstorm.minecraft.gui.theme.simple.SimpleTheme;

import co.uk.hexeption.opiates.module.Category;
import co.uk.hexeption.opiates.module.Module;
import co.uk.hexeption.opiates.module.ModuleManager;
import co.uk.hexeption.opiates.value.Values;
import net.minecraft.client.Minecraft;

/**
 * Minecraft GUI API
 * 
 * This class is not actually intended for use; rather, you should use this as a
 * template for your actual GuiManager, as the creation of frames is highly
 * implementation-specific.
 * 
 * @author DarkStorm (darkstorm@evilminecraft.net)
 */
public final class GuiManager extends AbstractGuiManager {
	
	public static String EspList;
	private class ModuleFrame extends BasicFrame {
		private ModuleFrame() {
		}

		private ModuleFrame(String title) {
			super(title);
		}
	}

	private final AtomicBoolean setup;

	public GuiManager() {
		setup = new AtomicBoolean();
		EspList = "Player";
	}

	@Override
	public void setup() {
		if (!setup.compareAndSet(false, true))
			return;
		
		createTestFrame();
		createValuesFrame();
		ESPFrame();

		final Map<Category, ModuleFrame> categoryFrames = new HashMap<Category, ModuleFrame>();
		for (Module module : ModuleManager.getModules()) {
			if (module.isCategory(Category.GUI))
				continue;
			ModuleFrame frame = categoryFrames.get(module.getCategory());
			if (frame == null) {
				String name = module.getCategory().name().toLowerCase();
				name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
				frame = new ModuleFrame(name);
				frame.setTheme(getTheme());
				frame.setLayoutManager(new GridLayoutManager(1, 0));
				frame.setVisible(true);
				frame.setClosable(false);
				frame.setMinimized(true);
				Dimension defaultDimension = theme.getUIForComponent(frame).getDefaultSize(frame);
				frame.setWidth(defaultDimension.width);
				frame.setHeight(defaultDimension.height);
				frame.layoutChildren();
				addFrame(frame);
				categoryFrames.put(module.getCategory(), frame);
			}
			final Module updateModule = module;
			Button button = new BasicButton(module.getName()){
				public void update(){
					setText(updateModule.getName());
					setEnabled(updateModule.getState());
				}
			};
			button.addButtonListener(new ButtonListener() {
				
				@Override
				public void onButtonPress(Button button) {
					updateModule.toggle();
					button.setText(updateModule.getName());
					button.setEnabled(updateModule.getState());
				}
			});
			frame.add(button, HorizontalGridConstraint.FILL);
		}

		// Optional equal sizing and auto-positioning
		resizeComponents();
		Minecraft minecraft = Minecraft.getMinecraft();
		Dimension maxSize = recalculateSizes();
		int offsetX = 5, offsetY = 5;
		int scale = minecraft.gameSettings.guiScale;
		if (scale == 0)
			scale = 1000;
		int scaleFactor = 0;
		while (scaleFactor < scale && minecraft.displayWidth / (scaleFactor + 1) >= 320 && minecraft.displayHeight / (scaleFactor + 1) >= 240)
			scaleFactor++;
		for (Frame frame : getFrames()) {
			frame.setX(offsetX);
			frame.setY(offsetY);
			offsetX += maxSize.width + 5;
			if (offsetX + maxSize.width + 5 > minecraft.displayWidth / scaleFactor) {
				offsetX = 5;
				offsetY += maxSize.height + 5;
			}
		}
	}
	
	private void createValuesFrame(){
		Theme theme = getTheme();
		final Frame valuesFrame = new BasicFrame("Value Manager");
		valuesFrame.setTheme(theme);
		valuesFrame.setX(50);
		valuesFrame.setY(50);
		Dimension defaultDimension = theme.getUIForComponent(valuesFrame).getDefaultSize(valuesFrame);
		valuesFrame.setWidth(defaultDimension.width);
		valuesFrame.setHeight(defaultDimension.height);
		valuesFrame.layoutChildren();
		valuesFrame.setVisible(true);
		valuesFrame.setClosable(false);
		valuesFrame.setMinimized(true);

		for (final Values v : Values.getVals()) {
			if (v.getValueDisplay() == null)
				continue;

			Slider slider = new BasicSlider(v.getName());
			slider.setValueDisplay(v.getValueDisplay());
			slider.setValue((double) v.getValue());
			slider.setMaximumValue((double) v.getMax());
			slider.setMinimumValue((double) v.getMin());
			slider.setIncrement(1F);
			slider.setEnabled(true);
			slider.addSliderListener(new SliderListener() {

				@Override
				public void onSliderValueChanged(Slider slider) {

					v.setValue(slider.getValue());
				}
			});
			//valuesFrame.update();
			//slider.update();
			

			valuesFrame.add(slider);
		}
		
		addFrame(valuesFrame);
	}
	
	private void createTestFrame() {
		Theme theme = getTheme();
		Frame testFrame = new BasicFrame("Theme Manager");
		testFrame.setTheme(theme);
		ComboBox comboBox = new BasicComboBox("Simple Theme", "Cryton Theme");
		comboBox.addComboBoxListener(new ComboBoxListener() {
			
			@Override
			public void onComboBoxSelectionChanged(ComboBox comboBox) {
				Theme theme = null;
				switch(comboBox.getSelectedIndex()) {
				case 0:
					theme = new SimpleTheme();
					break;
				case 1: 
					//theme = new CrytonTheme();
					break;
					
				default:
					return;
				}
				setTheme(theme);
			}
		});
		testFrame.add(comboBox);
		testFrame.setX(50);
		testFrame.setY(50);
		Dimension defaultDimension = theme.getUIForComponent(testFrame).getDefaultSize(testFrame);
		testFrame.setWidth(defaultDimension.width);
		testFrame.setHeight(defaultDimension.height);
		testFrame.setVisible(true);
		testFrame.setClosable(false);
		testFrame.setMinimized(true);
		addFrame(testFrame);
	}
	
	private void ESPFrame() {
		Theme theme = getTheme();
		Frame testFrame = new BasicFrame("Outline Esp Manager");
		testFrame.setTheme(theme);
		ComboBox comboBox = new BasicComboBox("Player Esp", "Hostal Mob Esp", "Animals Esp" );
		comboBox.addComboBoxListener(new ComboBoxListener() {

			@Override
			public void onComboBoxSelectionChanged(ComboBox comboBox) {
				
				switch(comboBox.getSelectedIndex()) {
				case 0:
					EspList = "Player";
					return;
				case 1:
					EspList = "Mob";
					return;
				case 2:
					EspList = "Animals";
					return;

				}
			}
		});
		testFrame.add(comboBox);
		testFrame.setX(50);
		testFrame.setY(50);
		Dimension defaultDimension = theme.getUIForComponent(testFrame).getDefaultSize(testFrame);
		testFrame.setWidth(defaultDimension.width);
		testFrame.setHeight(defaultDimension.height);
		testFrame.setVisible(true);
		testFrame.setClosable(false);
		testFrame.setMinimized(true);
		addFrame(testFrame);
	}

	

	@Override
	protected void resizeComponents() {
		Theme theme = getTheme();
		Frame[] frames = getFrames();
		Button enable = new BasicButton("Enable");
		Button disable = new BasicButton("Disable");
		Dimension enableSize = theme.getUIForComponent(enable).getDefaultSize(enable);
		Dimension disableSize = theme.getUIForComponent(disable).getDefaultSize(disable);
		int buttonWidth = Math.max(enableSize.width, disableSize.width);
		int buttonHeight = Math.max(enableSize.height, disableSize.height);
		for (Frame frame : frames) {
			if (frame instanceof ModuleFrame) {
				for (Component component : frame.getChildren()) {
					if (component instanceof Button) {
						component.setWidth(buttonWidth);
						component.setHeight(buttonHeight);
					}
				}
			}
		}
		recalculateSizes();
	}

	private Dimension recalculateSizes() {
		Frame[] frames = getFrames();
		int maxWidth = 0, maxHeight = 0;
		for (Frame frame : frames) {
			Dimension defaultDimension = frame.getTheme().getUIForComponent(frame).getDefaultSize(frame);
			maxWidth = Math.max(maxWidth, defaultDimension.width);
			frame.setHeight(defaultDimension.height);
			if (frame.isMinimized()) {
				for (Rectangle area : frame.getTheme().getUIForComponent(frame).getInteractableRegions(frame))
					maxHeight = Math.max(maxHeight, area.height);
			} else
				maxHeight = Math.max(maxHeight, defaultDimension.height);
		}
		for (Frame frame : frames) {
			frame.setWidth(maxWidth);
			frame.layoutChildren();
		}
		return new Dimension(maxWidth, maxHeight);
	}
}