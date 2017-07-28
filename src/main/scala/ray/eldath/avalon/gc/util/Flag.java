package ray.eldath.avalon.gc.util;

import ray.eldath.avalon.gc.model.GroupMember;

public class Flag {
    public static String AT(GroupMember member) {
        return "[CQ:at,qq=" + member.getUserUid() + "] ";
    }
}
