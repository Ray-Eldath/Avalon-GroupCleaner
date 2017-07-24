package ray.eldath.avalon.gc.main;

import ray.eldath.avalon.gc.core.Core;
import ray.eldath.avalon.gc.core.KickNextTime;
import ray.eldath.avalon.gc.core.RuleParser;
import ray.eldath.avalon.gc.model.GroupMember;
import ray.eldath.avalon.gc.model.Rule;
import ray.eldath.avalon.gc.tool.CoolQGroupOperator;
import ray.eldath.avalon.gc.util.Constant;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import static java.util.stream.Collectors.toList;

public class MainClass {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("=================================================");
        System.out.println("          Avalon QQ Group Member Cleaner");
        System.out.println();
        System.out.println("               开发者：Ray Eldath");
        System.out.println("-------------------------------------------------");
        System.out.println("          基于酷Q，遵守AGPL-3.0协议开源于           ");
        System.out.println("http://github.com/Ray-Eldath/Avalon-GroupCleaner");
        System.out.println("=================================================");
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.println("检索规则文件...");
        Path rulePath = Paths.get(Constant._CURRENT_PATH() + "/rule/groovy");
        if (!Files.exists(rulePath))
            Files.createDirectories(rulePath);
        List<Path> rulePaths = Files.list(rulePath).filter(e -> e.getFileName().toString().endsWith("groovy")).collect(toList());
        int size = rulePaths.size();
        if (size == 0) {
            System.err.println("未检索到有效规则文件！请尝试重新clone仓库！");
            return;
        }
        System.out.println("检索到" + size + "个有效规则文件：");
        int n = 1;
        for (Path nowPath : rulePaths)
            System.out.println("\t" + n++ + ". " + nowPath.getFileName().toString());
        System.out.println("请输入需应用的文件的编号：");
        File ruleFile = rulePaths.get(scanner.nextInt() - 1).toFile();

        Rule rule;
        try {
            rule = RuleParser.parse(ruleFile);
        } catch (IllegalAccessException | InstantiationException | IOException e) {
            System.err.println("解析规则文件时发生致命错误！若无法自行排除错误，" +
                    "请在https://github.com/Ray-Eldath/Avalon-GroupCleaner/issues上向开发者反馈！");
            e.printStackTrace();
            return;
        }

        System.out.println("请输入需执行群成员清理操作的群号：");
        Long groupUid = scanner.nextLong();
        System.out.println("即将开始清理！请确认已打开酷Q并已成功装载CoolQ HTTP API。键入`confirm`以开始清理进程：");
        String confirm = scanner.next();

        KickNextTime.instance().handle(groupUid, rule);
        if (!confirm.equalsIgnoreCase("confirm")) {
            System.out.println("操作已取消。");
            return;
        }
        List<GroupMember> groupMembers = CoolQGroupOperator.getGroupMembers(groupUid);
        int processed = 1;
        for (GroupMember thisMember : groupMembers) {
            Core.handleOnce(thisMember, rule);
            System.out.println("已处理群成员：" + processed++);
            if (!Constant._DEBUG())
                Thread.sleep(2500); // 防止刷屏
        }
        KickNextTime.instance().close();
        System.out.println("群成员清理已完成。");
    }
}
