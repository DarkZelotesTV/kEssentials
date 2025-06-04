# 💙 kEssentials

## 📝 Description
A plugin containing all the essential commands for managing a KettleMC server.
The plugin allows for easy moderation of players and the server, as well as a few QOL commands.

## 📦 Setup
1. Clone this repository
2. Open the project in your IDE
3. Use `./gradlew build` to build the project
4. Install the plugin in your server and restart it

## 🧾 Commands
```
/gamemode <Gamemode> [<Player>]
/fly [<Player>]
/teleportplayer <Target1> [<Target2>]
/freeze <Target>
/unfreeze <Target>
/repair [<Player>]
/sun [<World>]
/rain [<World>]
/thunder [<World>]
/morning [<World>]
/day [<World>]
/midday [<World>]
/evening [<World>]
/night [<World>]
/speed <Speed> [<Player>]
/vanish [<Player>]
/inventorysee <Player>
/armorsee <Player>
/enderchest [<Player>]
/chatclear
/f3d
/feed [<Player>]
/heal [<Player>]
/workbench [<Player>]
/anvil [<Player>]
/suicide

/tpa <Player>
/tpaccept <Player>
/tpdeny <Player>
/tplist
```

## ➕ Additional Information
As this plugin has been created for KettleMC.net, it mainly includes features required in our network. If you need additional features, feel free to create an issue or a pull request, but we won't merge features we feel are out of place.
---

## JetMP Integration (v1.0)

This plugin now includes an integrated system originally based on JetMP. The following features have been added on top of the existing Essentials functionality:

### Discord Integration
- Bot automatically starts with the server
- Real-time killfeed messages sent to a configured Discord channel
- Supports skin-based embeds using Minecraft avatar APIs
- Commands like `!playerinfo`, `!admininfo`, `!teaminfo`, `!topkills`, `!topteams`, and `!verify`

### Clan / Team System
- `/team create <name>`: Create a new team
- `/team join <name>`: Join an existing team
- `/team leave`: Leave your current team
- Teams are tracked in a SQLite database
- Scoreboard integration to show team, kills, and deaths

### Account Linking
- `/link`: Generates a code to link Minecraft and Discord account
- `!verify <code>`: Run in Discord to link accounts
- All links are stored in the local SQLite database

### SQLite Integration
- Auto-creates database file at `plugins/kEssentials/data.db`
- Manages player statistics, team data, and Discord linking

### Configurable Settings
- `config.json` file in plugin folder:
  - Bot token and Discord settings
  - Killfeed message format and embed style
  - Admin roles for protected commands
  - Customizable language for all messages

---

These additions were implemented without changing the original command structure of kEssentials, making the plugin fully backward compatible.

### Feature Overview

- **Discord Bot Integration**
  - Automatically starts with the server
  - Real-time killfeed as Discord embeds
  - Supports player skins as avatars
  - Role-based permissions for commands
- **Killfeed System**
  - Captures kills via PlayerDeathEvent
  - Shows avatars in the Discord killfeed
  - Counts kills and deaths in SQLite
  - Messages configurable through `config.json`
- **Clan System (/team)**
  - Create, join, or leave teams
  - Clan leaders manage members
  - Team point statistics
  - Clan tag displayed on the scoreboard
- **Scoreboard**
  - Displays current kills, deaths, and team tag
  - Automatically updates on events
- **SQLite Database**
  - Automatically created at startup
  - Stores player, clan, and link data
  - Tables: `player_data`, `clans`, `clan_members`, `link_codes`
- **Discord Commands**
  - `!playerinfo`, `!admininfo`, `!teaminfo`, `!topkills`, `!topteams`, `!position`, `!verify`
- **Minecraft Commands**
  - `/team create <name>`, `/team join <name>`, `/team leave`
  - `/link` generates a verification code
  - `/botreload` reloads `config.json`
  - `/bot say <text>` sends a message via the bot
- **Configurability**
  - `config.json` contains the token, channel ID, admin roles, and messages

### Discord Command Reference

| Command | Description |
|---------|-------------|
| `!playerinfo <name>` | Shows the kill and death count for a player |
| `!admininfo <name>` | Includes the player's position (admin only) |
| `!teaminfo <name>` | Displays clan members and points |
| `!topkills` | Lists the players with the most kills |
| `!topteams` | Lists the clans with the most points |
| `!position <name>` | Shows a player's coordinates (admin only) |
| `!verify <code>` | Links a Minecraft account with Discord |

