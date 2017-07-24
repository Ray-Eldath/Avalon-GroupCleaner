package groovy

import ray.eldath.avalon.gc.model.GroupMember
import ray.eldath.avalon.gc.model.Rule

class _rule implements Rule {
    boolean kickNow(GroupMember member) {
        return false
    }

    /**
     *
     * @param member 传入的群成员对象
     * @return 返回 {@code null} 或 空字符串 表示不需移出此群成员。返回非空字符串表示 需要用该字符串提醒该群成员。
     */
    String kickNextTime(GroupMember member) {
        return "下次踢"
    }

    String notice(GroupMember member) {
        return "提醒"
    }
}