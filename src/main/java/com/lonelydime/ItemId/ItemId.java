package main.java.com.lonelydime.ItemId;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.permissions.*;
/*
 * Portions of this software are Copyright (c) 2010 croemmich
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
  -----------------------------------------------------------------------------
 * Remaining portions are (c) TexasGamer - Licensed under the TexasGamer Software License
 */
public class ItemId extends JavaPlugin{
	
	private static String propFile = "itemid.properties";
	public static final Logger logger = Logger.getLogger("Minecraft.ItemID");
	private static iProperty props;
	public static String searchType = "all";
	public static String dataXml = "item-data.xml";
	public static String updateSource = "https://raw.github.com/TexasGamer/itemid/master/item-data.xml";
	public static boolean autoUpdate = true;
	public static String base = "decimal";
	public static String baseId = "decimal";
	public static int nameWidth = 24;
	public static int numWidth = 4;
	public static String delimiter = "-";
	public static int autoUpdateInterval = 86400;
	public static DataParser parser;
	private UpdateThread updateThread;
	
	public void onDisable() {
		logger.info("ItemID Disabled");
	}

	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
        logger.info(pdfFile.getName() + " " + pdfFile.getVersion() + " enabled.");
        
        if (!initProps()) {
            logger.severe(pdfFile.getName() + ": Could not initialise " + propFile);
            getServer().getPluginManager().disablePlugin(this);
            return;
          }
        
        if (parser == null) {
            parser = new DataParser();
          }
          if (!initData()) {
            logger.severe(pdfFile.getName() + ": An error occured while reading: " + dataXml + ".");
            if (!autoUpdate) {
              logger.severe(pdfFile.getName() + ": Set auto-update-data=true in " + propFile + " to automatically download the search data file " + dataXml);
            }
            getServer().getPluginManager().disablePlugin(this);
            return;
          }

          if (autoUpdate) {
            if (this.updateThread == null)
              this.updateThread = new UpdateThread(this);
            this.updateThread.start();
          }
	}
	
	public boolean initProps() {
	    File localFile1 = new File("plugins/ItemID/");
	    localFile1.mkdir();

	    props = new iProperty("plugins/ItemID/" + propFile);

	    searchType = props.getString("search-type", "all");
	    base = props.getString("base", "decimal");
	    baseId = props.getString("base-id", "decimal");
	    dataXml = props.getString("data-xml", "item-data.xml");
	    updateSource = props.getString("update-source", "https://raw.github.com/TexasGamer/itemid/master/item-data.xml");
	    autoUpdate = props.getBoolean("auto-update-data", true);
	    autoUpdateInterval = props.getInt("auto-update-interval", 86400);
	    nameWidth = props.getInt("width-blockname", 24);
	    numWidth = props.getInt("width-number", 4);
	    delimiter = props.getString("delimiter", "-");

	    if (autoUpdateInterval < 600) {
	      autoUpdateInterval = 600;
	      PluginDescriptionFile pdfFile = this.getDescription();
	      logger.info(pdfFile.getName() + ": auto-update-interval cannot be less than 600");
	    }

	    File localFile2 = new File("plugins/ItemID/" + propFile);
	    return localFile2.exists();
	  }
	
	public boolean initData() {
	    if ((dataXml == null) || (dataXml.equals(""))) {
	      return false;
	    }

	    File localFile = new File("plugins/ItemID/" + dataXml);
	    if ((!updateData()) && (!localFile.exists())) {
	      return false;
	    }

	    return parser.search("test") != null;
	  }
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		boolean canUseCommand = true;
		boolean canUseFind = true;
		String command = cmd.getName();
		
		if (sender instanceof Player) {
			canUseCommand = sender.hasPermission("itemid.usecmd");
			canUseFind = sender.hasPermission("itemid.find");
		}
		
		if (command.equalsIgnoreCase("itemid") && canUseCommand) {
			if (args.length >= 1) {
			  try {
			    int dataid = Integer.parseInt(args[0]);
			    try {
			    	sender.sendMessage(Material.getMaterial(dataid).toString());
			    }
			    catch (NullPointerException e) {
			    	sender.sendMessage("Item does not exist");
			    }

			  } 
			  catch (NumberFormatException e) {
				  	String datastring = args[0];
				  	if (args.length >= 2) {
				  		datastring = args[0]+"_"+args[1];
				  	}
				  	datastring = datastring.toUpperCase();

				    try {
				    	sender.sendMessage(datastring+": "+Integer.toString(Material.getMaterial(datastring).getId() ) );
				    }
				    catch (NullPointerException n) {
				    	sender.sendMessage("Item does not exist");
				    }
			  }

			}
			else {
				if (sender instanceof Player) {
					Player player = (Player)sender;
					String itemInHand = Integer.toString(player.getItemInHand().getTypeId());
					if(itemInHand.equalsIgnoreCase("0"))
					{
						player.sendMessage(ChatColor.RED + "You aren't holding anything!");
					}
					else
					{
						player.sendMessage(ChatColor.LIGHT_PURPLE+player.getItemInHand().getType().name()+": "+ itemInHand);
					}
				}
				else {
					return true;
				}
			}

		}
		
		if(command.equalsIgnoreCase("find") && canUseFind)
		{
			if (args.length > 0) {
		        String str2 = "";
		        for (int i = 0; i < args.length; i++) {
		          str2 = str2 + args[i] + " ";
		        }
		        str2 = str2.trim();
		        printSearchResults(sender, parser.search(str2, base), str2);
		        return true;
		      }
		      return false;
		}
		return true;
	}
	
	public boolean updateData() {
	    if (autoUpdate) {
	      try {
	    	 
	        URL localURL = new URL(updateSource);
	        PluginDescriptionFile pdfFile = this.getDescription();
	        logger.info(pdfFile.getName() + ": Updating data...");
	        InputStream localInputStream = localURL.openStream();
	        FileOutputStream localFileOutputStream = null;
	        localFileOutputStream = new FileOutputStream("plugins/ItemID/" + dataXml);
	        int j = 0;
	        int i;
	        while ((i = localInputStream.read()) != -1) {
	          localFileOutputStream.write(i);
	          j++;
	        }
	        localInputStream.close();
	        localFileOutputStream.close();
	        logger.info(pdfFile.getName() + ": Update complete!");
	        return true;
	      } catch (MalformedURLException localMalformedURLException) {
	        logger.severe(localMalformedURLException.toString());
	      } catch (IOException localIOException) {
	        logger.severe(localIOException.toString());
	      }
	      PluginDescriptionFile pdfFile = this.getDescription();
	      logger.info(pdfFile.getName() + ": Could not update search data.");
	      return false;
	    }
	    return true;
	  }
	
	public void printSearchResults(CommandSender paramCommandSender, ArrayList<Result> paramArrayList, String paramString)
	  {
	    if ((paramArrayList != null) && (paramArrayList.size() > 0)) {
	      paramCommandSender.sendMessage(ChatColor.AQUA + "Search results for \"" + paramString + "\":");
	      Iterator localIterator = paramArrayList.iterator();
	      String str = "";
	      int i = 0;
	      while (localIterator.hasNext()) {
	        i++;
	        Result localResult = (Result)localIterator.next();
	        str = str + rightPad(localResult.getFullValue(), localResult.getValuePad()) + " " + delimiter + " " + rightPad(localResult.getName(), nameWidth);
	        if ((i % 2 == 0) || (!localIterator.hasNext())) {
	          paramCommandSender.sendMessage(ChatColor.GOLD + str.trim());
	          str = "";
	        }
	        if (i > 16) {
	          paramCommandSender.sendMessage(ChatColor.RED + "Too many results found! Be more specific...");
	          break;
	        }
	      }
	    } else {
	      paramCommandSender.sendMessage(ChatColor.RED + "No results found!");
	    }
	  }

	  public static String leftPad(String paramString, int paramInt) {
	    return String.format("%" + paramInt + "s", new Object[] { paramString });
	  }

	  public static String rightPad(String paramString, int paramInt) {
	    return String.format("%-" + paramInt + "s", new Object[] { paramString });
	  }
}
