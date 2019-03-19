package com.mhearts.conf.util;

import java.util.Collection;

public class CollectionUtils {
	/**
	 * 求collectionA 和 collectionB的差集
	 * @param collectionA
	 * @param collectionB
	 * @return
	 */
	public static Collection<?> differenceSet(Collection<?> collectionA, Collection<?> collectionB) {
		if (null == collectionA || null == collectionB) {
			return null;
		}
	    collectionA.removeAll(collectionB);
	    return collectionA;
	}
	
	/**
	 * 求collectionA 和 collectionB的交集，注意结果会影响collectionA，
	 * @param collectionA
	 * @param collectionB
	 * @return
	 */
	public static Collection<?> intersection(Collection<?> collectionA, Collection<?> collectionB) {
		if (null == collectionA || null == collectionB) {
			return null;
		}
		collectionA.retainAll(collectionB);
		return collectionA;
	}
}
