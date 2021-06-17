
var JinroResult = { A:{label:'WWです。'}, B:{label:'WWではありません。'}}


class Jinro {
  test() {
	  //alert('test');
  }
  play(players) {
	  //alert(players.join());
  }
  infoTest(infos) {



	  /* formがユニークなら fromに紐づく結果がすべて確定 */
	  /* すべてのtoが同じ結果なら、toが確定 */




	  var str = infos.join();
	  return str;

  }
}
class JinroPlayer {
	constructor(name, role) {
		this.name = name;
		this.role = role;
	}
	toString() {
		return this.name;
	}
}
class JinroInfo {
	constructor(from, to, result) {
		this.from = from;
		this.to = to;
		this.result = result;
	}
	toString() {
		return this.from + " →" + this.to + "「" + this.result.label + "」";
	}
}