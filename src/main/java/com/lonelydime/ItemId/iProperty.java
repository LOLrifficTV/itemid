package main.java.com.lonelydime.ItemId;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

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

public final class iProperty
{
  private static final Logger log = Logger.getLogger("Minecraft");
  private Properties properties;
  private String fileName;

  public iProperty(String paramString)
  {
    this.fileName = paramString;
    this.properties = new Properties();
    File localFile = new File(paramString);

    if (localFile.exists())
      load();
    else
      save();
  }

  public void load()
  {
    try {
      this.properties.load(new FileInputStream(this.fileName));
    } catch (IOException localIOException) {
      log.log(Level.SEVERE, "Unable to load " + this.fileName, localIOException);
    }
  }

  public void save() {
    try {
      this.properties.store(new FileOutputStream(this.fileName), "Minecraft Properties File");
    } catch (IOException localIOException) {
      log.log(Level.SEVERE, "Unable to save " + this.fileName, localIOException);
    }
  }

  public Map<String, String> returnMap() throws Exception {
    HashMap localHashMap = new HashMap();
    BufferedReader localBufferedReader = new BufferedReader(new FileReader(this.fileName));
    String str1;
    while ((str1 = localBufferedReader.readLine()) != null) {
      if ((str1.trim().length() == 0) || 
        (str1.charAt(0) == '#')) {
        continue;
      }
      int i = str1.indexOf('=');
      String str2 = str1.substring(0, i).trim();
      String str3 = str1.substring(i + 1).trim();
      localHashMap.put(str2, str3);
    }
    localBufferedReader.close();
    return localHashMap;
  }

  public void removeKey(String paramString) {
    this.properties.remove(paramString);
    save();
  }

  public boolean keyExists(String paramString) {
    return this.properties.containsKey(paramString);
  }

  public String getString(String paramString) {
    if (this.properties.containsKey(paramString)) {
      return this.properties.getProperty(paramString);
    }

    return "";
  }

  public String getString(String paramString1, String paramString2) {
    if (this.properties.containsKey(paramString1)) {
      return this.properties.getProperty(paramString1);
    }
    setString(paramString1, paramString2);
    return paramString2;
  }

  public void setString(String paramString1, String paramString2) {
    this.properties.setProperty(paramString1, paramString2);
    save();
  }

  public int getInt(String paramString) {
    if (this.properties.containsKey(paramString)) {
      return Integer.parseInt(this.properties.getProperty(paramString));
    }

    return 0;
  }

  public int getInt(String paramString, int paramInt) {
    if (this.properties.containsKey(paramString)) {
      return Integer.parseInt(this.properties.getProperty(paramString));
    }

    setInt(paramString, paramInt);
    return paramInt;
  }

  public void setInt(String paramString, int paramInt) {
    this.properties.setProperty(paramString, String.valueOf(paramInt));
    save();
  }

  public double getDouble(String paramString) {
    if (this.properties.containsKey(paramString)) {
      return Double.parseDouble(this.properties.getProperty(paramString));
    }

    return 0.0D;
  }

  public double getDouble(String paramString, double paramDouble) {
    if (this.properties.containsKey(paramString)) {
      return Double.parseDouble(this.properties.getProperty(paramString));
    }

    setDouble(paramString, paramDouble);
    return paramDouble;
  }

  public void setDouble(String paramString, double paramDouble) {
    this.properties.setProperty(paramString, String.valueOf(paramDouble));
    save();
  }

  public long getLong(String paramString) {
    if (this.properties.containsKey(paramString)) {
      return Long.parseLong(this.properties.getProperty(paramString));
    }

    return 0L;
  }

  public long getLong(String paramString, long paramLong) {
    if (this.properties.containsKey(paramString)) {
      return Long.parseLong(this.properties.getProperty(paramString));
    }

    setLong(paramString, paramLong);
    return paramLong;
  }

  public void setLong(String paramString, long paramLong) {
    this.properties.setProperty(paramString, String.valueOf(paramLong));
    save();
  }

  public boolean getBoolean(String paramString) {
    if (this.properties.containsKey(paramString)) {
      return Boolean.parseBoolean(this.properties.getProperty(paramString));
    }

    return false;
  }

  public boolean getBoolean(String paramString, boolean paramBoolean) {
    if (this.properties.containsKey(paramString)) {
      return Boolean.parseBoolean(this.properties.getProperty(paramString));
    }

    setBoolean(paramString, paramBoolean);
    return paramBoolean;
  }

  public void setBoolean(String paramString, boolean paramBoolean) {
    this.properties.setProperty(paramString, String.valueOf(paramBoolean));
    save();
  }
}
