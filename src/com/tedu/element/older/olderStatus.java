package com.tedu.element.older;

import java.security.cert.CRLReason;
import java.util.Queue;

public enum olderStatus {
    LABA,JIETUO,JUMP,RUN1,UP,RUN2;

    public static Queue<olderStatus> getQueue() {
        Queue<olderStatus> queue = new java.util.LinkedList<>();
        queue.add(olderStatus.LABA);
        queue.add(JIETUO);
        queue.add(JUMP);
        queue.add(RUN1);
        queue.add(UP);
        queue.add(RUN2);
        return queue;
    }
}
