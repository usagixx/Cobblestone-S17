# Cobblestone S17

[日本語](#日本語) | [English](#english)

## 日本語

**play.cobblestone.gg 向けのクライアントMODです。** 農場ワープ、ギルドクエストHUD、プレイヤー非表示などをまとめています。

### ダウンロード・導入

**[Cobblestone S17 v1.10.0をダウンロード](https://github.com/usagixx/Cobblestone-S17/raw/refs/heads/main/distribution/Cobblestone-S17-v1.10.0-portable.zip)**

動作環境：Minecraft Java Edition **1.21.11**／Fabric Loader **0.20.1**／Java **21以上**。Minecraft本体・アカウント・ランチャーは別途必要です。

1. Minecraft 1.21.11用のFabric起動構成を用意します。
2. Minecraftを終了して、ZIPを展開します。
3. ZIP内の `mods` と `config` を、その起動構成のゲームディレクトリへコピーします。既存の設定は先にバックアップし、古いCobblestone Helper／S17や重複したFabric APIは外してください。
4. Fabricで起動し、`play.cobblestone.gg` に接続します。

同梱：Cobblestone S17、Fabric API、Chat Utilities、チャット設定、対応ソース、ライセンス。

### キー・コマンド早見表

| キー | チャットに入力するコマンド | 動作 |
|---|---|---|
| `R` | `/wf` | 農場を選んでワープ |
| H | — | ほかのプレイヤーを表示／非表示 |
| 2 | /cookie grandma | Grandmaクッキーを使用 |
| 3 | /cookie cobbled | Cobbledクッキーを使用 |
| 5 | /cookie suspicious | Suspiciousクッキーを使用 |
| 右Shift | `/s17hud` | HUDの位置・サイズを編集 |
| — | `/s17chconfig` | MOD設定を開く |
| — | `/s17chance` | Rush・Echoのチャンス通知をまとめてON／OFF |
| — | `/s17chancesound` | チャンス通知の金床音だけON／OFF |
| — | `/quest` | `/guild quests` を実行してクエスト画面を開き、HUDに同期 |
| — | `/s17chattest` | チャットの振り分けをテスト。自分の画面だけに表示 |

`/guild` と `/cookie` はサーバー側のコマンドです。クッキーキーはチャット・メニューを開いている間は反応せず、長押ししても連打しません。数字キーはホットバー選択と競合するため、必要に応じて操作設定で割り当てを変更してください。

### 農場ワープ

`R` または `/wf` で農場選択画面を開き、S17の作物一覧から行き先を選びます。小さい画面ではスクロールして選択できます。

R画面では、未完了の収穫クエスト対象に緑、Combatクエスト対象のMOBがいる農場に赤い枠線が付きます。クエスト情報は /quest で画面を開いて同期してください。

Farm Warpsのタイトル枠をクリックすると **Fish Warps** に切り替わり、もう一度クリックすると戻ります。魚28種類のアイコンを押すと /fish 魚名 を実行します。専用アイコンはサーバーのリソースパックから表示し、未適用の場合は代わりに通常の魚を表示します。農場はW7（Cobblespace）まで対応し、各行の右にワールド番号1～7を表示します。W6・W7のMOBクエストも対応する農場を赤枠で表示します。

### ギルドクエストHUD

MODの `/quest` を入力すると、サーバーへ `/guild quests` を送って **Guild Quests** を開き、MOB討伐2件・作物収穫2件の対象と達成状況を読み取ります。4件そろうと同期を完了し、HUDに対象・完了数・更新までの時間を表示します。情報を更新したいときは、クエスト画面を開き直してください。

クエスト画面の右上にあるボタン：

| ボタン | 動作 |
|---|---|
| `C` | MODが保存したクエスト情報を消去。サーバー側の進捗は消えません。消去後は画面を開き直して再同期 |
| `S` | クエストHUDの位置調整画面を開く |

### HUDの配置・サイズ

右Shift または `/s17hud` で、クエストHUD・右側のスコアボード・ボスバーを編集できます。

| 操作 | 動作 |
|---|---|
| ドラッグ | 移動 |
| ホイール、または `＋`／`−` | サイズ変更 |
| `Done` または `Esc` | 保存して終了 |

ボスバーは受信した全件を、自動縮小・複数列で表示します。優先順は **セレスティアル → ウェザー → グローバル → クッキー → その他**。同じ優先度ではサーバーの順番を保ちます。天候は Shimmering Night、Calm Thunder、Blood Moon、Casual Rain、Positive Sun、Fortuitous Rain、Mysterious Fog、Fishy Rainfall に対応しています。

### チャットの振り分け

同梱のChat Utilitiesと設定で、チャットを3つに分けます。

| チャット欄 | 表示する内容 |
|---|---|
| 下の「会話」 | プレイヤーの会話、全プレイヤーの `[+] 名前` 参加通知 |
| 上の「通知」 | 上記以外のサーバー通知 |
| 右の「GC」 | 行頭が GUILD • のギルドチャット。他の欄には重複表示しません |

募集ボタンなどはMinecraft標準のクリック処理で動作します。過去ログを読んでいる間は新着で表示位置を動かさず、開き直しても位置を保ちます。最下部へ戻るには下方向へスクロールしてください。

サーバーのTIP、スタッフ募集、単独の募集用Discordリンク、空行、次のストア宣伝は両方の欄で非表示にします。

- `[!] Ranks, crates, boosters and bundles!`
- `[!] Click here to check out the store!`

通常の会話本文に同じ語句やURLが含まれていても、この非表示ルールの対象にはしません。書式で分類するため、未対応の会話は通知欄に入る場合があります。

同梱設定の文字倍率は両方 **0.8**。会話欄は X=0／Y=176（幅214・高さ80）、通知欄は X=0／Y=112（幅215・高さ80）、GC欄は X=266／Y=176（幅214・高さ80）です。画面サイズに合わせて調整してください。

### 日本時間・1時間ごとのFARMED

日本時間（JST）の現在時刻と、直前に計測を完了した1時間のFARMED増加量を表示します。右Shift → **Clock / FARMED** で位置・サイズを調整できます。

日本時間の毎時00分にだけFARMEDを読み取り、前の00分との差をHUDとローカルチャットへ `60min 100000` の形式で表示します。最初の00分は基準の記録だけを行い、次の00分から結果が出ます。履歴は `config/s17-farmed-history.tsv` に保存します。サーバー表示がK／Mなどで丸められているため、結果には丸め誤差が含まれます。切断・カウンターの減少・00分の読み取り失敗時は基準を取り直し、60分を超える区間を1時間の結果として表示しません。

初回チュートリアル通知（木を倒す・板材を作る等）は自動的にOFFになります。

### Rush／Echoのチャンス通知

Rushの輪・矢印とEchoの矢印を受信データから検知し、従来の中央見出しと同じ位置に **RUSH CHANCE!／ECHO CHANCE!** を表示します。視線や音量に依存せず、ウィンドウサイズの変更にも追従します。文字は標準タイトルの75％で、Rushは金〜クリーム色、Echoは紫〜ピンク色。従来の通知を受信すると、そのフォント・太字・影・配色を引き継ぎます。

連鎖中は種類ごとに最初の1回だけ通知し、`ECHO chain (… steps) • …`／`RUSH chain (…s) • …` の終了チャットで次の検知を有効にします。中央のECHO／RUSH表示ではリセットしません。終了通知を受信できない場合は、専用表示が10秒途切れると再検知します。新しい中央見出しは前の見出しを上書きしますが、下段のスペシャルクロップ通知は消しません。

チャンス通知時には金床を置く音を1回鳴らします。`/s17chance` は通知全体、`/s17chancesound` は追加音だけを、入力するたびにON／OFFします。設定は保存されます。追加音はMinecraftのUI音量に従います。`/s17chconfig` ではRushとEchoを個別に切り替えられます。`/s17rushtest`・`/s17echotest` で表示と音を自分の画面だけで確認できます。

スペシャルクロップの中央報酬行は作物名だけを省略し、倍率・獲得数・回数・Golden倍率・色を残します。例：`1335x [+30.27B Warped Wart] GOLDEN x5` → `1335x [+30.27B] GOLDEN x5`。

### 機能のON／OFF

/s17chconfig の上部で Chat MOD・日本時間・FARMED表示・クッキーキー（2・3・5）を切り替えられます。OFFにするとキーからのクッキーコマンド送信を停止します。設定は再起動後も保持され、手入力の /cookie は使えます。Chat MODをOFFにすると通常のMinecraftチャットに戻り、ONで3欄表示に戻ります。配置とフィルター設定は保持します。/s17chat で切り替え、/s17chat on・/s17chat off で直接指定もできます。再起動は不要です。

### その他の設定

`/s17chconfig` から変更できます。

| 機能 | 内容 |
|---|---|
| 自動リスポーン | 死亡後、設定した待ち時間でリスポーン。初期設定では無効 |
| ドロップアイテムの発光 | 落ちているアイテムを見つけやすくする。有効／無効・色を変更可能 |

### クレジット・ライセンス

原作 [Cobblestone Helper](https://github.com/JandBDavenport/Cobblestone-Helper-Mod) の作者は **JandBDavenport** です。本MODはその改変版で、Cobblestoneサーバーの公式MODではありません。

- 原作・改変部分：[GPL-3.0](LICENSE)
- 同梱Chat Utilities：MIT
- 同梱Fabric API：Apache-2.0

同梱物のライセンス全文は [licenses](licenses) にあります。

---

## English

**A client-side mod for play.cobblestone.gg**, combining farm warps, a guild quest HUD, player hiding, and other utilities.

### Download and installation

**[Download Cobblestone S17 v1.10.0](https://github.com/usagixx/Cobblestone-S17/raw/refs/heads/main/distribution/Cobblestone-S17-v1.10.0-portable.zip)**

Requires Minecraft Java Edition **1.21.11**, Fabric Loader **0.20.1**, and Java **21 or later**. Minecraft, an account, and a launcher are required separately.

1. Set up a Fabric installation for Minecraft 1.21.11.
2. Close Minecraft and extract the ZIP.
3. Copy its `mods` and `config` folders into that installation's game directory. Back up existing settings first. Remove older Cobblestone Helper/S17 JARs and duplicate Fabric API JARs.
4. Launch Fabric and connect to `play.cobblestone.gg`.

Includes Cobblestone S17, Fabric API, Chat Utilities, chat configuration, corresponding source, and licenses.

### Controls and commands

| Key | Command to enter in chat | Action |
|---|---|---|
| `R` | `/wf` | Select a farm and warp |
| H | — | Show/hide other players |
| 2 | /cookie grandma | Use Grandma cookie |
| 3 | /cookie cobbled | Use Cobbled cookie |
| 5 | /cookie suspicious | Use Suspicious cookie |
| Right Shift | `/s17hud` | Edit HUD positions and sizes |
| — | `/s17chconfig` | Open mod settings |
| — | `/s17chance` | Toggle Rush/Echo chance notifications |
| — | `/s17chancesound` | Toggle the chance notification sound |
| — | `/quest` | Run `/guild quests` to open the quest screen and sync the HUD |
| — | `/s17chattest` | Preview chat routing locally; nothing is broadcast |

`/guild` and `/cookie` are server commands. Cookie shortcuts are inactive in chat and menus and do not repeat while held. Number keys conflict with hotbar selection; rebind them in Controls if needed.

### Farm warps

Press `R` or enter `/wf`, then select a destination from the S17 crop list. Scroll to reach all destinations on smaller screens.

In the R menu, unfinished farming objectives have green outlines; farms hosting combat quest targets have red outlines. Open /quest to sync quest information.

Click the Farm Warps title bar to switch to **Fish Warps**, and click again to return. Select one of 28 fish to run /fish FishName. Custom icons use the server resource pack; a vanilla fish appears if it is unavailable. Farms include W7 (Cobblespace), with world numbers 1–7 on the right of each row. W6 and W7 mob quests highlight their corresponding farm destinations in red.

### Guild quest HUD

The client command `/quest` sends `/guild quests` to the server, opening **Guild Quests** to read two mob quests and two farming quests. Sync completes once all four quest items arrive. The HUD shows targets, the completed quest count, and the reset timer. Reopen the quest screen to refresh the information.

Buttons at the top right of the quest screen:

| Button | Action |
|---|---|
| `C` | Clear the mod's cached quest data without resetting server progress. Reopen the screen to sync again |
| `S` | Open the quest HUD position editor |

### HUD layout and size

Press Right Shift or enter `/s17hud` to edit the quest HUD, right-hand scoreboard, and boss bars.

| Control | Action |
|---|---|
| Drag | Move |
| Mouse wheel or `+` / `−` | Resize |
| `Done` or `Esc` | Save and exit |

All received boss bars are displayed with automatic scaling and multiple columns. Priority: **Celestial → Weather → Global → Cookie → Others**. Server order is preserved within each priority group. Recognized weather types include Shimmering Night, Calm Thunder, Blood Moon, Casual Rain, Positive Sun, Fortuitous Rain, Mysterious Fog, and Fishy Rainfall.

### Chat routing

The bundled Chat Utilities mod and configuration separate messages into three windows.

| Window | Messages |
|---|---|
| Lower conversation window | Player conversations and every player's `[+] Name` join notification |
| Upper notification window | Other server notifications |
| Right-hand GC window | Guild messages starting with GUILD •, excluded from the other windows |

Recruitment buttons use standard Minecraft click handling. New messages keep your place while reading older messages, including after reopening chat. Scroll downward to return to the newest messages.

Server TIPs, staff recruitment messages, standalone recruitment Discord links, blank lines, and these store promotions are hidden in both windows:

- `[!] Ranks, crates, boosters and bundles!`
- `[!] Click here to check out the store!`

Regular player conversations containing the same words or URLs are excluded from these hide rules. Routing depends on message format, so unsupported conversation formats may appear in the notification window.

Both bundled font scales are **0.8**. Conversation: X=0, Y=176, size 214×80. Notifications: X=0, Y=112, size 215×80. GC: X=266, Y=176, size 214×80. Adjust the layout for your screen size.

### Japan time and hourly FARMED

Shows the current time in Japan (JST) and the FARMED increase for the last completed measurement hour. Move or resize it using Right Shift → **Clock / FARMED**.

FARMED is read only at minute 00 of each hour in Japan time. The difference from the previous hour is shown as `60min 100000` on the HUD and in a local-only chat report. The first minute-00 reading establishes the baseline; the next produces the first result. History is saved in `config/s17-farmed-history.tsv`. Totals may include rounding error because the server uses K/M suffixes. Disconnecting, a decreasing counter, or a missed hour-boundary reading resets the baseline. Longer intervals are never labeled as 60 minutes.

First-time tutorial prompts (punching trees, crafting planks, etc.) are automatically disabled.

### Rush / Echo chance notifications

Received Rush wave/arrow and Echo arrow display models trigger **RUSH CHANCE! / ECHO CHANCE!** in the vanilla title position, independent of camera direction or audio volume. The position follows window resizing. Chance text uses 75% of the normal title size, with gold/cream for Rush and purple/pink for Echo. After a native proc title is received, its font, boldness, shadow and palette are reused.

Each chain triggers one notification per proc type. Final `ECHO chain (… steps) • …` / `RUSH chain (…s) • …` chat summaries rearm detection; intermediate central ECHO/RUSH titles do not. If no summary arrives, ten seconds without a matching display rearms detection. New central titles replace earlier titles while preserving the separate crop-reward subtitle.

An anvil placement sound plays once with each chance notification. `/s17chance` toggles notifications, and `/s17chancesound` toggles only the added sound. Both commands alternate ON/OFF and save the setting. The sound follows Minecraft's UI volume. Rush and Echo can also be toggled individually in `/s17chconfig`. `/s17rushtest` and `/s17echotest` preview the title and sound locally.

Central special-crop rewards omit only the crop name, keeping the multiplier, amount, progress, Golden multiplier and colors. Example: `1335x [+30.27B Warped Wart] GOLDEN x5` → `1335x [+30.27B] GOLDEN x5`.

### Feature toggles

Use the top of /s17chconfig to toggle Chat MOD, Japan time, the FARMED display, and cookie shortcuts (2/3/5). Disabling cookie shortcuts stops commands sent by those keys. The setting persists after restart; manually typed /cookie commands remain available. Chat MOD OFF restores vanilla chat; ON restores the three windows without deleting their layout or filters. /s17chat toggles the mode; /s17chat on and /s17chat off set it explicitly. No restart is required.

### Other settings

Enter `/s17chconfig` to configure these features.

| Feature | Description |
|---|---|
| Auto respawn | Respawn after a configurable delay following death. Disabled by default |
| Dropped item glow | Highlight dropped items. Enable/disable the feature and change its color |

### Credits and licenses

The original [Cobblestone Helper](https://github.com/JandBDavenport/Cobblestone-Helper-Mod) was created by **JandBDavenport**. This is a modified version, not an official Cobblestone server mod.

- Original code and modifications: [GPL-3.0](LICENSE)
- Bundled Chat Utilities: MIT
- Bundled Fabric API: Apache-2.0

Full bundled license texts are in [licenses](licenses).
