var entity = Java.type('old.ecs.entity.Entity');
var position = Java.type('old.ecs.component.Position')
var sprite = Java.type('old.ecs.component.Sprite')

var init = function(inputEntity){
	entity = inputEntity
	entity.addComponent('position')
	entity.addComponent('sprite')
	print("init!!")
	return inputEntity
}

var event = function(){
	print("event!!")
}

var render = function(){
	print("render!!")
}

var close = function(){
	print("close!!")
}