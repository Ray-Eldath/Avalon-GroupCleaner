package ray.eldath.avalon.gc.model

class Group(groupUid: Long, groupName: String) {
  def getGroupUid: Long = groupUid

  def getGroupName: String = groupName


  override def toString = s"Group(getGroupUid=$getGroupUid, getGroupName=$getGroupName)"
}
