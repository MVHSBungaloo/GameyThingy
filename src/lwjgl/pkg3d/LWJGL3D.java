/*package lwjgl.pkg3d;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.opengl.ImageIOImageData;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class LWJGL3D 
{
    
    public static void main(String[] args) 
    {
        initDisplay();
       
        gameLoop();
        cleanUp();
    }
    
    
    //Pass in the name of a .png file (without the file extension!) and it will attempt to load a .png in the "res" folder (under project root) with the same name
    public static Texture loadTexturePNG(String key) //Having both loadTexturePNG and loadTextureJPG active at the same time may be the cause of some odd behavior I've seen
    {      
        try 
        {
            return TextureLoader.getTexture("png", new FileInputStream(new File("res/" + key + ".png")));
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(LWJGL3D.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return null;
    }
    //Pass in the name of a .jpg file (without the file extension!) and it will attempt to load a .jpg in the "res" folder (under project root) with the same name
        public static Texture loadTextureJPG(String key)
    {      
        try 
        {
            return TextureLoader.getTexture("jpg", new FileInputStream(new File("res/" + key + ".jpg")));
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(LWJGL3D.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return null;
    }
        

    
    public static void gameLoop()
    {
        Texture kappa = loadTextureJPG("kappa"); //textures need to be square and a multiple of 2!!!
        
        Camera cam = new Camera(70,(float)Display.getWidth()/(float)Display.getHeight(),0.3f,1000);
        float x = 0;
        
        boolean temp = false;
        
        while(!Display.isCloseRequested())
        {
            boolean forward = Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP);
            boolean backward = Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN);
            boolean left = Keyboard.isKeyDown(Keyboard.KEY_A);
            boolean right = Keyboard.isKeyDown(Keyboard.KEY_D);
            
            if(forward)
                cam.move(.1f,1);
            if(backward)
                cam.move(-.1f,1);
            if(left)
                cam.move(.1f, 0);//cam.rotateY(-0.1f);
            if(right)
                cam.move(-.1f, 0);//cam.rotateY(0.1f);
            
            if(Keyboard.isKeyDown(Keyboard.KEY_LEFT))
                cam.rotateY(-1f);
            if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
                cam.rotateY(1f);
            
          
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glLoadIdentity();
            
            
            cam.useView();
            
            
            
            glPushMatrix();
            {
                glColor3f(1.0f,0.5f,0f);
                glTranslatef(0,0,-10);
                glRotatef(x,1,1,0);
                kappa.bind(); //binds texture to the following square faces
                
                
                //Draws the cube
                glBegin(GL_QUADS);
                {
                    //FrontFace
                    
                    glColor3f(1f,0f,0f);
                    glTexCoord2f(0,0); glVertex3f(-1,-1,1);
                    glTexCoord2f(0,1); glVertex3f(-1,1,1);
                    glTexCoord2f(1,1); glVertex3f(1,1,1);
                    glTexCoord2f(1,0); glVertex3f(1,-1,1);
                    
                    //BackFace
                    glColor3f(0f,1f,0f);
                    glTexCoord2f(0,0); glVertex3f(-1,-1,-1);
                    glTexCoord2f(0,1);glVertex3f(-1,1,-1);
                    glTexCoord2f(1,1);glVertex3f(1,1,-1);
                    glTexCoord2f(1,0);glVertex3f(1,-1,-1);
                    
                    //BottomFace
                    
                    glColor3f(0f,0f,1f);
                    glTexCoord2f(0,0);glVertex3f(-1,-1,-1);
                    glTexCoord2f(0,1);glVertex3f(-1,-1,1);
                    glTexCoord2f(1,1);glVertex3f(-1,1,1);
                    glTexCoord2f(1,0);glVertex3f(-1,1,-1);
                    
                    //TopFace
                    glColor3f(1f,1f,0f);
                    glTexCoord2f(0,0);glVertex3f(1,-1,-1);
                    glTexCoord2f(0,1);glVertex3f(1,-1,1);
                    glTexCoord2f(1,1); glVertex3f(1,1,1);
                    glTexCoord2f(1,0);glVertex3f(1,1,-1);
                    
                    //LeftFace
                    glColor3f(0f,1f,1f);
                    glTexCoord2f(0,0);glVertex3f(-1,-1,-1);
                    glTexCoord2f(0,1);glVertex3f(1,-1,-1);
                    glTexCoord2f(1,1);glVertex3f(1,-1,1);
                    glTexCoord2f(1,0);glVertex3f(-1,-1,1);
                    
                    //RightFace
                    glColor3f(1f,0f,1f);
                    glTexCoord2f(0,0);glVertex3f(-1,1,-1);
                    glTexCoord2f(0,1);glVertex3f(1,1,-1);
                    glTexCoord2f(1,1);glVertex3f(1,1,1);
                    glTexCoord2f(1,0);glVertex3f(-1,1,1);
                    
                    
                    
                    
                }
                glEnd();
            }
            glPopMatrix();
            x += 1f;
            Display.update();
            Display.sync(60);
        }
    }
    
    public static void cleanUp()
    {
        Display.destroy();
    }
    
    public static void initDisplay()
    {
        try 
        {
            Display.setDisplayMode(new DisplayMode(600,600));
            Display.create();
            Display.setVSyncEnabled(true);
            Display.setTitle("KAPPA");
            
        } 
        catch (LWJGLException ex) 
        {
            Logger.getLogger(LWJGL3D.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void update() {
    
  }
}*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lwjgl.pkg3d;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;
import java.util.logging.FileHandler;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.ImageIOImageData;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

//import org.newdawn.slick.Image;



/**
 * @author jediTofu
 * @see <a href="http://lwjgl.org/">LWJGL Home Page</a>
 */
public class LWJGL3D {
  public static final int DISPLAY_HEIGHT = 480;
  public static final int DISPLAY_WIDTH = 640;
  public static final Logger LOGGER = Logger.getLogger(LWJGL3D.class.getName());

  private int squareSize;
  private int squareX;
  private int squareY;
  private int squareZ;
  
        
        //Camera cam = new Camera(70,(float)Display.getWidth()/(float)Display.getHeight(),0.3f,1000);
        float x = 0;
        
        boolean forward;
        boolean backward;
        boolean left;
        boolean right;

  static {
    try {
      LOGGER.addHandler(new FileHandler("errors.log",true));
    }
    catch(IOException ex) {
      LOGGER.log(Level.WARNING,ex.toString(),ex);
    }
  }

  
    Texture kappa = loadTextureJPG("kappa"); //textures need to be square and a multiple of 2!!!

  public static void main(String[] args) {
    LWJGL3D main = null;
    try {
      System.out.println("Keys:");
      System.out.println("down  - Shrink");
      System.out.println("up    - Grow");
      System.out.println("left  - Rotate left");
      System.out.println("right - Rotate right");
      System.out.println("esc   - Exit");
      main = new LWJGL3D();
      main.create();
      main.run();
    }
    catch(Exception ex) {
      LOGGER.log(Level.SEVERE,ex.toString(),ex);
    }
    finally {
      if(main != null) {
        main.destroy();
      }
    }
  }

  public LWJGL3D() {
    squareSize = 100;
    squareX = 0;
    squareY = 0;
    squareZ = 0;
    
    
        
  }

  public void create() throws LWJGLException {
    //Display
    Display.setDisplayMode(new DisplayMode(DISPLAY_WIDTH,DISPLAY_HEIGHT));
    Display.setFullscreen(false);
    
    Display.sync(60);
    Display.setTitle("Hello LWJGL World!");
    
    Display.create();
    //Keyboard
    Keyboard.create();

    //Mouse
    Mouse.setGrabbed(false);
    Mouse.create();

    //OpenGL
    initGL();
    resizeGL();
  }

  public void destroy() {
    //Methods already check if created before destroying.
    Mouse.destroy();
    Keyboard.destroy();
    Display.destroy();
  }

  public void initGL() {
    //2D Initialization
      glMatrixMode(GL_PROJECTION);
      glLoadIdentity(); //clear matrix
      glOrtho(0,Display.getWidth(),0,Display.getHeight(),-1,1);
      glMatrixMode(GL_MODELVIEW);
      
      
    glClearColor(0,0.0f,0.0f,0.0f);
    glDisable(GL_DEPTH_TEST);
    glDisable(GL_LIGHTING);
  }

  public void processKeyboard() {
    //Square's Size
    if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
      backward=true;
    }
    if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {
      forward=true;
    }

    //Square's Z
    if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
        left=true;
    }
    if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
      right=true;
    }
  }

  public void processMouse() {
    squareX = Mouse.getX();
    squareY = Mouse.getY();
  }

  
  public static Texture loadTexturePNG(String key) //Having both loadTexturePNG and loadTextureJPG active at the same time may be the cause of some odd behavior I've seen
    {      
        try 
        {
            return TextureLoader.getTexture("png", new FileInputStream(new File("res/" + key + ".png")));
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(LWJGL3D.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return null;
    }
  
  public static Texture loadTextureJPG(String key)
    {      
        try 
        {
            return TextureLoader.getTexture("jpg", new FileInputStream(new File("res/" + key + ".jpg")));
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(LWJGL3D.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return null;
    }
  public void render() {
      //clear
    glClear(GL_COLOR_BUFFER_BIT);
    glLoadIdentity();

    
    
//    glColor3f(0.25f,0.75f,0.65f);
//     glBegin(GL_QUADS);
//     {
//      glVertex2f(0,0);
//      glVertex2f(64,64);
//      glVertex2f(64,0);
//            glVertex2f(0,64);
//
//      
//
//     }
//    glEnd();
    

    //Draw a basic square
    glTranslatef(squareX,squareY,0.0f);
    glRotatef(squareZ,0.0f,0.0f,1.0f);
    glTranslatef(-(squareSize >> 1),-(squareSize >> 1),0.0f);
    glColor3f(0.0f,0.5f,0.5f);
    glBegin(GL_QUADS);
      glTexCoord2f(0.0f,0.0f); glVertex2f(0.0f,0.0f);
      glTexCoord2f(1.0f,0.0f); glVertex2f(0.0f,0.0f);
      glTexCoord2f(1.0f,1.0f); glVertex2f(0.0f,0.0f);
      glTexCoord2f(0.0f,1.0f); glVertex2f(0.0f,0.0f);
    glEnd();
    
    glBegin(GL_QUADS);
      glTexCoord2f(0.0f,0.0f); glVertex2f(0.0f,0.0f);
      glTexCoord2f(1.0f,0.0f); glVertex2f(5f,0.0f);
      glTexCoord2f(1.0f,1.0f); glVertex2f(5f,5f);
      glTexCoord2f(0.0f,1.0f); glVertex2f(0.0f,5f);
    glEnd();
  }

  public void resizeGL() {
    //2D Scene
    glViewport(0,0,DISPLAY_WIDTH,DISPLAY_HEIGHT);

    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluOrtho2D(0.0f,DISPLAY_WIDTH,0.0f,DISPLAY_HEIGHT);
    glPushMatrix();

    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
    glPushMatrix();
  }
//paint
  public void run() {
    while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
      if(Display.isVisible()) {
        processKeyboard();
        processMouse();
        update();
        render();
      }
      else {
        if(Display.isDirty()) {
          render();
        }
        try {
          Thread.sleep(100);
        }
        catch(InterruptedException ex) {
        }
      }
      Display.update();
      Display.sync(60);
    }
  }

  public void update() {
    if(squareSize < 5) {
      squareSize = 5;
    }
    else if(squareSize >= DISPLAY_HEIGHT) {
      squareSize = DISPLAY_HEIGHT;
    }
//   // if(forward)
//               // cam.move(.1f,1);
//            if(backward)
//              //  cam.move(-.1f,1);
//            if(left)
//              //  cam.move(.1f, 0);//cam.rotateY(-0.1f);
//            if(right)
//                //cam.move(-.1f, 0);//cam.rotateY(0.1f);
  }
  
  
  
}

