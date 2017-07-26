package ray.eldath.avalon.gc.core;

import ray.eldath.avalon.gc.model.GroupMember;
import ray.eldath.avalon.gc.model.Rule;

import static ray.eldath.avalon.gc.util.Flag.AT;

public class Core {
	private static int kickT = 0, kickNextTimeT = 0, noticeT = 0;

	public static void handleOnce(GroupMember member, Rule rule) {
		String nextTime = rule.kickNextTime(member);
		String notice = rule.notice(member);

		if (rule.kickNow(member)) {
			member.kick();
			System.out.println("群员 " + member.getUserUid() + ":" + member.getCard() + " 已被移出。");
			kickT++;
		} else if (nextTime != null && !nextTime.isEmpty()) {
			KickNextTime.instance().add(member);
			member.reply(AT(member) + nextTime + "\n若您不做出修正，则您将在下次运行Avalon-GroupCleaner时被移出本群！");
			System.out.println("群员 " + member.getUserUid() + ":" + member.getCard() + " 将在下次运行时移出。");
			kickNextTimeT++;
		} else if (notice != null && !notice.isEmpty()) {
			member.reply(AT(member) + notice + "\n请您做出修正。");
			System.out.println("已提醒群员 " + member.getUserUid() + ":" + member.getCard() + " 做出修正。");
			noticeT++;
		}
	}

	public static int kickT() {
		return kickT;
	}

	public static int kickNextTimeT() {
		return kickNextTimeT;
	}

	public static int noticeT() {
		return noticeT;
	}
}
