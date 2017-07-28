package ray.eldath.avalon.gc.core;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import ray.eldath.avalon.gc.model.GroupMember;
import ray.eldath.avalon.gc.model.Rule;
import ray.eldath.avalon.gc.tool.CoolQGroupOperator;
import ray.eldath.avalon.gc.util.Constant;

import java.io.Closeable;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KickNextTime implements Closeable {
    private static final List<GroupMember> list = new ArrayList<>();

    private static final KickNextTime instance = new KickNextTime();

    public static KickNextTime instance() {
        return KickNextTime.instance;
    }

    void add(GroupMember member) {
        list.add(member);
    }

    public void handle(long groupUid, Rule rule) throws IOException {
        if (!Constant._KICK_NEXT_TIME_LIST().exists())
            return;
        try (FileReader reader = new FileReader(Constant._KICK_NEXT_TIME_LIST())) {
            JSONObject object = (JSONObject) new JSONTokener(reader).nextValue();
            JSONArray groupMember = object.getJSONArray(String.valueOf(groupUid));

            for (Object thisGroupMemberObject : groupMember) {
                long thisGroupMember =
                        thisGroupMemberObject instanceof Long ?
                                (long) thisGroupMemberObject :
                                (long) (int) thisGroupMemberObject;
                GroupMember member = CoolQGroupOperator.getGroupMember(groupUid, thisGroupMember);
                String kick = rule.kickNextTime(member);
                if (kick != null && !kick.isEmpty())
                    member.kick();
                System.out.println("群员 " + thisGroupMember + " 由于未做出修正而被移出本群。");
            }
        }
        System.out.println("文件删除状态：" + Constant._KICK_NEXT_TIME_LIST().delete());
    }

    @Override
    public void close() throws IOException {
        if (list.isEmpty())
            return;
        JSONObject object = new JSONObject();
        List<Long> array = new ArrayList<>();
        for (GroupMember thisMember : list)
            array.add(thisMember.getUserUid());
        object.put(String.valueOf(list.get(0).getGroup().getGroupUid()), array);

        try (FileWriter writer = new FileWriter(Constant._KICK_NEXT_TIME_LIST())) {
            writer.write(object.toString());
        }
    }
}
