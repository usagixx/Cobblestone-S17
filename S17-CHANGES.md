# Cobblestone S17 version 1.3

Minecraft 1.21.11 / Fabric / Java 21. Based on Cobblestone Helper 1.4.4 by JandBDavenport (commit 36830363a78c44c735070476eadb35b9221f9198).

## Changes

- Removed Shady Summoner, Bazaar scanning and best-crop highlighting, plant hitbox enlargement, Discord RPC, and Herald's Mark / Talisman Forge / scoreboard-team kick workarounds. Removed their implementations, registrations and settings controls.
- Updated Farm Warps to 45 S17 crops from the user's six screenshots. Kept the existing visual style, R key and /wf. Added scrolling to keep all six worlds accessible in a small window.
- Retained player hiding (H), guild quest HUD, optional auto respawn, item glow, and theme controls.
- Added /s17chattest: two local-only messages to preview chat routing. No message is broadcast.
- The companion Chat Utilities 2.2.3 mod is installed separately, with conversation and notification windows configured. Chat Wheel and Auto Text are disabled/empty.

## Farm data

Bundled data: src/client/resources/assets/cobblestonehelper/s17-farms.json.
An optional config/cobblestonehelper/s17-farms.json file replaces the bundled data after validation, on the next game startup.

Display names and category membership were read from the user's screenshots. Direct-warp aliases initially use lowercase display names without spaces; aliases that have not been tested in game must not be described as verified. Item icons use vanilla equivalents; the user's server resource pack may alter their appearance.

The old guild quest parser and boss-to-crop mappings are retained; S17 compatibility is not yet verified.

## Build

Set JAVA_HOME to a Java 21 JDK and run gradlew.bat build. Runtime JAR: build/libs/cobblestone-s17-1.2.jar.

## Licensing / credits

Original author: JandBDavenport, https://github.com/JandBDavenport/Cobblestone-Helper-Mod.
This modified source follows the repository's GPL-3.0 license (LICENSE), rather than the inconsistent CC0 label in the upstream fabric.mod.json. Original authorship is preserved. Chat Utilities is a separate mod by rd5ey24d1ae, distributed on Modrinth with the MIT license; its source is not merged into this project.

## 1.0.1+s17
- Restyled guild quest HUD to match the Cobblestone sidebar: amber heading and accent, yellow labels, white values, reset timer and completion count.
- Dynamic HUD bounds, long-name clipping, and sample rows in the position editor when no quest data is available.
- Existing quest detection is preserved; no sample quest data is used during normal play.


## 1.1.0+s17
- Right Shift or /s17hud opens a unified editor for guild HUD, vanilla server sidebar, and boss bars. Drag to move; wheel or +/- to resize; Esc/Done saves; Reset restores the selected widget.
- Sidebar uses the vanilla renderer with its formatted server text and scores preserved.
- Boss bars render every received entry. A grid and adaptive scale replace vanilla's screen-height cutoff; 216 geometry cases were checked.
- Coordinates and sizes persist in the existing helper configuration. Server-side objectives and gameplay are unchanged.

## 1.1.1+s17
- Stable boss-bar priority: Celestial, weather (including Shimmering Night), Global, Cookie, then remaining entries in server order. Every received bar remains visible.
- Distribution chat configuration retains the user's adjusted layout and font sizes, routes [+] join messages to conversation, and hides server TIP/staff recruitment messages in both windows.

## 1.2
- Release name: Cobblestone S17. Version: 1.2. Includes all changes listed above.


## 1.3
- Fixed both chat font scales at 0.8 and updated window positions and sizes.
- Hide the two standalone store promotion messages in both chat windows.
- Mod gameplay behavior is unchanged from 1.2.

- Recognize S17 MOB QUEST #1/#2 as well as legacy BOSS QUEST titles, normalize title case, support comma-separated kill counts, and wait for all four quests before finishing sync. HUD labels now use MOB.

## 1.4
- Use vanilla handling for companion chat click actions.
- Preserve chat scroll position on reopening and new arrivals.
- Add a dedicated GUILD chat window.
- Add an editable JST clock and hourly estimated FARMED HUD with local TSV history.


## 1.5
- Read FARMED only at minute 00, once per hour, and compare consecutive hour boundaries.
- Display the last completed result as 60min 100000, without a range or approximation prefix.
- Missing boundaries, disconnects and counter resets do not produce misleading hourly totals.


## 1.6
- Add rebindable cookie shortcuts: 2 grandma, 3 cobbled, 5 suspicious. One command per press, disabled in menus and chat.


## 1.6.1
- Disable and save the vanilla tutorial step at runtime so in-memory settings cannot restore tutorial notifications.


## 1.7
- Display both synced combat quest objectives and completion state above farms in the R menu, with wrapped text and scrolling.


## 1.7.1
- Replace legacy boss mappings with the supplied S17 mob-to-farm mappings for five regions (40 mobs, singular/plural aliases).
- Highlight the corresponding farm icon for unfinished combat quests; remove the extra combat text section.
- Infernal Citadel mobs are not mapped without supplied data.


## 1.8
- Click the title bar to toggle farm/fish pages. Add 20 fish commands and server-pack texture references (no server textures redistributed).
- Extend Infernal Citadel with Redstone, Raw Gold and Netherite.


## 1.8.1
- Compact category spacing without shrinking farm icons.
- Add all eight W6 mob-to-farm mappings in the supplied order.


## 1.9
- Add a persisted live Chat Utilities/vanilla toggle to /s17chconfig and /s17chat [on|off].
- Add separate clock and FARMED display toggles to /s17chconfig.


