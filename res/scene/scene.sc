{
  "title": "Test scene.",
  "entities": [
    {
      "type": "Renderable",
      "x_pos": -10,
      "y_pos": -20,
	  "x_size": 100f,
	  "y_size": 100f,
      "rot": 0,
      "shader": {
        "vertexShader": "res/shader/defaultVertexShader.vs",
        "fragmentShader": "res/shader/defaultFragmentShader.fs"
      },
      "sprite": {
        "spritePath": "res/img/PNG/Aliens/alienBeige_round.png"
      }
    },
    {
      "type": "Renderable",
      "x_pos": 10,
      "y_pos": 0,
	  "x_size": 100f,
	  "y_size": 100f,
      "rot": 45,
      "shader": {
        "vertexShader": "res/shader/defaultVertexShader.vs",
        "fragmentShader": "res/shader/defaultFragmentShader.fs"
      },
      "sprite": {
        "spritePath": "res/img/PNG/Stone elements/elementStone045.png"
      }
    },
    {
      "path": "res/scene/entity/player.ent"
    },
    {
      "path": "res/scene/entity/ent.ent"
    }
  ]
}