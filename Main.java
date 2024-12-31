import javax.swing.JFrame;
import javax.swing.JPanel;


public class Main extends JFrame{
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		panel.add(new GraphicsPanel());
		//panel.setLayout(null);
		
		frame.setTitle("Chess");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setResizable(false);
		frame.setSize(720, 753);
		frame.setLocationRelativeTo(null);
		frame.setContentPane(panel);
		frame.setVisible(true);
	
		
	}
}
