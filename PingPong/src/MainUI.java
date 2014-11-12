import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.TextField;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JCheckBox;
import java.awt.FlowLayout;

public class MainUI extends JFrame {

	private JPanel contentPane;
	private JTextField text_IP;
	private String iP;
	private JCheckBox checkBox;

	private Boolean isHost = false;
	private Boolean isConnected = false;
	private JTextField text_input;
	private JPanel panel_1;
	private JButton button;

	Socket socket;
	private JPanel CanvasPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainUI frame = new MainUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);

		checkBox = new JCheckBox("是否主机");
		panel_1.add(checkBox);
		checkBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				isHost = checkBox.isSelected();
			}
		});

		JButton btn_connect = new JButton("连接");
		panel_1.add(btn_connect);
		btn_connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				iP = text_IP.getText();
				if (isHost) {
					try {
						ServerSocket serverSocket = new ServerSocket(1234);
						socket = serverSocket.accept();
						isConnected = true;
						System.out.println(socket.getRemoteSocketAddress());
						InputStream in = socket.getInputStream();
						while (!socket.isClosed()) {
							int count = 0;
							while (count == 0) {
								count = in.available();
							}
							byte[] b = new byte[count];
							in.read(b);
							String text = new String(b, "utf-8");
							System.out.println(text);
							if (text.equals("exit")) {
								socket.close();
								isConnected = false;
							}
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						socket = new Socket(iP, 1234);
						isConnected = true;
						OutputStream outputStream = socket.getOutputStream();
						// System.out.println(outputStream.toString());
						// outputStream.write("nihao".getBytes("UTF-8"));
						// socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		text_input = new JTextField();
		panel_1.add(text_input);
		text_input.setColumns(10);

		button = new JButton("发送");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String text = text_input.getText();
				try {
					socket.getOutputStream().write(text.getBytes("utf-8"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		panel_1.add(button);
		text_IP = new JTextField();
		contentPane.add(text_IP, BorderLayout.NORTH);
		text_IP.setColumns(10);

		CanvasPanel = new JPanel();
		FlowLayout fl_CanvasPanel = (FlowLayout) CanvasPanel.getLayout();
		fl_CanvasPanel.setAlignment(FlowLayout.RIGHT);
		contentPane.add(CanvasPanel, BorderLayout.CENTER);
	}

	class myPanel extends JPanel {

		@Override
		public void paint(Graphics g) {
			// super.paint(arg0);
		}
	}

}
