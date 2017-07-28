package ray.eldath.avalon.gc.model

import ray.eldath.avalon.gc.tool.CoolQGroupOperator.{groupKick, sendToGroup}

class GroupMember(group: Group, userUid: Long, nickname: String,
                  card: String, lastActiveTime: Long, role: GroupMemberRoleEnum, unfriendly: Boolean) {
	def getGroup: Group = group

	def getUserUid: Long = userUid

	def getNickname: String = nickname

	def getCard: String = card

	def getLastActiveTime: Long = lastActiveTime * 1000

	def getRole: GroupMemberRoleEnum = role

	def isUnfriendly: Boolean = unfriendly


	def reply(reply: String): Unit = sendToGroup(group.getGroupUid, reply)

	def kick(): Unit = groupKick(group.getGroupUid, userUid)

	override def toString = s"GroupMember(getGroup=$getGroup, getUserUid=$getUserUid, getNickname=$getNickname, getCard=$getCard, getLastActiveTime=$getLastActiveTime, getRole=$getRole, isUnfriendly=$isUnfriendly)"
}
