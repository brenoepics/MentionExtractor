package com.brenoepic.timeout;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MentionTimeout {
    private Map < Integer, Timeout > users;
    public MentionTimeout() {
        this.users = new HashMap < Integer, Timeout > ();
    }
    public void dispose() {
        this.users.clear();
    }
    public boolean canMention(int user) {
        for (Entry < Integer, Timeout > usr: this.users.entrySet()) {
            Timeout u = usr.getValue();
            if (u.getId() == user) {
                if (Duration.between(u.getFinish(), Instant.now()).isNegative()) {

                    return false;
                } else {
                    this.users.remove(usr.getKey());
                    return true;
                }
            }
        }
        return true;
    }
    public Duration getTimeout(int user){
        for (Entry < Integer, Timeout > usr: this.users.entrySet()) {
            Timeout u = usr.getValue();
            if (u.getId() == user) {
                if (Duration.between(u.getFinish(), Instant.now()).isNegative()) {
                    return Duration.between(u.getFinish(), Instant.now());
                } else {
                    this.users.remove(usr.getKey());
                    return null;
                }
            }
        }
        return null;
    }
    public void Add(int user, int time) {
        this.users.put(
                this.users.size() + 1,
                new Timeout(user, Instant.now().plusSeconds(time))
        );
    }
}