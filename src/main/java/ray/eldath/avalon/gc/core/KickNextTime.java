package ray.eldath.avalon.gc.core;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import ray.eldath.avalon.gc.model.GroupMember;
import ray.eldath.avalon.gc.model.Rule;
import ray.eldath.avalon.gc.util.Constant;
import ray.eldath.avalon.gc.tool.CoolQGroupOperator;

import java.io.*;
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

    public void handle(Rule rule) throws FileNotFoundException {
        JSONObject object = (JSONObject) new JSONTokener(new FileReader(Constant._KICK_NEXT_TIME_LIST)).nextValue();

        JSONArray thisGroupMember;
        for (Object thisGroupNumberObject : object.names()) {
            String thisGroupNumberString = (String) thisGroupNumberObject;
            Long thisGroupNumber = Long.parseLong(thisGroupNumberString);
            thisGroupMember = object.getJSONArray(thisGroupNumberString);

            for (Object thisGroupMember1Object : thisGroupMember) {
                Long thisGroupMember1 = Long.parseLong((String) thisGroupMember1Object);
                GroupMember member = CoolQGroupOperator.getGroupMember(thisGroupNumber, thisGroupMember1);
                String kick = rule.kickNextTime(member);
                if (kick != null && !kick.isEmpty())
                    member.kick();
                System.out.println("群员 " + thisGroupMember1 + " 由于未做出修正而被移出本群。");
            }
        }
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

        try (FileWriter writer = new FileWriter(Constant._KICK_NEXT_TIME_LIST)) {
            writer.write(object.toString());
        }
    }
}
