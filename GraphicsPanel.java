import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.hamcrest.core.StringEndsWith;

import java.util.*;
import java.awt.event.*;

public class GraphicsPanel extends JPanel implements MouseListener{
	public static ChessPiece[][] board = new ChessPiece[8][8];
	
	public static double windowScale = 0.4;
  private ChessPiece[][] bCaptures = new ChessPiece[8][2];
  private ChessPiece[][] wCaptures = new ChessPiece[8][2];
	ChessPiece x;
	ImageIcon y;
	int winHeight = 720;
	int winWidth = 720;

  boolean wKingInCheck = false;
  boolean bKingInCheck = false;
  
	boolean whiteTurn = true;

  boolean gameOver = false;

  String winner = "Black";
	
	int selectedX = -1;
	int selectedY = -1;
	
	String wPawn = "images/pieces/wPawn.png";
	String wRook = "images/pieces/wRook.png";
	String wBishop = "images/pieces/wBishop.png";
	String wKing = "images/pieces/wKing.png";
	String wQueen = "images/pieces/wQueen.png";
	String wKnight = "images/pieces/wKnight.png";
	
	String bPawn = "images/pieces/bPawn.png";
	String bRook = "images/pieces/bRook.png";
	String bBishop = "images/pieces/bBishop.png";
	String bKing = "images/pieces/bKing.png";
	String bQueen = "images/pieces/bQueen.png";
	String bKnight = "images/pieces/bKnight.png";
	
	Image img1 = Toolkit.getDefaultToolkit().getImage("images/pieces/wPawn.png");
    
	public GraphicsPanel() {
		setPreferredSize(new Dimension(winWidth, winHeight));
		resetBoard();
		this.addMouseListener(this);
		this.repaint();
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if((i + j) % 2 == 1){
					g2.setColor(new Color(197, 140, 89));
				}else {
					g2.setColor(new Color(135, 95, 59));
				}
				g2.fillRect((int)(i * 90.0  * windowScale), (int)(j * 90.0 * windowScale), (int)(90.0 * windowScale), (int)(90.0 * windowScale));
			}
		}
		if(selectedX != -1 && selectedY != -1) {
			g2.setColor(new Color(255, 239, 0));
			g2.fillRect((int)(selectedX * 90.0 * windowScale), (int)(selectedY * 90.0 * windowScale), (int)(90.0 * windowScale), (int)(90.0 * windowScale));
		}
		for(ChessPiece[] x:board) {
			for(ChessPiece y:x) {
				if(y != null) {
					y.draw(g2, this);
				}
			}
		}
    if(gameOver){
      g2.setColor(Color.gray);
      g2.fillRect(0, 0, (int)(720 * windowScale), (int)(720 * windowScale));
      g2.setColor(Color.white);
      g2.setFont(new Font("Serif", Font.PLAIN, (int)(70 * windowScale)));
      g2.drawString(winner + " Won!", (int)(163 * windowScale), (int)(180 * windowScale));
      }
	}
	public void onClick(int x, int y) {
		int arrX = x / (int)(90 * windowScale);
		int arrY = y / (int)(90 * windowScale);
    if((selectedX == -1 && selectedY == -1)) {
        String name = board[arrY][arrX].getName();
			boolean isWhite = name.substring(0, 1).equals("w");
      if(isWhite == whiteTurn){
					selectedX = arrX;
					selectedY = arrY;
      }
		}else if(board[arrY][arrX] != null){
      String name = board[arrY][arrX].getName();
			boolean isWhite = name.substring(0, 1).equals("w");
      if(isWhite == whiteTurn){
        selectedX = arrX;
				selectedY = arrY;
      }else{
        movePieceFr(arrX, arrY, isWhite);
        selectedX = -1;
        selectedY = -1;
      }
    }else{
      movePieceFr(arrX, arrY, false);
      selectedX = -1;
      selectedY = -1;
    }
		this.repaint();
	}
  public void movePieceFr(int arrX, int arrY, boolean isWhite){
    if(board[selectedY][selectedX].movePiece(arrX, arrY)){
      if(board[arrY][arrX] != null){
        if(board[arrY][arrX].getName().equals("bKing")){
          winner = "White";
          gameOver = true;
          }else if(board[arrY][arrX].getName().equals("wKing")){
          winner = "Black";
          gameOver = true;
          }
        if(isWhite){
          
          for(int i = 0; i < bCaptures.length; i++){
             for(int j = 0; j < bCaptures[i].length; j++){
               if(bCaptures[i][j] == null){
                 bCaptures[i][j] = board[arrY][arrX];
                 break;
               }
            }
          }
        }else{
          for(int i = 0; i < wCaptures.length; i++){
             for(int j = 0; j < wCaptures[i].length; j++){
               if(wCaptures[i][j] == null){
                 wCaptures[i][j] = board[arrY][arrX];
                 break;
               }
            }
          }
        }
        }
        board[arrY][arrX] = board[selectedY][selectedX];
        board[selectedY][selectedX] = null;
        board[arrY][arrX].changePos(arrX, arrY);
        selectedX = -1;
        selectedY = -1;
      String str;
        if(whiteTurn){
          str = "bKing";
        }else{
          str = "wKing";
        }
      ChessPiece p = board[0][0];
        for(ChessPiece[] x:board){
        for(ChessPiece y:x){
          if(y.getName().equals(str)){
             p = y;
            break;
          }
          }
        }
      // if(isInCheck(!whiteTurn, p.getX(), p.getY())){
      //   if(isCheckmate(!whiteTurn, p.getX(), p.getY())){
      //     gameOver = true;
      //     winner =  whiteTurn ? "White" : "Black";
      //   }
      // }
      //   whiteTurn = !whiteTurn;
      // }
    }
  }
  public boolean isCheckmate(boolean whiteKing, int kingX, int kingY){
    ArrayList<Integer> threats = dangerSquares(whiteKing);
    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        if(board[kingY][kingX].movePiece(i, j) && !(isInCheck(whiteKing, i, j))){
          return false;
        }
      }
    }
    return true;
  }
  public boolean isInCheck(boolean whiteKing, int x, int y){
    ArrayList<Integer> threats = dangerSquares(whiteKing);
    int i = 0;
    while(i < threats.size()){
      if(x == threats.get(i)){
          if(y == threats.get(i + 1)){
            return true;
          }
      }
      i += 2;
    }
    return false;
  }

  public ArrayList<Integer> dangerSquares(boolean whiteInDanger){
   ArrayList<Integer> out = new ArrayList<Integer>();
    String z;
    if(whiteInDanger){
      z = "b";
    }else{
      z = "w";
    }
      for(ChessPiece[] x:board){
        for(ChessPiece y:x){
          if(y.getName().substring(0, 1).equals(z)){
            for(int i = 0; i < 8; i++){
              for(int j = 0; j < 8; j++){
                if(y.movePiece(i, j)){
                  out.add(i);
                  out.add(j);
                }
              }
            }
          }
        }
      }
    return out;
  }
	public double getScale(){
    return windowScale;
  }
	public void resetBoard() {
		for(int i = 0; i < 8; i++) {
			board[1][i] = new ChessPiece(i, 1, 0, -5, "bPawn", bPawn);
      board[1][i].scaleImage(windowScale);
		}
		for(int i = 0; i < 8; i++) {
			board[6][i] = new ChessPiece(i, 6, 20, 10, "wPawn", wPawn);
      board[6][i].scaleImage(windowScale);
		 }
		board[0][0] = new ChessPiece(0, 0, 0, 0, "bRook", bRook);
		board[0][0].scaleImage(0.75 * windowScale);
		board[0][7] = new ChessPiece(7, 0, 0, 0, "bRook", bRook);
		board[0][7].scaleImage(0.75 * windowScale);
		
		board[0][1] = new ChessPiece(1, 0, 5, 5, "bKnight", bKnight);
		board[0][1].scaleImage(1.3 * windowScale);
		board[0][6] = new ChessPiece(6, 0, 5, 5, "bKnight", bKnight);
		board[0][6].scaleImage(1.3 * windowScale);
		
		board[0][2] = new ChessPiece(2, 0, 8, 8, "bBishop", bBishop);
		board[0][2].scaleImage(1 * windowScale);
		board[0][5] = new ChessPiece(5, 0, 8, 8, "bBishop", bBishop);
		board[0][5].scaleImage(1 * windowScale);
		
		board[0][3] = new ChessPiece(3, 0, -1, 0, "bQueen", bQueen);
		board[0][3].scaleImage(0.32 * windowScale);
		
		board[0][4] = new ChessPiece(4, 0, 0, 0, "bKing", bKing);
		board[0][4].scaleImage(0.32 * windowScale);
		
		board[7][0] = new ChessPiece(0, 7, 0, 0, "wRook", wRook);
		board[7][0].scaleImage(0.32 * windowScale);
		board[7][7] = new ChessPiece(7, 7, 0, 0, "wRook", wRook);
		board[7][7].scaleImage(0.32 * windowScale);
		
		board[7][1] = new ChessPiece(1, 7, 5, 5, "wKnight", wKnight);
		board[7][1].scaleImage(1 * windowScale);
		board[7][6] = new ChessPiece(6, 7, 5, 5, "wKnight", wKnight);
		board[7][6].scaleImage(1 * windowScale);
		
		board[7][2] = new ChessPiece(2, 7, -3, -3, "wBishop", wBishop);
		board[7][2].scaleImage(0.8 * windowScale);
		board[7][5] = new ChessPiece(5, 7, -3, -3, "wBishop", wBishop);
		board[7][5].scaleImage(0.8 * windowScale);
		
		board[7][3] = new ChessPiece(3, 7, -1, 0, "wQueen", wQueen);
		board[7][3].scaleImage(0.76 * windowScale);
		
		board[7][4] = new ChessPiece(4, 7, 0, 0, "wKing", wKing);
		board[7][4].scaleImage(0.32 * windowScale);
	}
	//@Override
	public void mousePressed(MouseEvent e){
		onClick(e.getX(), e.getY());
	}

	//@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	//@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	//@Override
	public void mouseExited(MouseEvent e) {
		
	}

	//@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}
}
