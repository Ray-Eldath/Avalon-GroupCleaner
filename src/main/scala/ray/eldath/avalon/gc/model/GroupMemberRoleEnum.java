package ray.eldath.avalon.gc.model;

public enum GroupMemberRoleEnum {
    OWNER, ADMIN, MEMBER;

    public static GroupMemberRoleEnum fromString(String name) {
        return name.equalsIgnoreCase("owner") ?
                OWNER : (name.equalsIgnoreCase("admin") ?
                ADMIN :
                MEMBER);
    }
}