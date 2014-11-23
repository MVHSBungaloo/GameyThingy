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
        
//Failed attempt to get an icon to appear for the game (LWJGL makes it a little hard)
    /* public ByteBuffer loadIcon(String filename, int width, int height) throws IOException 
    {
        BufferedImage image = ImageIO.read(new File(filename)); // load image

        // convert image to byte array
        byte[] imageBytes = new byte[width * height * 4];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel = image.getRGB(j, i);
                for (int k = 0; k < 3; k++) // red, green, blue
                    imageBytes[(i*16+j)*4 + k] = (byte)(((pixel>>(2-k)*8))&255);
                imageBytes[(i*16+j)*4 + 3] = (byte)(((pixel>>(3)*8))&255); // alpha
            }
        }
        return ByteBuffer.wrap(imageBytes);
    }
    */
    
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
    
}
