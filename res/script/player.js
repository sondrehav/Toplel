function init(){
	gameObject.moveTo(600, 600);
	print("kekekekeke");
}

var k = true;
function onMoveDone(){
	if(k){
		gameObject.moveTo(600, 600);
		k = false;
	} else {
		gameObject.moveTo(200, 200);
		k = true;
	}

}