package ray.eldath.avalon.gc.tool;

import org.eclipse.jetty.util.UrlEncoded;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import ray.eldath.avalon.gc.model.Group;
import ray.eldath.avalon.gc.model.GroupMember;
import ray.eldath.avalon.gc.model.GroupMemberRoleEnum;
import ray.eldath.avalon.gc.util.Constant;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class CoolQGroupOperator {
	private static final String _API_ADDRESS = "http://127.0.0.1:5000";

	private CoolQGroupOperator() {
		throw new Error("不准实例化我！");
	}

	public static void sendToGroup(long groupUid, String message) {
		if (Constant._DEBUG()) {
			System.out.println("To " + groupUid + ": " + message);
			return;
		}
		Map<String, Object> object = new HashMap<>();
		object.put("group_id", groupUid);
		object.put("message", message);
		object.put("is_raw", !message.contains("[CQ:"));
		sendRequest("/send_group_msg", object);
	}

	public static void groupKick(long groupUid, long userUid) {
		if (Constant._DEBUG())
			return;
		Map<String, Object> object = new HashMap<>();
		object.put("group_id", groupUid);
		object.put("user_id", userUid);
		object.put("reject_add_request", false);
		sendRequest("/set_group_kick", object);
	}

	public static List<GroupMember> getGroupMembers(long groupUid) {
		Map<String, Object> object = new HashMap<>();
		Group group = getGroup(groupUid);
		object.put("group_id", groupUid);
		JSONArray array = ((JSONObject) new JSONTokener(sendRequest("/get_group_member_list", object))
				.nextValue()).getJSONArray("data");
		List<GroupMember> members = new ArrayList<>();
		for (Object thisObject : array) {
			JSONObject item = (JSONObject) thisObject;
			if (!item.has("card"))
				System.err.println("无法获取到昵称为 " + item.getString("nickname") + " 的群成员的相关信息，已略过。");
			else
				members.add(new GroupMember(
						group,
						item.getLong("user_id"),
						item.getString("nickname"),
						item.getString("card"),
						item.getLong("last_sent_time"),
						GroupMemberRoleEnum.fromString(item.getString("role")),
						item.getBoolean("unfriendly")
				));
		}
		return members;
	}

	public static GroupMember getGroupMember(long groupUid, long userUid) {
		HashMap<String, Object> object = new HashMap<>();
		object.put("group_id", groupUid);
		object.put("user_id", userUid);
		object.put("no_cache", false);

		JSONObject response = ((JSONObject) new JSONTokener(
				sendRequest("/get_group_member_info", object)).nextValue()).getJSONObject("data");

		return new GroupMember(
				getGroup(groupUid),
				userUid,
				response.getString("nickname"),
				response.getString("card"),
				response.getLong("last_sent_time"),
				GroupMemberRoleEnum.fromString(response.getString("role")),
				response.getBoolean("unfriendly")
		);
	}

	private static Group getGroup(long groupUid) {
		JSONArray array = ((JSONObject) new JSONTokener(sendRequest("/get_group_list", null))
				.nextValue()).getJSONArray("data");
		for (Object thisObject : array) {
			JSONObject item = (JSONObject) thisObject;
			if (item.getLong("group_id") == groupUid)
				return new Group(groupUid, item.getString("group_name"));
		}
		return null;
	}

	private static String sendRequest(String url, Map<String, Object> data) {
		if (data == null)
			url = _API_ADDRESS + url;
		else {
			StringBuilder requestBuilder = new StringBuilder();
			Set<Map.Entry<String, Object>> entrySet = data.entrySet();
			for (Map.Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				Object value = entry.getValue();
				requestBuilder.append(key).append("=");
				if (value instanceof String)
					requestBuilder.append(UrlEncoded.encodeString((String) value));
				else
					requestBuilder.append(String.valueOf(value));
				requestBuilder.append("&");
			}
			String request = requestBuilder.toString();
			url = _API_ADDRESS + url + "?" + request.substring(0, request.length() - 1);
		}
		StringBuilder response = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
			String thisLine;
			while ((thisLine = reader.readLine()) != null)
				response.append(thisLine);
			reader.close();
		} catch (Exception e) {
			System.err.println("exception thrown while sendRequest to " + url + " " + e);
		}
		return response.toString();
	}
}
