# Cobblestone S17

[日本語](#日本語) | [English](#english)

## 日本語

**play.cobblestone.gg 向けのクライアントMODです。** 農場ワープ、ギルドクエストHUD、プレイヤー非表示などをまとめています。

### ダウンロード・導入

**[Cobblestone S17 v1.4をダウンロード](https://github.com/usagixx/Cobblestone-S17/raw/refs/heads/main/distribution/Cobblestone-S17-v1.4-portable.zip)**

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
| `H` | — | ほかのプレイヤーを表示／非表示 |
| 右Shift | `/s17hud` | HUDの位置・サイズを編集 |
| — | `/chconfig` | MOD設定を開く |
| — | `/guild` | ギルド画面を開く。クエスト画面まで進むとHUDに同期 |
| — | `/s17chattest` | チャットの振り分けをテスト。自分の画面だけに表示 |

`/guild` はサーバー側のコマンドです。

### 農場ワープ

`R` または `/wf` で農場選択画面を開き、S17の作物一覧から行き先を選びます。小さい画面ではスクロールして選択できます。

### ギルドクエストHUD

`/guild` から **Guild Quests** を開くと、MOB討伐2件・作物収穫2件の対象と達成状況を読み取ります。4件そろうと同期を完了し、HUDに対象・完了数・更新までの時間を表示します。情報を更新したいときは、クエスト画面を開き直してください。

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

ボスバーは受信した全件を、自動縮小・複数列で表示します。優先順は **セレスティアル → ウェザー → グローバル → クッキー → その他**。同じ優先度ではサーバーの順番を保ちます。

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

計測はFARMEDを読み取った時点から始まり、1時間ごとに結果をHUDとローカルチャットへ表示します。履歴は `config/s17-farmed-history.tsv` に保存します。サーバー表示がK／Mなどで丸められているため、結果は概算（`~`）です。切断・カウンターの減少・5分以上の読み取り中断時は計測し直します。

### その他の設定

`/chconfig` から変更できます。

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

**[Download Cobblestone S17 v1.4](https://github.com/usagixx/Cobblestone-S17/raw/refs/heads/main/distribution/Cobblestone-S17-v1.4-portable.zip)**

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
| `H` | — | Show/hide other players |
| Right Shift | `/s17hud` | Edit HUD positions and sizes |
| — | `/chconfig` | Open mod settings |
| — | `/guild` | Open the guild menu; enter the quest screen to sync the HUD |
| — | `/s17chattest` | Preview chat routing locally; nothing is broadcast |

`/guild` is a server command.

### Farm warps

Press `R` or enter `/wf`, then select a destination from the S17 crop list. Scroll to reach all destinations on smaller screens.

### Guild quest HUD

Enter `/guild` and open **Guild Quests** to read two mob quests and two farming quests. Sync completes once all four quest items arrive. The HUD shows targets, the completed quest count, and the reset timer. Reopen the quest screen to refresh the information.

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

All received boss bars are displayed with automatic scaling and multiple columns. Priority: **Celestial → Weather → Global → Cookie → Others**. Server order is preserved within each priority group.

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

Measurement begins with the first readable FARMED value. Each completed hour updates the HUD and posts a local-only chat report. History is saved in `config/s17-farmed-history.tsv`. Totals are estimates (`~`) because the server rounds values using K/M suffixes. Disconnecting, a decreasing counter, or a sampling gap longer than five minutes starts a new measurement. The first result appears after one full hour.

### Other settings

Enter `/chconfig` to configure these features.

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




