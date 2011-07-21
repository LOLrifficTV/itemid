package main.java.com.lonelydime.ItemId;

import org.bukkit.ChatColor;

public class Result
{
  private String name;
  private int value;
  private int id;

  public Result(int paramInt, String paramString)
  {
    this.value = paramInt;
    this.name = paramString;
  }

  public Result(int paramInt1, int paramInt2, String paramString) {
    this.value = paramInt1;
    this.name = paramString;
    this.id = paramInt2;
  }

  public String getName() {
    return this.name;
  }

  public String getValue() {
    if ((ItemId.base.equalsIgnoreCase("hex")) || (ItemId.base.equalsIgnoreCase("hexadecimal"))) {
      return Integer.toHexString(this.value).toUpperCase();
    }
    return String.valueOf(this.value);
  }

  public String getId()
  {
    if ((ItemId.baseId.equalsIgnoreCase("hex")) || (ItemId.baseId.equalsIgnoreCase("hexadecimal"))) {
      return Integer.toHexString(this.id).toUpperCase();
    }
    return String.valueOf(this.id);
  }

  public String getFullValue()
  {
    if (this.id == 0) {
      return getValue();
    }
    if (this.id >= 10) {
      return getValue() + ChatColor.GRAY + ":" + getId() + ChatColor.GOLD;
    }
    return getValue() + ChatColor.GRAY + ":" + getId() + " " + ChatColor.GOLD;
  }

  public int getValuePad()
  {
    if (this.id <= 0) {
      return ItemId.numWidth;
    }
    return ItemId.numWidth + 3;
  }
}