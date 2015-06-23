var animation;

function init(){
	animation = gameObject.getComponent("animatedSprite");
}

var animationCounter = 0;
var type = "triangle";

var up = true;

function update(){
	if(type=="saw"){
		animationCounter+=0.5;
		if(animationCounter>128){
			animationCounter = 0;
		}
	} else if(type=="triangle") {
		if(up){
			animationCounter+=0.5;
		} else {
			animationCounter-=0.5;
		}
		if(animationCounter>=128){
			up = false;
			animationCounter = 96;
		} else if(animationCounter<=0){
			up = true;
			animationCounter=32;
		}
	}
	var currentCounter = Math.floor(animationCounter / 32);
	var currentRow = 0;
	switch(currentCounter + currentRow * 4){
		case 0:
			animation.setState("idle1");
			break;
		case 1:
			animation.setState("idle2");
			break;
		case 2:
			animation.setState("idle3");
			break;
		case 3:
			animation.setState("idle4");
			break;
		case 4:
			animation.setState("walk1");
			break;
		case 5:
			animation.setState("walk2");
			break;
		case 6:
			animation.setState("walk3");
			break;
		case 7:
			animation.setState("walk4");
			break;
		case 8:
			animation.setState("attack1");
			break;
		case 9:
			animation.setState("attack2");
			break;
		case 10:
			animation.setState("attack3");
			break;
		case 11:
			animation.setState("attack4");
			break;
		case 12:
			animation.setState("transition1");
			break;
		case 13:
			animation.setState("transition2");
			break;
		case 14:
			animation.setState("transition3");
			break;
		case 15:
			animation.setState("transition4");
			break;
	}
}