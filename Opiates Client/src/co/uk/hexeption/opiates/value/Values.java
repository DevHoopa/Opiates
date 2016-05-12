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

package co.uk.hexeption.opiates.value;

import java.util.ArrayList;

import org.darkstorm.minecraft.gui.component.BoundedRangeComponent.ValueDisplay;

public class Values {
	private double value, min, max;
	private String name;
	private ValueDisplay valueDisplay;
	private static ArrayList<Values> vals = new ArrayList<Values>();

	public static ArrayList<Values> getVals() {
		return vals;
	}

	public Values(String name, double value, double min, double max, ValueDisplay valueDisplay) {
		this.setName(name);
		this.setValue(value);
		this.setMin(min);
		this.setMax(max);
		this.setValueDisplay(valueDisplay);
		this.getVals().add(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ValueDisplay getValueDisplay() {
		return valueDisplay;
	}

	public void setValueDisplay(ValueDisplay valueDisplay) {
		this.valueDisplay = valueDisplay;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}
}