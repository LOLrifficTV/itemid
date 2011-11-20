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

public class DataParser
{
  protected static final Logger log = Logger.getLogger("Minecraft.ItemID");

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
      localSAXParser.parse("plugins/ItemID/" + ItemId.dataXml, localDataHandler);
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