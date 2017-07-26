package ray.eldath.avalon.gc.test;

import ray.eldath.avalon.gc.model.GroupMember;
import ray.eldath.avalon.gc.tool.CoolQGroupOperator;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class MainClassTest {
	public static void main(String[] args) {
		GroupMember member = CoolQGroupOperator.getGroupMember(319293196, 1797609811);
		long time = member.getLastActiveTime();
		long current = LocalDateTime.of(
				2017, 7, 26, 0, 0, 0)
				.toEpochSecond(ZoneOffset.ofHours(8)) * 1000;
		System.out.println(current);
		System.out.println(time);
		System.out.print("-: ");
		System.out.println(time - current > 86400000);
	}
}
