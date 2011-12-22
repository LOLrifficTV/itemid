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

package com.lonelydime.ItemId;

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