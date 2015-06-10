var Entity = Java.type("old.ecs.entity.Entity");
var Position = Java.type("old.ecs.component.Component");
var Sprite = Java.type("old.ecs.component.Component");

var init = function(){
	var sprite = addComponent("res/system/components/sprite.json");
	var position = addComponent("res/system/components/position.json");
	var input = addComponent("res/system/components/playerInput.json")
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