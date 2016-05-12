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

package co.uk.hexeption.opiates.module;

import net.minecraft.client.Minecraft;

public class Module {

	private String			name, cmdName, description;
	private int				bind, color;
	private Category		category;
	private boolean			isEnabled;

	public static Minecraft	mc	= Minecraft.getMinecraft();

	public Module(String name, String descrption, String cmdName, int bind, int color, Category category) {
		this.name = name;
		this.description = descrption;
		this.cmdName = cmdName;
		this.bind = bind;
		this.color = color;
		this.category = category;
	}

	public Module(String name, int bind, Category category) {
		this(name, "Empty", name, bind, 0x00ff7f, category);
	}

	public String getName() {
		return name;
	}

	public String getCmdName() {
		return cmdName;
	}

	public String getDescription() {
		return description;
	}

	public int getBind() {
		return bind;
	}

	public int getColor() {
		return color;
	}

	public Category getCategory() {
		return category;
	}

	public boolean getState() {
		return isEnabled;
	}

	public void onToggle() {
	}

	public void onEnable() {
	}

	public void onDisable() {
	}

	public void setState(boolean state) {
		this.onToggle();
		if (state) {
			this.onEnable();
			this.isEnabled = true;
		} else {
			this.onDisable();
			this.isEnabled = false;
		}
	}

}
