package com.aeolus.collection;
import com.aeolus.util.signlink.Signlink;

public final class ReferenceCache {

	public ReferenceCache(int i) {
		emptyNodeSub = new QueueNode();
		nodeSubList = new NodeSubList();
		initialCount = i;
		spaceLeft = i;
		nodeCache = new HashTable();
	}

	public QueueNode insertFromCache(long l) {
		QueueNode nodeSub = (QueueNode) nodeCache.get(l);
		if (nodeSub != null) {
			nodeSubList.insertHead(nodeSub);
		}
		return nodeSub;
	}

	public void removeFromCache(QueueNode nodeSub, long l) {
		try {
			if (spaceLeft == 0) {
				QueueNode nodeSub_1 = nodeSubList.popTail();
				nodeSub_1.unlink();
				nodeSub_1.unlinkSub();
				if (nodeSub_1 == emptyNodeSub) {
					QueueNode nodeSub_2 = nodeSubList.popTail();
					nodeSub_2.unlink();
					nodeSub_2.unlinkSub();
				}
			} else {
				spaceLeft--;
			}
			nodeCache.put(nodeSub, l);
			nodeSubList.insertHead(nodeSub);
			return;
		} catch (RuntimeException runtimeexception) {
			Signlink.reporterror("47547, " + nodeSub + ", " + l + ", " + (byte) 2 + ", " + runtimeexception.toString());
		}
		throw new RuntimeException();
	}

	public void unlinkAll() {
		do {
			QueueNode nodeSub = nodeSubList.popTail();
			if (nodeSub != null) {
				nodeSub.unlink();
				nodeSub.unlinkSub();
			} else {
				spaceLeft = initialCount;
				return;
			}
		} while (true);
	}

	private final QueueNode emptyNodeSub;
	private final int initialCount;
	private int spaceLeft;
	private final HashTable nodeCache;
	private final NodeSubList nodeSubList;
}
