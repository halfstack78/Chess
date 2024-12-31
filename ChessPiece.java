import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.*;

public class ChessPiece {
	private int xcoord;
	private int ycoord;
	private int offsetx;
	private int offsety;
	protected String imString;
	private String type;
	//private JLabel label;
	private ImageIcon image;
	public ChessPiece(int x, int y, int offsetx, int offsety, String type, String imageString) {
		xcoord = x;
		ycoord = y;
		this.type = type;
		this.offsetx = offsetx;
		this.offsety = offsety;
		ClassLoader cldr = this.getClass().getClassLoader();	
		String imagePath = imageString;		
		imString= imageString;
		URL imageURL = cldr.getResource(imagePath);	
		this.image = new ImageIcon(imageURL);
		scaleImage(0.12);
		
	}
	public String getName() {
		return type;
	}
	public boolean movePiece(int x, int y) {
		boolean taking = false;
		if(GraphicsPanel.board[y][x] != null) {
			taking = true;
		}
    //White Pawn
    if(type.equals("wPawn")){
      if(taking){
        if(y == ycoord - 1 && (x == xcoord - 1 || x == xcoord + 1)){
          return true;
        }else{
          return false;
        }
      }else if(x == xcoord){
        if(y == ycoord - 1){
          return true;
        }else if(ycoord == 6 && y == 4 && GraphicsPanel.board[5][xcoord] == null){
          return true;
        }else{
          return false;
        }
      }
      return false;
    }
    //

    //Black Pawn
    if(type.equals("bPawn")){
      if(taking){
        if(y == ycoord + 1 && (x == xcoord - 1 || x == xcoord + 1)){
          return true;
        }else{
          return false;
        }
      }else if(x == xcoord && !taking){
        if(y == ycoord + 1){
          return true;
        }else if(ycoord == 1 && y == 3 && GraphicsPanel.board[2][xcoord] == null){
          return true;
        }else{
          return false;
        }
      }
      return false;
    }
    //

    //Rooks
    if(type.equals("wRook") || type.equals("bRook")){
      if(ycoord == y){
        
      int xNum = ((x - xcoord) / Math.abs(x - xcoord));
        //System.out.println("Along x-line");
        for(int i = xcoord + xNum; i != x; i += xNum){
          if(GraphicsPanel.board[ycoord][i] != null){
            //System.out.println("blocked");
            return false;
          }
        }
        return true;
      }else if(xcoord == x){
        
      int yNum = ((y - ycoord) / Math.abs(y - ycoord));
        //System.out.println("Along x-line");
        for(int i = ycoord + yNum; i != y; i += yNum){
          if(GraphicsPanel.board[i][xcoord] != null){
            //System.out.println("blocked at " + xcoord + " " + i);
            return false;
          }
        }
        return true;
      }  
      return false;
    }

    //Bishops
    if(type.equals("bBishop") || type.equals("wBishop")){
      if(Math.abs(x - xcoord) == Math.abs(y - ycoord)){
        int xNum = ((x - xcoord) / Math.abs(x - xcoord));
        int yNum = ((y - ycoord) / Math.abs(y - ycoord));
        for(int i = 1; xcoord + i * xNum != x; i += 1){
          //System.out.println((xcoord + i * xNum) + " " + (ycoord + i * yNum));
          if(GraphicsPanel.board[ycoord + i * yNum][xcoord + i * xNum] != null){
            return false;
          }
        }
        return true;
      }
      return false;
    }

    //Knights
    if(type.equals("bKnight") || type.equals("wKnight")){
      if((Math.abs(x - xcoord) == 1 && Math.abs(y - ycoord) == 2) || (Math.abs(x - xcoord) == 2 && Math.abs(y - ycoord) == 1)){
        return true;
      }
      return false;
    }

    //Queens
    if(type.equals("bQueen") || type.equals("wQueen")){
      if((Math.abs(x - xcoord) == Math.abs(y - ycoord))){
        int xNum = ((x - xcoord) / Math.abs(x - xcoord));
        int yNum = ((y - ycoord) / Math.abs(y - ycoord));
        for(int i = 1; xcoord + i * xNum != x; i += 1){
          if(GraphicsPanel.board[ycoord + i * yNum][xcoord + i * xNum] != null){
            return false;
          }
        }
        return true;
      }else if(xcoord == x){
        int yNum = ((y - ycoord) / Math.abs(y - ycoord));
        for(int i = ycoord + yNum; i != y; i += yNum){
          if(GraphicsPanel.board[i][xcoord] != null){
            return false;
          }
        }
        return true;
      }else if(ycoord == y){
      int xNum = ((x - xcoord) / Math.abs(x - xcoord));
        for(int i = xcoord + xNum; i != x; i += xNum){
          if(GraphicsPanel.board[ycoord][i] != null){
            return false;
          }
        }
        return true;
    }
	}
  if(type.equals("bKing") || type.equals("wKing")){
    if((x != xcoord || y != ycoord) && (Math.abs(x - xcoord) <= 1 && Math.abs(y - ycoord) <= 1)){
      return true;
    }
    return false;
  }
    return false;
  }
  public void changePos(int x, int y){
    xcoord = x;
    ycoord = y;
  }
	public void scaleImage(double scale) {
		Image scaled = image.getImage().getScaledInstance((int)(image.getIconWidth() * scale), 
				(int)(image.getIconHeight() * scale), Image.SCALE_SMOOTH);
		
		image = new ImageIcon(scaled);
	}
	public void draw(Graphics g, Component c) {
		image.paintIcon(c, g, (int)(xcoord * 90 * GraphicsPanel.windowScale + offsetx * GraphicsPanel.windowScale), (int)(ycoord * 90 * GraphicsPanel.windowScale + offsety * GraphicsPanel.windowScale));
	}
  public int getX(){
    return xcoord;
  }
  public int getY(){
    return ycoord;
  }


}
