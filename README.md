# QuickUtils - Minecraft 实用工具插件

![GitHub](https://img.shields.io/badge/Minecraft-1.21.5-blueviolet)
![Version](https://img.shields.io/badge/Version-1.4.3-success)

## 简介
QuickUtils 是一个 Minecraft 实用工具插件，允许玩家通过命令快捷访问各种原版 GUI 界面，如工作台、末影箱、附魔台等。同时，插件提供了一个综合界面，方便玩家快速打开这些功能。此外，插件支持权限管理，管理员可以灵活控制玩家对不同功能的使用权限，还会定期检查 GitHub 上的最新版本，确保您使用的是最新版。

## 功能特性
- **多种 GUI 界面**：支持虚拟工作台、个人末影箱、附魔台、铁砧、砂轮、织布机、制图台和锻造台等多种界面。
- **综合界面**：通过 `/qu` 命令可以打开一个综合界面，快速访问各种功能。
- **权限管理**：每个功能都有对应的权限设置，管理员可以灵活控制玩家的使用权限。
- **版本检查**：插件会根据配置文件中的设置，定期检查 GitHub 上的最新版本，若发现新版本会在服务器日志中提示。
- **附魔台绑定**：玩家可以使用 `/qu bind` 命令绑定附魔台，之后可通过 `/qu enchant` 命令快速访问绑定的附魔台。

## 安装方法
1. 将编译后的 `QuickUtils.jar` 放入服务端 `plugins` 目录。
2. 重启/重载服务器。
3. 使用指令 `/qu help` 查看帮助。

## 命令列表
| 命令               | 描述                     | 权限要求               |
|--------------------|--------------------------|------------------------|
| `/qu`              | 打开综合界面             | `quickutils.gui.menu`  |
| `/qu help`         | 查看命令帮助             | 无                     |
| `/qu workbench`    | 打开虚拟工作台           | `quickutils.gui.workbench` |
| `/qu enderchest`   | 打开个人末影箱           | `quickutils.gui.enderchest` |
| `/qu enchant`      | 打开附魔界面             | `quickutils.gui.enchant` |
| `/qu anvil`        | 打开铁砧界面             | `quickutils.gui.anvil` |
| `/qu grindstone`   | 打开砂轮界面             | `quickutils.gui.grindstone` |
| `/qu loom`         | 打开织布机界面           | `quickutils.gui.loom` |
| `/qu cartography_table` | 打开制图台界面       | `quickutils.gui.cartography_table` |
| `/qu smithing`     | 打开锻造台界面           | `quickutils.gui.smithing` |
| `/qu bind`         | 绑定附魔台               | `quickutils.bind` |

## 权限设置
以下是插件支持的权限设置及其描述：
- `quickutils.gui.workbench`：允许使用虚拟工作台
- `quickutils.gui.enderchest`：允许使用个人末影箱
- `quickutils.gui.enchant`：允许使用附魔界面
- `quickutils.gui.anvil`：允许使用铁砧界面
- `quickutils.gui.grindstone`：允许使用砂轮界面
- `quickutils.gui.loom`：允许使用织布机界面
- `quickutils.gui.cartography_table`：允许使用制图台界面
- `quickutils.gui.smithing`：允许使用锻造台
- `quickutils.gui.menu`：允许使用 `/qu` 命令打开综合界面
- `quickutils.bind`：允许使用 `/qu bind` 命令绑定附魔台
- `quickutils.admin`：拥有所有插件权限
- `quickutils.*`：通配所有功能权限
- `quickutils.gui.*`：通配所有 GUI 相关权限

## 版本检查
插件会根据配置文件中的设置，定期检查 GitHub 上的最新版本。如果发现新版本，会在服务器日志中提示。

## 配置文件
配置文件 `config.yml` 位于插件数据目录下，您可以通过修改该文件来调整版本检查的频率等设置。

## 贡献
如果您想为 QuickUtils 做出贡献，请访问我们的 [GitHub 仓库](https://github.com/CN-MRZZJ/QuickUtils)。

## 许可证
本插件采用 [Apache 2.0 License](https://www.apache.org/licenses/LICENSE-2.0) 许可证。具体详情请查看 `LICENSE` 文件。