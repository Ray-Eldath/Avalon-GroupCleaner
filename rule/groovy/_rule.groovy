package groovy

import ray.eldath.avalon.gc.model.GroupMember
import ray.eldath.avalon.gc.model.Rule

class _rule implements Rule {
    boolean kickNow(GroupMember member) {
        return false
    }

    String kickNextTime(GroupMember member) {
        return ""
    }

    String notice(GroupMember member) {
        return ""
    }
}