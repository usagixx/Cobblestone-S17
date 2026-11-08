# Cobblestone S17

Minecraft 1.21.11 / Fabric / Java 21. Based on Cobblestone Helper by JandBDavenport.

The current feature list and controls are documented in README.md in Japanese and English.

## Data and configuration

- `s17-farms.json`: 56 farm destinations in seven worlds, including Cobblespace. World numbers appear beside each row.
- `s17-fish.json`: 28 fish destinations. Icons are loaded from the active server resource pack.
- `s17-mob-farms.json`: mob-to-farm quest mappings, including Infernal Citadel and Cobblespace.
- Quest reading scans lore for action and progress text and normalizes formatting. An explicit false glint override does not complete a quest.
- Optional local farm overrides in `config/cobblestonehelper/s17-farms.json` replace bundled data.

Warp aliases use lowercase crop names without spaces; newly added aliases require in-game verification.

## Build

Use Java 21 and run `gradlew.bat build`. Output: `build/libs/cobblestone-s17-1.10.0.jar`.

## Credits and licensing

Original: JandBDavenport, https://github.com/JandBDavenport/Cobblestone-Helper-Mod.
This modified project is GPL-3.0; see LICENSE. It is not an official Cobblestone server mod.
Chat Utilities by rd5ey24d1ae is a separate MIT-licensed companion mod; see licenses/.
