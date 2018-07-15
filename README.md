# Pokemon-HUFScampus.ver-

大学3年生の時に作ったJAVAゲームです。
2人チームでプロジェクトを進めました。
プロジェクトは私がプログラミング担当で友達がデザインを担当しました。

期間は1か月半ぐらいでそのプロジェクトのテーマは「現在世に出ているゲームの中でシンプルな物を一つだけ選んで
ゲームをそのままJAVAで作ろう.」でしたが私のチームはポケモンのゲームを自分たちが通っている大学キャンパスバージョンで作る
ことを決めてプロジェクトを進めました。

基本的にはJAVAのタイマースレッドとGUI(AWTとSWING)ライブラリを活用してゲームを表現しました。
その中で野生ポケモンとのバトルとボスNPCとのバトルがあって、
二つのバトルはオリジナルゲームを見ながら考えた初歩的なアルゴリズムを作って適用しました。(doc branchに当時作ったflowがあります)

Pocketmon.java→ゲーム全体のflow

AI.java→バトルに適応されるNPCと野生ポケモンとの戦闘の行動を決めるメソッド存在

MoveEvent.java→キーボード操作で起こるイベント(stateの変更)を担当
