package groovy

import ray.eldath.avalon.gc.model.GroupMember
import ray.eldath.avalon.gc.model.Rule

import java.time.LocalDateTime
import java.time.ZoneOffset

class _rule implements Rule {
	boolean kickNow(GroupMember member) {
		long line = LocalDateTime.of(
				2017, 1, 27, 0, 0, 0)
				.toEpochSecond(ZoneOffset.ofHours(8)) * 1000
		if (member.lastActiveTime - line <= 0)
			return true
		return false
	}

	/**
	 *
	 * @param member 传入的群成员对象
	 * @return 返回 {@code null} 或 空字符串 表示不需移出此群成员。返回非空字符串表示 需要用该字符串提醒该群成员。
	 */
	String kickNextTime(GroupMember member) {
		if (member.unfriendly)
			return "下次踢"
		return null
	}

	String notice(GroupMember member) {
		return null
//		if (!member.card.matches("【.*】.+")) {
//			println(member.card)
//			return "您的群名片不和规范！请确保严格符合 【主语言】昵称 的群名片规则。"
//		}
//		return null
	}
}