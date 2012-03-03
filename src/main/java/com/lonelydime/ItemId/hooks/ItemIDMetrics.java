package com.lonelydime.ItemId.hooks;

import java.io.IOException;
import java.util.logging.Logger;

import com.lonelydime.ItemId.ItemIDStats;
import com.lonelydime.ItemId.ItemId;

public class ItemIDMetrics {
	
	public static final Logger logger = Logger.getLogger("Minecraft");
	
	public static ItemIDStats metricsStats;
	
	public static void setupMetrics(ItemId instance, ItemIDStats stats) {
		try {
			metricsStats = stats;
		    Metrics metrics = new Metrics();
		    
		    metrics.addCustomData(instance, new Metrics.Plotter() {
		        @Override
		        public String getColumnName() {
		            return "Searches";
		        }

		        @Override
		        public int getValue() {
		            return metricsStats.getSearches();
		        }
		    });
		    
		    metrics.beginMeasuringPlugin(instance);
		    logger.info("[ItemID] Registered Metrics hook");
		} catch (IOException e) {
			logger.warning("[ItemID] Failed to register Metrics hook");
		}
	}
}
