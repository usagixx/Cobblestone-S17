# Cobblestone S17

[日本語](#日本語) | [English](#english)

## 日本語

Minecraft Java版の **play.cobblestone.gg** 向けに、Cobblestone HelperをS17用へ改変したクライアントMODです。  
**Minecraft 1.21.11／Fabric** に対応しています。

### 操作一覧

チャット欄にコマンドを入力するか、対応するキーを押して操作できます。

| キー | コマンド | できること |
|---|---|---|
| `R` | `/wf` | 農場選択画面を開く |
| `H` | — | ほかのプレイヤーの表示・非表示を切り替える |
| — | `/chconfig` | MODの設定画面を開く |
| 右Shift | `/s17hud` | HUDの位置・サイズを編集する |
| — | `/guild` | サーバーのギルド画面を開き、クエスト情報を同期する |

※ `/guild` はサーバー側のコマンドです。

### 農場へワープする

1. `R` キーを押すか、`/wf` を入力します。
2. S17対応の作物一覧から、移動したい農場を選びます。

### ギルドクエストを表示する

`/guild` を入力し、クエスト画面を開いて情報を同期します。

同期した討伐・収穫の対象や達成状況をHUDに表示します。配色はCobblestoneのスコアボードに合わせています。

### HUDの位置・サイズを変える

右Shiftを押すか、`/s17hud` を入力すると編集画面が開きます。

| 操作 | 動作 |
|---|---|
| ドラッグ | 表示位置を移動 |
| マウスホイール、または `＋`／`−` | サイズを変更 |
| `Done`、または `Esc` | 保存して編集を終了 |

ギルドクエストHUD・右側のスコアボード・上部のボスバーを調整できます。

### 設定画面で変更できる機能

`/chconfig` を入力すると、MODの設定画面が開きます。

| 機能 | 内容 |
|---|---|
| 自動リスポーン | 死亡後、設定した待ち時間で自動的にリスポーンします。初期設定では無効です。 |
| ドロップアイテムの発光 | 落ちているアイテムを発光させます。有効・無効や色を変更できます。 |

### 自動で動作する機能

#### ボスバーの全件表示

サーバーから受信したボスバーを、自動縮小や複数列への配置で全件表示します。コマンド入力は不要です。

#### 会話とサーバー通知の分離

同梱のChat Utilitiesと専用設定で、プレイヤーの会話とサーバー通知を別のチャット欄に表示します。

メッセージの書式で分類するため、未対応の会話は通知側に表示される場合があります。

### クレジット

原作「Cobblestone Helper」の作者は **JandBDavenport** です。

本MODはその改変版で、Cobblestoneサーバーの公式MODではありません。原作・改変部分のライセンスはGPL-3.0です。

---

## English

A client-side modification of Cobblestone Helper updated for S17 on **play.cobblestone.gg** in Minecraft Java Edition.  
Supports **Minecraft 1.21.11 / Fabric**.

### Controls and commands

Enter a command in chat or press the corresponding key.

| Key | Command | Action |
|---|---|---|
| `R` | `/wf` | Open the farm selection screen |
| `H` | — | Toggle visibility of other players |
| — | `/chconfig` | Open the mod settings screen |
| Right Shift | `/s17hud` | Edit HUD positions and sizes |
| — | `/guild` | Open the server's guild screen to sync quest information |

Note: `/guild` is a server command.

### Warp to a farm

1. Press `R` or enter `/wf`.
2. Select a farm from the crop list updated for S17.

### Display guild quests

Enter `/guild` and open the quest screen to sync quest information.

The HUD displays synced kill and harvest objectives and their progress, using colors based on Cobblestone's scoreboard.

### Change HUD positions and sizes

Press Right Shift or enter `/s17hud` to open the HUD editor.

| Control | Action |
|---|---|
| Drag | Move an element |
| Mouse wheel or `+` / `−` | Resize an element |
| `Done` or `Esc` | Save and exit the editor |

You can adjust the guild quest HUD, the scoreboard on the right, and the boss bars at the top.

### Features available in settings

Enter `/chconfig` to open the mod settings screen.

| Feature | Description |
|---|---|
| Auto respawn | Automatically respawn after a configurable delay following death. Disabled by default. |
| Dropped item glow | Make dropped items glow. You can enable or disable the feature and change its color. |

### Automatic features

#### Display all boss bars

Displays all boss bars received from the server, automatically scaling them down or arranging them into multiple columns. No command is required.

#### Separate player chat and server notifications

The bundled Chat Utilities mod and custom configuration display player conversations and server notifications in separate chat windows.

Messages are classified by their format. Unsupported conversation formats may appear in the notifications window.

### Credits

The original **Cobblestone Helper** was created by **JandBDavenport**.

This mod is a modified version and is not an official Cobblestone server mod. The original code and modifications are licensed under GPL-3.0.
