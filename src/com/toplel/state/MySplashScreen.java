package com.toplel.state;

public class MySplashScreen {/*

    MyImage tree = new MyImage("res/img/game/objects/tree.png", new Vector2f(100f,100f), new Vector2f(100f,100f));
    MyImage rock = new MyImage("res/img/game/objects/rock.png", new Vector2f(100f,250f), new Vector2f(100f,100f));

    final Matrix4f viewMatrix = Matrix4f.setIdentity(new Matrix4f());

    MyHUD hud = new MyHUD(MyMain.getProjectionMatrix());
    MyWorld world = new MyWorld(MyMain.getProjectionMatrix(), viewMatrix);

    LinkedList<MyImage> images = new LinkedList<>();
    LinkedList<MyContextMenu> contextMenus = new LinkedList<>();

    MyControlPoint controlPoint = new MyControlPoint(new Vector2f(500f,500f), 100f, 200f, "res/img/defaultImage.png");

    @Override
    public void init() {
        viewMatrix.m32 = .1f;
        tree.setContext(hud);
        rock.setContext(hud);
        controlPoint.setContext(world);
        MyMouseEventHandler.addListener(new MyMouseListener(tree, 0) {

            MyImage dragging = null;

            @Override
            public void onMouseIn(float mx, float my) {
                tree.alpha = .9f;
            }

            @Override
            public void onMouseOut(float mx, float my) {
                tree.alpha = .6f;
            }

            @Override
            public void onMouseDown(float mx, float my) {
                Vector2f mousePos = tree.getContext().toContext(new Vector2f(mx, my));
                mousePos = world.fromContext(mousePos);
                MyImage newImg = new MyImage("res/img/game/objects/tree.png", mousePos, new Vector2f(100f, 100f));
                newImg.setContext(world);
                images.addFirst(newImg);
                MyMouseEventHandler.addListener(new MyMouseListener(newImg, 0) {
                    @Override
                    public void onMouseDrag(float mx, float my) {
                        newImg.setPosition(new Vector2f(mx - newImg.getSize().x * .5f, my - newImg.getSize().y * .5f));
                    }

                    @Override
                    public void onMouseDown(float mx, float my) {
                        images.remove(newImg);
                        images.addFirst(newImg);
                    }
                });
                MyMouseEventHandler.addListener(new MyMouseListener(newImg, 1) {
                    @Override
                    public void onMouseUp(float mx, float my) {
                        MyContextMenu contextMenu = new MyContextMenu(new Vector2f(mx,my), new Vector2f(200f, 20f));
                        contextMenu.setContext(newImg.getContext());
                        contextMenu.addElement("Delete");
                        contextMenu.addElement("Set size");
                        contextMenu.addElement("Set rotation");
                        contextMenu.addListener(0, new OnAction() {
                            @Override
                            public void event() {
                                images.remove(newImg);
                                newImg.onDelete();
                                contextMenu.setActive(false);
                                contextMenus.remove(contextMenu);
                                contextMenu.onDelete();
                            }
                        });
                        contextMenu.addListener(1, new OnAction() {
                            @Override
                            public void event() {
                                System.out.println("TODO: Set size!");
                                contextMenu.setActive(false);
                            }
                        });
                        contextMenu.addListener(2, new OnAction() { @Override public void event() {
                            System.out.println("TODO: Set rotation!");
                            contextMenu.setActive(false);
                        } });
                        contextMenus.addFirst(contextMenu);
                    }
                });
                dragging = newImg;
            }

            @Override
            public void onMouseDrag(float mx, float my) {
                Vector2f mousePos = tree.getContext().toContext(new Vector2f(mx, my));
                mousePos = dragging.getContext().fromContext(mousePos);
                dragging.setPosition(mousePos);
            }

            @Override
            public void onMouseDragRelease(float mx, float my) {
                dragging = null;
            }

        });
        MyMouseEventHandler.addListener(new MyMouseListener(rock, 0) {

            MyImage dragging = null;

            @Override
            public void onMouseIn(float mx, float my) {
                rock.alpha = .9f;
            }

            @Override
            public void onMouseOut(float mx, float my) {
                rock.alpha = .6f;
            }

            @Override
            public void onMouseDown(float mx, float my) {
                Vector2f mousePos = rock.getContext().toContext(new Vector2f(mx, my));
                mousePos = world.fromContext(mousePos);
                MyImage newImg = new MyImage("res/img/game/objects/rock.png", mousePos, new Vector2f(50f, 50f));
                newImg.setContext(world);
                images.addFirst(newImg);
                MyMouseEventHandler.addListener(new MyMouseListener(newImg, 0) {
                    @Override
                    public void onMouseDrag(float mx, float my) {
                        newImg.setPosition(new Vector2f(mx - newImg.getSize().x * .5f, my - newImg.getSize().y * .5f));
                    }

                    @Override
                    public void onMouseDown(float mx, float my) {
                        images.remove(newImg);
                        images.addFirst(newImg);
                    }
                });
                MyMouseEventHandler.addListener(new MyMouseListener(newImg, 1) {
                    @Override
                    public void onMouseUp(float mx, float my) {
                        images.remove(newImg);
//                        menu.setPosition(new Vector2f(mx, my));
                    }
                });
                dragging = newImg;
            }

            @Override
            public void onMouseDrag(float mx, float my) {
                Vector2f mousePos = rock.getContext().toContext(new Vector2f(mx, my));
                mousePos = dragging.getContext().fromContext(mousePos);
                dragging.setPosition(mousePos);
            }

            @Override
            public void onMouseDragRelease(float mx, float my) {
                dragging = null;
            }

        });
        MyKeyboardEventHandler.addListener(new MyKeyListener(Keyboard.KEY_S) {
            @Override
            public void onKeyHold() {
                world.setPosition(Vector2f.add(world.getPosition(), new Vector2f(0f, 3f), null));
            }
        });
        MyKeyboardEventHandler.addListener(new MyKeyListener(Keyboard.KEY_D) {
            @Override
            public void onKeyHold() {
                world.setPosition(Vector2f.add(world.getPosition(), new Vector2f(-3f, 0f), null));
            }
        });
        MyKeyboardEventHandler.addListener(new MyKeyListener(Keyboard.KEY_W) {
            @Override
            public void onKeyHold() {
                world.setPosition(Vector2f.add(world.getPosition(), new Vector2f(0f, -3f), null));
            }
        });
        MyKeyboardEventHandler.addListener(new MyKeyListener(Keyboard.KEY_A) {
            @Override
            public void onKeyHold() {
                world.setPosition(Vector2f.add(world.getPosition(), new Vector2f(3f, 0f), null));
            }
        });
        MyKeyboardEventHandler.addListener(new MyKeyListener(Keyboard.KEY_Q) {
            @Override public void onKeyHold() {
                Vector2f v = new Vector2f(world.getSize());
                v.x *= 1.01f;
                v.y *= 1.01f;
                world.setSize(v);
            }
        });
        MyKeyboardEventHandler.addListener(new MyKeyListener(Keyboard.KEY_E) {
            @Override public void onKeyHold() {
                Vector2f v = new Vector2f(world.getSize());
                v.x *= .99f;
                v.y *= .99f;
                world.setSize(v);
            }
        });
        MyKeyboardEventHandler.addListener(new MyKeyListener(Keyboard.KEY_F1) {
            @Override
            public void onKeyDown() {
                wf = !wf;
                if(wf){
                    GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
                } else {
                    GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
                }
            }
        });
        MyMouseEventHandler.addListener(new MyMouseListener(controlPoint, 0) {
            @Override
            public void onMouseDrag(float mx, float my) {
                controlPoint.setPosition(new Vector2f(mx - controlPoint.getSize().x*.5f, my - controlPoint.getSize().y*.5f));
            }
            @Override
            public void onMouseDragRelease(float mx, float my){
                System.out.println("RECALCULATE");
            }
        });

    }
    boolean wf = false;
    @Override
    public void event() {
//        world.setRotation(world.getRotation()+1f);
    }

    @Override
    public void render() {
        tree.render();
        rock.render();
        for(MyContextMenu menus : contextMenus){
            menus.render();
        }
        for(MyImage image : images){
            image.render();
        }
    }

    @Override
    public void close() {

    }

    @Override
    public void onResize(){
        world.recalculate();
        hud.recalculate();
    }*/
}
