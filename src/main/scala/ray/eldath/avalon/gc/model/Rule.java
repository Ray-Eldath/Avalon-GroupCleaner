package ray.eldath.avalon.gc.model;

public interface Rule {
    boolean kickNow(GroupMember member);

    String kickNextTime(GroupMember member);

    /**
     * @return 若不需要提醒成员，则返回空字符串或{@code null}。否则则提醒群成员指定内容。
     */
    String notice(GroupMember member);
}
