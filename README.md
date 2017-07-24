# Avalon-GroupCleaner [![GNU Affero General Public License, version 3](https://www.gnu.org/graphics/agplv3-155x51.png)](https://www.gnu.org/licenses/lgpl.html)

基于酷Q的自动QQ群成员清理程序。

## 功能
 - 基于Groovy语言自定义清理规则
 - 自动保存待清理列表实现“下一次运行时清理”功能
 - 提供7种群成员属性供筛选
 - 提供三种清理方式（立即移出、下次运行时移出、提醒）及提醒语句的自定义
 - ...
 
## 使用方法
 1. 下载[最新RELEASE](https://github.com/Ray-Eldath/Avalon-GroupCleaner/releases)并解压缩归档文件
 2. 编辑`./bin/rule/groovy/_rule.groovy`文件自定义规则
 3. 运行`./bin/avalon.group-cleaner`文件