/**
 *   920 Text Editor is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   920 Text Editor is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with 920 Text Editor.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.jecelyin.util;

import java.lang.reflect.Array;

public class ArrayUtils
{
	private static Object[] EMPTY = new Object[0];
	private static final int CACHE_SIZE = 73;
	private static Object[] sCache = new Object[CACHE_SIZE];
	
    public static boolean inArray(String value, String[] strArray)
    {
        for(String v : strArray)
        {
            if(v == value)
                return true;
        }
        return false;
    }
    
    public static boolean inArray(int value, int[] intArray)
    {
        for(int v : intArray)
        {
            if(v == value)
                return true;
        }
        return false;
    }

	public static <T> T[] emptyArray(Class<T> kind) {
		if (kind == Object.class) {
			return (T[]) EMPTY;
		}
		
		int bucket = ((System.identityHashCode(kind) / 8) & 0x7FFFFFFF) % CACHE_SIZE;
		Object cache = sCache[bucket];
		
		if (cache == null || cache.getClass().getComponentType() != kind) {
			cache = Array.newInstance(kind, 0);
			sCache[bucket] = cache;
		}
		return (T[]) cache;
	}
    
    public static int idealCharArraySize(int need) {
        return idealByteArraySize(need * 2) / 2;
    }
    
    public static int idealIntArraySize(int need) {
        return idealByteArraySize(need * 4) / 4;
    }
    
    public static int idealByteArraySize(int need) {
        for (int i = 4; i < 32; i++)
            if (need <= (1 << i) - 12)
                return (1 << i) - 12;

        return need;
    }
}
