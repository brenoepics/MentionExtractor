package com.brenoepic.timeout;

import java.time.Instant;

public class Timeout
{
    private final int id;
    private final Instant finish;
    
    public Timeout(int id, Instant time) {
        this.id = id;
        this.finish = time;
    }
    
    public int getId() {
        return this.id;
    }
    
    public Instant getFinish() {
        return this.finish;
    }
    
}
