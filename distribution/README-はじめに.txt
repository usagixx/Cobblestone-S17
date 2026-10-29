Cobblestone S17 v1.4 — 別PCへの導入

対象: Minecraft Java Edition 1.21.11 / Fabric Loader 0.20.1 / Java 21以上
Minecraft本体・アカウント・ランチャーは別途必要です。

1. 別PCでMinecraft Java版1.21.11とFabric Loaderを用意します。
   公式Fabricインストーラー: https://fabricmc.net/use/installer/
   Fabric Loader 0.20.1は現在のPCで動作確認したバージョンです。
2. Fabricの起動構成を一度起動して終了します。
3. ZIPを展開し、modsとconfigを、その起動構成のゲームディレクトリへコピーします。
   Windowsで既定のゲームディレクトリなら %APPDATA%\.minecraft です。
   専用のゲームディレクトリを指定している場合は、そちらへコピーします。
   既存の同名設定はコピー前にバックアップしてください。
   古いCobblestone Helper/S17や重複するFabric APIはmodsへ同時に入れないでください。
4. Fabricで起動し、play.cobblestone.ggへ接続します。

操作:
  R または /wf: 農場ワープ
  H: プレイヤー表示切替
  /chconfig: MOD設定
  右Shift または /s17hud: HUD編集
  HUD編集: ドラッグで移動、ホイールか +/- で拡縮、Done/Escで保存
  ギルドクエスト: /guildでクエスト画面を開いて同期
  ボスバー: 受信した全件を自動縮小・複数列で表示
  チャット: 同梱Chat Utilitiesの設定で会話と通知を分離

HUDの位置・大きさは別PCの画面に合わせて初期値から調整できます。
チャットの窓位置は画面サイズによって調整が必要な場合があります。
チャット判定はCobblestoneの公開会話の書式に基づきます。
未対応の会話書式は通知側に表示されることがあります。

同梱:
  Cobblestone S17 1.4（改変MOD）
  Fabric API 0.141.6+1.21.11
  Chat Utilities 2.2.3
  会話/通知分離設定、改変MODの対応ソース、ライセンス

Cobblestone Helper原作者: JandBDavenport
原作: https://github.com/JandBDavenport/Cobblestone-Helper-Mod
改変内容: S17作物データ、機能整理、HUDデザインと配置編集、全ボスバー表示など。
サーバー公式配布物ではありません。
Fabric API: https://github.com/FabricMC/fabric
Chat Utilities: https://modrinth.com/mod/chat-utilities

ログ、スクリーンショット、アカウント認証情報、サーバー一覧、
個人のクエスト進捗やキー設定は含めていません。

ボスバー優先順: セレスティアル → ウェザー → グローバル → クッキー → その他。同じ優先度はサーバーの順番を保持します。

チャット文字倍率: 会話・通知ともに0.8。会話欄: X=0/Y=175、通知欄: X=0/Y=112。両方とも幅215・高さ80。
ストア宣伝の2行も両方のチャット欄で非表示にします。


時計・FARMED集計のHUDは右Shift → Clock / FARMEDで位置とサイズを変更できます。ギルド会話はGC欄へ表示します。HUDとチャットの配置設定を同梱しています。個人のクエスト進捗は含みません。

