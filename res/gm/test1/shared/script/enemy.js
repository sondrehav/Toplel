file.begin res\gm\test1\shared\script\spritetest.jsvar Entity = Java.type("oldold.old.ecs.entity.Entity");
var Position = Java.type("oldold.old.ecs.component.Component");
var Sprite = Java.type("oldold.old.ecs.component.Component");

var init = function(){
	var sprite = addComponent("res/system/ecs/sprite.json");
	var position = addComponent("res/system/ecs/position.json");
	var input = addComponent("res/system/ecs/playerInput.json")
	sprite.setVar("PATH", "res/img/cross.png")
	sprite.setVar("bounds", "use_image")
	position.setVar("position", 0, 0)
	position.setVar("rotation", 45)
	input.setVar("keys", "W", "A", "S", "D")
}

var event = function(){
	
}
	
var render = function(){
	sprite.draw(position)
}

var close = function(){
	print("close!!")
}