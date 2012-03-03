package com.lonelydime.ItemId;

public class ItemIDStats {

	private int searches;
	
	public ItemIDStats() {
		searches = 0;
	}
	
	public int getSearches() {
		return searches;
	}
	
	public void increaseSearches() {
		searches++;
	}
}
