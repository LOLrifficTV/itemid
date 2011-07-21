package main.java.com.lonelydime.ItemId;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class DataParser
{
  protected static final Logger log = Logger.getLogger("Minecraft.ItemId");

  public ArrayList<Result> search(String paramString)
  {
    return search(paramString, "decimal");
  }

  public ArrayList<Result> search(String paramString1, String paramString2) {
    try {
      SAXParserFactory localSAXParserFactory = SAXParserFactory.newInstance();
      SAXParser localSAXParser = localSAXParserFactory.newSAXParser();
      DataHandler localDataHandler = new DataHandler();
      localDataHandler.setPattern(Pattern.compile(".*?" + Pattern.quote(paramString1) + ".*", 2));
      localSAXParser.parse("plugins/ItemId/" + ItemId.dataXml, localDataHandler);
      return localDataHandler.getResults();
    } catch (Exception localException) {
      localException.printStackTrace();
    }
    return null;
  }

  class DataHandler extends DefaultHandler {
    boolean data = false;
    boolean blocks = false;
    boolean items = false;
    boolean item = false;
    private Pattern pattern;
    private ArrayList<Result> results = new ArrayList();

    DataHandler() {  }

    public void setPattern(Pattern paramPattern) { this.pattern = paramPattern; }

    public ArrayList<Result> getResults()
    {
      return this.results;
    }

    public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes) throws SAXException {
      if (paramString3.equalsIgnoreCase("DATA")) {
        this.data = true;
      }

      if (paramString3.equalsIgnoreCase("BLOCKS")) {
        this.blocks = true;
      }

      if (paramString3.equalsIgnoreCase("ITEMS")) {
        this.items = true;
      }

      if (paramString3.equalsIgnoreCase("ITEM")) {
        this.item = true;

        if ((ItemId.searchType.equalsIgnoreCase("all")) || ((ItemId.searchType.equalsIgnoreCase("blocks")) && (this.blocks == true)) || ((ItemId.searchType.equalsIgnoreCase("items")) && (this.items == true)))
        {
          String str1 = paramAttributes.getValue("name");
          String str2 = paramAttributes.getValue("dec");
          String str3 = paramAttributes.getValue("id");

          if ((str1 != null) && (str2 != null)) {
            if (this.pattern.matcher(str1).matches()) {
              if (str3 != null)
                this.results.add(new Result(Integer.valueOf(str2).intValue(), Integer.valueOf(str3).intValue(), str1));
              else
                this.results.add(new Result(Integer.valueOf(str2).intValue(), str1));
            }
          }
          else
            DataParser.log.severe("Name or value is null on an item");
        }
      }
    }

    public void endElement(String paramString1, String paramString2, String paramString3)
      throws SAXException
    {
      if (paramString3.equalsIgnoreCase("DATA")) {
        this.data = false;
      }

      if (paramString3.equalsIgnoreCase("BLOCKS")) {
        this.blocks = false;
      }

      if (paramString3.equalsIgnoreCase("ITEMS")) {
        this.items = false;
      }

      if (paramString3.equalsIgnoreCase("ITEM"))
        this.item = false;
    }
  }
}