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
package co.uk.hexeption.opiates;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.uk.hexeption.opiates.module.ModuleManager;
import co.uk.hexeption.opiates.wrapper.Wrapper;

public class Opiates {

	public static String	Client_Name		= "Opiates";
	public static double	Client_Version	= 1.0;
	public static String	Client_Creator	= "Hexeption";
	public static Logger	logger			= LogManager.getLogger();
	public static Opiates	Client_Instance = new Opiates();
	
	private static ModuleManager moduleManager = new ModuleManager();

	public void startClient() {
		logger.log(Level.DEBUG, "Loading " + Client_Name);
		logger.log(Level.DEBUG, "Made by " + Client_Creator);

		// INIT
		
		if(Wrapper.getInstance().getWorld() != null){
			moduleManager.setModuleState("Hud", false);
		}else{
			moduleManager.setModuleState("Hud", true);
		}

		logger.log(Level.DEBUG, "Finished loading " + Client_Name);
	}

	public static String getClient_Name() {
		return Client_Name;
	}

	public static double getClient_Version() {
		return Client_Version;
	}

	public static String getClient_Creator() {
		return Client_Creator;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static Opiates getInstance() {
		return Client_Instance;
	}

	public static ModuleManager getModuleManager() {
		return moduleManager;
	}
	
	
	
	

}
