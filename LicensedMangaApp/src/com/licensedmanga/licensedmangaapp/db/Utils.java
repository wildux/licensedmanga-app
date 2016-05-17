/**
 * 
 */
package com.licensedmanga.licensedmangaapp.db;

/**
 * @author joan
 *
 */
public final class Utils {
	
	public static String getType(int type_id) {
		String result;
		switch(type_id) {
		case 1:
			result = "Shojo";
			break;
		case 2:
			result = "Josei";
			break;
		case 3:
			result = "Shonen";
			break;
		case 4:
			result = "Seinen";
			break;
		case 5:
			result = "Kodomo";
			break;
		case 6:
			result = "Yuri";
			break;
		case 7:
			result = "BL";
			break;
		case 8:
			result = "Manhua";
			break;
		case 9:
			result = "Manhwa";
			break;
		case 10:
			result = "Novel";
			break;
		case 11:
			result = "Hentai";
			break;
		case 12:
			result = "Artbook";
			break;
		case 13:
			result = "International/OEL";
			break;
		default:
			result = "Other";
			break;
		}
		
		return result;
	}
	
	
	public static String getState(int state) {
		String st;
		switch(state) {
		case 1:
			st = "Ongoing";
			break;
		case 2:
			st = "Complete";
			break;
		case 3:
			st = "Hiatus";
			break;
		case 4:
			st = "Dropped";
			break;
		case 5:
			st = "Incomplete";
			break;
		default:
			st = "Unknown";
			break;
		}		
		return st;
	}
	
}
